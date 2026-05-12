package com.coding.higamerapp.feature_chat_list.domain.use_case

import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRooms @Inject constructor(
    private val repository: ChatRoomRepository
) {

    operator fun invoke(server: Int): Flow<List<ChatRoom>> = flow {
        repository.getChatRoomsByServer(server).map { rooms ->
            rooms.sortedBy { it.timestamp }
        }
    }
}
