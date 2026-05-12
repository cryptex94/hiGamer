package com.coding.higamerapp.feature_chat.model

import com.google.firebase.Timestamp

data class ChatMessage(
    val text: String,
    val timestamp: Timestamp,
    val isMine: Boolean
)