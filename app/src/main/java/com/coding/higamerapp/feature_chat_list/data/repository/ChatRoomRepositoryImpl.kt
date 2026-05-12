package com.coding.higamerapp.feature_chat_list.data.repository

import com.coding.higamerapp.feature_chat_list.data.ChatRoomDao
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class ChatRoomRepositoryImpl(
    private val dao: ChatRoomDao
) : ChatRoomRepository {


    override fun getChatRoomsByServer(server: Int): Flow<List<ChatRoom>> {
        return dao.getChatRoomsByServer(server)
    }

    override suspend fun getChatRoomById(id: String): ChatRoom? {
        return dao.getChatRoomWithId(id)
    }

    override fun getAllChatRooms(): List<ChatRoom> {
        return dao.getAllChatRooms()
    }

    override suspend fun checkChatRoomExists(id: String): Boolean {
        return dao.checkChatRoomExists(id)
    }


    override suspend fun insertChatRoom(chatroom: ChatRoom) {
        return dao.insertChatRoom(chatroom = chatroom)
    }

    override suspend fun deleteChatRoom(chatroom: ChatRoom) {
        return dao.deleteChatRoom(chatRoom = chatroom)
    }

    override suspend fun updateUnreadMessage(unreadMessage: Boolean, firebaseId: String) {
        return dao.updateUnreadMessage(unreadMessage, firebaseId)
    }

    override suspend fun updateGamerInfo(
        username: String,
        role: Int,
        avatar: Int,
        tier: Int,
        server: Int,
        team : Boolean,
        language : Int?,
        champions : List<Int?>?,
        firebaseId: String
    ) {
        return dao.updateGamerInfo(username, role, avatar, tier, server, team, language, champions,firebaseId)
    }

    override suspend fun getLastMessageFromDatabase(firebaseId: String): String {
        return dao.getLastMessageFromDatabase(firebaseId = firebaseId)
    }

    override suspend fun getLastTimestamp(firebaseId: String) : String? {
        return dao.getLastTimestamp(firebaseId)
    }

}