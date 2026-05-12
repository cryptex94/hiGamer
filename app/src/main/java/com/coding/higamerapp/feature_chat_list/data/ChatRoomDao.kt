package com.coding.higamerapp.feature_chat_list.data

import androidx.room.*
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatRoom(chatroom: ChatRoom)

    @Query("SELECT * FROM ChatRoom WHERE firebaseId =:firebaseId")
    suspend fun getChatRoomWithId(firebaseId: String): ChatRoom

    @Query("SELECT EXISTS(SELECT * FROM ChatRoom WHERE firebaseId = :id)")
    suspend fun checkChatRoomExists(id: String): Boolean

    @Query(value = "SELECT * FROM ChatRoom WHERE server = :server")
    fun getChatRoomsByServer(server: Int): Flow<List<ChatRoom>>

    @Query(value = "SELECT * FROM ChatRoom")
    fun getAllChatRooms(): List<ChatRoom>

    @Delete
    suspend fun deleteChatRoom(chatRoom: ChatRoom)

    @Query("UPDATE ChatRoom SET unreadMessage = :unreadMessage WHERE firebaseId = :firebaseId")
    suspend fun updateUnreadMessage(unreadMessage: Boolean, firebaseId: String)

    @Query("UPDATE ChatRoom SET username = :username, role = :role, avatar = :avatar, tier = :tier, server = :server, team = :team, language = :language, champions = :champions WHERE firebaseId = :firebaseId")
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

    @Query("SELECT lastMessage FROM ChatRoom WHERE firebaseId = :firebaseId")
    suspend fun getLastMessageFromDatabase(firebaseId: String): String


    @Query("SELECT timestamp FROM ChatRoom WHERE firebaseId = :firebaseId")
    suspend fun getLastTimestamp(firebaseId: String) : String?
}