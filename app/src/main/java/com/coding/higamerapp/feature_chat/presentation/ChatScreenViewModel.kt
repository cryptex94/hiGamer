package com.coding.higamerapp.feature_chat.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.higamerapp.feature_chat.data.data_source.GamerDetail
import com.coding.higamerapp.feature_chat.model.ChatMessage
import com.coding.higamerapp.feature_chat.presentation.components.ChatState
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.http.toHttpDateString
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val roomDatabase: ChatRoomRepository,
) : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var chatRegistration: ListenerRegistration? = null


    private val _stateMessage = mutableStateOf(ChatState())
    val stateMessage: MutableState<ChatState> = _stateMessage

    private val replacement: MutableList<ChatMessage> = mutableListOf()


    init {
        loadMessages()
        listenForChatMessages()
    }


    fun sendChatMessage(message: String) {
        val timestamp = Timestamp.now()
        val chatRoom = ChatRoom(
            GamerDetail.firebaseId!!,
            message,
            timestamp.toDate().toHttpDateString(),
            GamerDetail.username!!,
            GamerDetail.avatar!!,
            GamerDetail.tier!!,
            GamerDetail.role!!,
            false,
            GamerDetail.server!!,
            GamerDetail.team!!,
            GamerDetail.language,
            GamerDetail.champions
        )
        viewModelScope.launch {
            roomDatabase.insertChatRoom(chatroom = chatRoom)
        }
        firestore.collection("rooms").document(GamerDetail.firebaseId!!).collection("contacts")
            .document(
                myFirebaseId!!
            ).collection("messages")
            .get().continueWith {

                firestore.collection("rooms").document(GamerDetail.firebaseId!!).collection(
                    "contacts"
                ).document(myFirebaseId!!).collection("messages")
                    .add(
                        mapOf(
                            Pair("text", message),
                            Pair("timestamp", timestamp)
                        )
                    )

                _stateMessage.value = ChatState(
                    listOf(
                        ChatMessage(
                            message,
                            timestamp,
                            true
                        )
                    )
                )
                _stateMessage.value.messages.lastOrNull()?.let { replacement.add(it) }
                _stateMessage.value.messages = replacement
            }

        firestore.collection("rooms").document(GamerDetail.firebaseId!!).collection("notify")
            .document(
                myFirebaseId!!
            ).set(
                mapOf(
                    Pair("lastMessage", message),
                    Pair("timestamp", timestamp)
                )
            )
    }

    private fun listenForChatMessages() {
        chatRegistration = firestore.collection("rooms")
            .document(myFirebaseId!!).collection("contacts").document(GamerDetail.firebaseId!!)
            .collection("messages")
            .addSnapshotListener { messageSnapshot, _ ->
                if (messageSnapshot == null || messageSnapshot.isEmpty)
                    return@addSnapshotListener


                for (messageDocument in messageSnapshot.documentChanges) {
                    when (messageDocument.type) {
                        DocumentChange.Type.ADDED -> {
                            _stateMessage.value = ChatState(
                                listOf(
                                    ChatMessage(
                                        messageDocument.document.getString("text") as String,
                                        (messageDocument.document.get("timestamp") as Timestamp),
                                        false
                                    )
                                )
                            )
                            _stateMessage.value.messages.lastOrNull()?.let { replacement.add(it) }
                        }
                        else -> {}
                    }
                }
                _stateMessage.value.messages = replacement
            }
    }

    private fun loadMessages() {
        firestore.collection("rooms").document(GamerDetail.firebaseId!!).collection("contacts")
            .document(myFirebaseId!!).collection("messages").get()
            .addOnSuccessListener { messages ->
                messages.forEach { document ->
                    _stateMessage.value = ChatState(
                        listOf(
                            ChatMessage(
                                document.getString("text") as String,
                                (document.get("timestamp") as Timestamp),
                                true
                            )
                        )
                    )
                    _stateMessage.value.messages.lastOrNull()?.let { replacement.add(it) }
                    _stateMessage.value.messages = replacement
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        chatRegistration?.remove()
    }
}


