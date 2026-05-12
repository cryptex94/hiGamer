package com.coding.higamerapp.feature_chat_list.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coding.higamerapp.common.util.Converters
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom

@TypeConverters(Converters::class)
@Database(
    entities = [
        ChatRoom::class
    ],
    version = 1
)


abstract class ChatRoomDatabase : RoomDatabase() {

    abstract val chatRoomDao: ChatRoomDao

    companion object {
        const val DATABASE_NAME = "chatroom_lol_db"
    }

}