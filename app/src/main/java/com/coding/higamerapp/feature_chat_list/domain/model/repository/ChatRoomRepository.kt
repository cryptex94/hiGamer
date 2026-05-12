package com.coding.higamerapp.feature_chat_list.domain.model.repository

import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import kotlinx.coroutines.flow.Flow

interface ChatRoomRepository {

    fun getChatRoomsByServer(server: Int): Flow<List<ChatRoom>>

    suspend fun getChatRoomById(id: String): ChatRoom?

    fun getAllChatRooms(): List<ChatRoom>

    suspend fun checkChatRoomExists(id: String): Boolean

    suspend fun insertChatRoom(chatroom: ChatRoom)

    suspend fun deleteChatRoom(chatroom: ChatRoom)

    suspend fun updateUnreadMessage(unreadMessage: Boolean, firebaseId: String)

    suspend fun updateGamerInfo(
        username: String,
        role: Int,
        avatar: Int,
        tier: Int,
        server: Int,
        team : Boolean,
        language : Int?,
        champions : List<Int?>?,
        firebaseId: String
    )

     suspend fun getLastMessageFromDatabase(firebaseId: String): String

     suspend fun getLastTimestamp(firebaseId: String) : String?

}