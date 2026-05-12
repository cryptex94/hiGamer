package com.coding.higamerapp.feature_chat_list.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ChatRoom(
    @PrimaryKey(autoGenerate = false) val firebaseId: String,

    @ColumnInfo(name = "lastMessage") val lastMessage: String,

    val timestamp: String,

    val username: String,

    val avatar: Int,

    val tier: Int,

    val role: Int,

    val unreadMessage: Boolean,

    val server: Int,

    val team : Boolean,

    val language: Int?,

    val champions : List<Int?>?
)
