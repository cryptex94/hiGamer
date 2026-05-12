package com.coding.higamerapp.feature_chat_list.presentation.chatlist_states

import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom

data class ChatListStates(
    var rooms: List<ChatRoom> = emptyList()
)