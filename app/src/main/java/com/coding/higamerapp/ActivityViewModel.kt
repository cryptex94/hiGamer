package com.coding.higamerapp

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.common.util.*
import com.coding.higamerapp.feature_chat.data.data_source.GamerDetail
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import com.coding.higamerapp.feature_gamers.domain.use_case.delete_gamer.DeleteGamer
import com.coding.higamerapp.feature_gamers.domain.use_case.get_gamer.GetGamer
import com.coding.higamerapp.feature_gamers.domain.use_case.post_gamer.PostGamer
import com.coding.higamerapp.feature_gamers.domain.use_case.update_gamer.UpdateGamer
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.coding.higamerapp.feature_profile.data.DataStoreManager
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import com.coding.higamerapp.feature_terms.data.Terms
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.http.toHttpDateOrNull
import okhttp3.internal.http.toHttpDateString
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val deleteGamer: DeleteGamer,
    private val postGamer: PostGamer,
    private val getGamer: GetGamer,
    private val updateGamer: UpdateGamer,
    private val roomDatabase: ChatRoomRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var roomRegistration: ListenerRegistration? = null

    private var mediaPlayer: android.media.MediaPlayer? =
        android.media.MediaPlayer.create(context, R.raw.swiftly)
    var mHandler: Handler? = Handler(Looper.getMainLooper())

    init {
        runBlocking {
            getDataStoreProfile()
        }
    }

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    if (myFirebaseId != null && Profile.name != null &&
                        Profile.role != null && Profile.server != null
                        && Profile.avatar != null && Profile.tier != null
                    )
                        updateGamerOnDatabase()
                }
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler!!.postDelayed(this, Constants.mInterval)
            }
        }
    }

    fun startRepeatingTask() {
        mStatusChecker.run()
    }

    fun stopRepeatingTask() {
        mHandler!!.removeCallbacks(mStatusChecker)
    }

    private suspend fun getDataStoreProfile() {
        Profile.role = dataStoreManager.readIntValueDataStore(Constants.PROFILE_ROLE)
        Profile.name = dataStoreManager.readStringValueDataStore(Constants.PROFILE_NAME)
        Profile.server = dataStoreManager.readIntValueDataStore(Constants.PROFILE_SERVER)
        Profile.team = dataStoreManager.readBooleanValueDataStore(Constants.PROFILE_TEAM)
        Profile.tier = dataStoreManager.readIntValueDataStore(Constants.PROFILE_TIER)
        Profile.avatar = dataStoreManager.readIntValueDataStore(Constants.PROFILE_AVATAR)
        Profile.language = dataStoreManager.readStringValueDataStore(Constants.PROFILE_LANGUAGE)
            ?.toIntOrNull()
        val stringChampions = dataStoreManager.readStringValueDataStore(Constants.PROFILE_CHAMPIONS)

        var firstChamp: Int? = null
        if (stringChampions != null) {
            firstChamp = if (stringChampions.contains(","))
                stringChampions.substringBefore(",", "null").drop(1).toIntOrNull()
            else stringChampions.drop(1).dropLast(1).toIntOrNull()
        }
        val secondChamp =
            stringChampions?.substringAfter(",", "null")?.drop(1)?.dropLast(1)?.toIntOrNull()
        Profile.champions = listOf(firstChamp, secondChamp)
        Terms.terms = dataStoreManager.readBooleanValueDataStore(Constants.TERMS)
    }

    suspend fun deleteGamerFromDatabase() {
        myFirebaseId?.let {
            deleteGamer(it).collect()
        }
    }

    private suspend fun postGamerToDatabase() {
        val gamer = ProfileDto(
            username = Profile.name,
            role = Profile.role,
            firebaseId = myFirebaseId,
            tier = Profile.tier,
            avatar = Profile.avatar,
            server = Profile.server,
            team = Profile.team,
            language = Profile.language,
            champions = Profile.champions
        )
        postGamer(gamer).collect()
    }

    suspend fun updateGamerOnDatabase() {
        myFirebaseId?.let {
            val gamer = ProfileDto(
                username = Profile.name,
                role = Profile.role,
                firebaseId = myFirebaseId,
                tier = Profile.tier,
                avatar = Profile.avatar,
                server = Profile.server,
                team = Profile.team,
                language = Profile.language,
                champions = Profile.champions
            )
            updateGamer(myFirebaseId!!, gamer).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() != 200) {
                            if (result.data?.code() == 404) {
                                postGamerToDatabase()
                            }
                        }
                    }
                    is Resource.Error -> {}
                    else -> {}
                }
            }
        }
    }

    fun listenForChatRooms(navController: NavController) {
        val query = firestore.collection("rooms").document(myFirebaseId!!)
            .collection("notify")

        roomRegistration = query.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, _ ->
            if (snapshot == null || snapshot.isEmpty)
                return@addSnapshotListener

            for (dc in snapshot.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        viewModelScope.launch {
                            if (!roomDatabase.checkChatRoomExists(dc.document.id)) {
                                getGamer(dc.document.id).collect { result ->
                                    if (result is Resource.Success) {

                                        var lastMessage: String
                                        var lastTimestamp: String
                                        if ((dc.document.get("timestamp") as Timestamp).toDate().time
                                            > roomDatabase.getLastTimestamp(dc.document.id)
                                                ?.toHttpDateOrNull()?.time ?: 0
                                        ) {
                                            lastMessage = dc.document.get("lastMessage") as String
                                            lastTimestamp =
                                                (dc.document.get("timestamp") as Timestamp).toDate()
                                                    .toHttpDateString()
                                        } else {
                                            lastMessage =
                                                roomDatabase.getLastMessageFromDatabase(dc.document.id)
                                            lastTimestamp =
                                                roomDatabase.getLastTimestamp(dc.document.id) ?: ""
                                        }


                                        if (navController.currentDestination?.route
                                            == Screen.ChatScreen.route
                                            && GamerDetail.firebaseId == dc.document.id
                                        ) {
                                            val chatRoom =
                                                ChatRoom(
                                                    firebaseId = dc.document.id,
                                                    lastMessage = lastMessage,
                                                    timestamp = lastTimestamp,
                                                    result.data!!.username,
                                                    result.data.avatar,
                                                    result.data.tier,
                                                    result.data.role,
                                                    false,
                                                    result.data.server,
                                                    result.data.team,
                                                    result.data.language,
                                                    result.data.champions
                                                )
                                            roomDatabase.insertChatRoom(chatRoom)
                                        } else {
                                            val chatRoom =
                                                ChatRoom(
                                                    firebaseId = dc.document.id,
                                                    lastMessage = lastMessage,
                                                    timestamp = lastTimestamp,
                                                    result.data!!.username,
                                                    result.data.avatar,
                                                    result.data.tier,
                                                    result.data.role,
                                                    true,
                                                    result.data.server,
                                                    result.data.team,
                                                    result.data.language,
                                                    result.data.champions
                                                )
                                            roomDatabase.insertChatRoom(chatRoom)
                                            BottomNavigationItem.Chat.badge.value = true
                                            mediaPlayer?.start()
                                        }
                                    }
                                }
                            }
                        }
                    }

                    DocumentChange.Type.MODIFIED -> {
                        viewModelScope.launch {
                            getGamer(dc.document.id).collect { result ->
                                if (result is Resource.Success) {
                                    var lastMessage: String
                                    var lastTimestamp: String


                                        if ((dc.document.get("timestamp") as Timestamp).toDate().time
                                            > roomDatabase.getLastTimestamp(dc.document.id)
                                                ?.toHttpDateOrNull()?.time ?: 0
                                        ) {
                                            lastMessage = dc.document.get("lastMessage") as String
                                            lastTimestamp =
                                                (dc.document.get("timestamp") as Timestamp).toDate()
                                                    .toHttpDateString()
                                        } else {
                                            lastMessage =
                                                roomDatabase.getLastMessageFromDatabase(dc.document.id)
                                            lastTimestamp =
                                                roomDatabase.getLastTimestamp(dc.document.id) ?: ""
                                        }


                                    if (navController.currentDestination?.route
                                        == Screen.ChatScreen.route
                                        && GamerDetail.firebaseId == dc.document.id
                                    ) {
                                        val chatRoom =
                                            ChatRoom(
                                                firebaseId = dc.document.id,
                                                lastMessage,
                                                timestamp = lastTimestamp,
                                                result.data!!.username,
                                                result.data.avatar,
                                                result.data.tier,
                                                result.data.role,
                                                false,
                                                result.data.server,
                                                result.data.team,
                                                result.data.language,
                                                result.data.champions
                                            )
                                        roomDatabase.insertChatRoom(chatRoom)
                                    } else {
                                        val chatRoom =
                                            ChatRoom(
                                                firebaseId = dc.document.id,
                                                lastMessage = lastMessage,
                                                timestamp = lastTimestamp,
                                                result.data!!.username,
                                                result.data.avatar,
                                                result.data.tier,
                                                result.data.role,
                                                true,
                                                result.data.server,
                                                result.data.team,
                                                result.data.language,
                                                result.data.champions
                                            )
                                        roomDatabase.insertChatRoom(chatRoom)
                                        mediaPlayer?.start()
                                        BottomNavigationItem.Chat.badge.value = true
                                    }
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}



