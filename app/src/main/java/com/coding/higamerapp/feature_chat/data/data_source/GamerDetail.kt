package com.coding.higamerapp.feature_chat.data.data_source

import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto

object GamerDetail {
    var username: String? = null
    var role: Int? = null
    var server: Int? = null
    var tier: Int? = null
    var avatar: Int? = null
    var team: Boolean? = null
    var firebaseId: String? = null
    var language : Int? = null
    var champions: List<Int?>? = null

    fun setGamerDetail(entry: GamerDto) {
        username = entry.username
        role = entry.role
        server = entry.server
        tier = entry.tier
        avatar = entry.avatar
        team = entry.team
        firebaseId = entry.firebaseId
        language = entry.language
        champions = entry.champions
    }

    fun setGamerDetail(entry: ChatRoom) {
        username = entry.username
        role = entry.role
        server = entry.server
        tier = entry.tier
        avatar = entry.avatar
        team = entry.team
        firebaseId = entry.firebaseId
        language = entry.language
        champions = entry.champions
    }

    fun gamerDetailToGamerDto(): GamerDto {
        return GamerDto(
            username!!,
            role = role!!,
            server = server!!,
            lookingFor = null,
            tier = tier!!,
            avatar = avatar!!,
            team = team!!,
            firebaseId = firebaseId!!,
            language = language,
            champions = champions,
        )
    }
}