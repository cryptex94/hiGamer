package com.coding.higamerapp.feature_chat_list.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import com.coding.higamerapp.feature_chat_list.presentation.chatlist_states.ChatListStates
import com.coding.higamerapp.feature_gamers.domain.use_case.get_gamer.GetGamer
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val roomDatabase: ChatRoomRepository,
    private val getGamer: GetGamer
) : ViewModel() {

    private val _state = mutableStateOf(ChatListStates())
    val state: MutableState<ChatListStates> = _state
    private var getRoomsJob: Job? = null

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private var recentlyDeletedChat: ChatRoom? = null

    init {
        viewModelScope.launch {
            getChatList()
            delay(100)
            updateGamersInfo()
        }
    }


    suspend fun updateUnreadMessage(unreadMessage: Boolean, firebaseId: String) {
        roomDatabase.updateUnreadMessage(unreadMessage, firebaseId)
    }

    fun getChatList() {
        getRoomsJob?.cancel()
        _state.value.rooms = emptyList()
        getRoomsJob = roomDatabase.getChatRoomsByServer(Profile.server!!)
            .onEach { rooms ->
                _isRefreshing.emit(true)
                _state.value = state.value.copy(
                    rooms = rooms,
                )
                _isRefreshing.emit(false)
            }.launchIn(viewModelScope)
    }

    suspend fun updateGamersInfo() {
        withContext(Dispatchers.IO) {
            roomDatabase.getAllChatRooms().forEach {
                getGamer(it.firebaseId).collectLatest { result ->
                    if (result is Resource.Success) {
                        roomDatabase.updateGamerInfo(
                            result.data!!.username,
                            result.data.role,
                            result.data.avatar,
                            result.data.tier,
                            result.data.server,
                            result.data.team,
                            result.data.language,
                            result.data.champions,
                            it.firebaseId
                        )
                    }
                }
            }
        }
    }

    fun deleteNotifyFromFirestore(firebaseId: String) {
        firestore.collection("rooms").document(myFirebaseId!!).collection("notify")
            .document(firebaseId).delete()
    }


    fun deleteChatRoom(chatRoom: ChatRoom) {
        viewModelScope.launch {
            roomDatabase.deleteChatRoom(chatroom = chatRoom)
            recentlyDeletedChat = chatRoom
        }
    }

    fun restoreChatRoom() {
        viewModelScope.launch {
            roomDatabase.insertChatRoom(recentlyDeletedChat ?: return@launch)
            recentlyDeletedChat = null
        }
    }
}