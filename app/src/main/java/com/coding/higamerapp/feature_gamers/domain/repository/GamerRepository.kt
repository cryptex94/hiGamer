package com.coding.higamerapp.feature_gamers.domain.repository

import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import retrofit2.Response

interface GamerRepository {

    suspend fun getGamers(
        pageNumber: Int,
        size: Int,
        tier: Int?,
        role: Int?,
        server: Int?,
        team: Boolean?,
        language: Int?
    ): List<GamerDto>

    suspend fun getGamerById(firebaseId: String): GamerDto

    suspend fun deleteGamerById(firebaseId: String): Response<Unit>

    suspend fun updateGamerById(firebaseId: String, gamer: ProfileDto): Response<ProfileDto>

    suspend fun postGamer(gamerProfile: ProfileDto): Response<ProfileDto>
}