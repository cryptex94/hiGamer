package com.coding.higamerapp.feature_profile.domain.model

object Profile {
    var name: String? = null
    var role: Int? = null
    var server: Int? = null
    var tier: Int? = null
    var avatar: Int? = null
    var team: Boolean? = null
    var language: Int? = null
    var champions: List<Int?>? = null
}

data class ProfileDto(
    val username: String?,
    val role: Int?,
    val firebaseId: String?,
    val server: Int?,
    val tier: Int?,
    val avatar: Int?,
    var team: Boolean?,
    var language: Int?,
    var champions: List<Int?>?
)