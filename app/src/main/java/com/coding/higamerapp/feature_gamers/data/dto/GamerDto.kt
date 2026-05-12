package com.coding.higamerapp.feature_gamers.data.dto

data class GamerDto(
    val username: String,
    val role: Int,
    val tier: Int,
    val server: Int,
    val lookingFor: Int?,
    val team: Boolean,
    val firebaseId: String,
    val language: Int?,
    val avatar: Int,
    val champions: List<Int?>?
)
