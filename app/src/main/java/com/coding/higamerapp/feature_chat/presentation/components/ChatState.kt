package com.coding.higamerapp.feature_chat.presentation.components

import com.coding.higamerapp.feature_chat.model.ChatMessage

data class ChatState(
    var messages: List<ChatMessage> = emptyList(),
)