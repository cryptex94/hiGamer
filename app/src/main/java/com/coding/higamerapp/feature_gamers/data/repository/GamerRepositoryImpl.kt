package com.coding.higamerapp.feature_gamers.data.repository

import com.coding.higamerapp.feature_gamers.data.data_source.hiGamerApi
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import retrofit2.Response
import javax.inject.Inject

class GamerRepositoryImpl @Inject constructor(
    private val api: hiGamerApi
) : GamerRepository {

    override suspend fun getGamers(
        pageNumber: Int,
        size: Int,
        tier: Int?,
        role: Int?,
        server: Int?,
        team: Boolean?,
        language: Int?
    ): List<GamerDto> {
        return api.getGamers(pageNumber, size, tier, role, server, team, language)
    }

    override suspend fun getGamerById(firebaseId: String): GamerDto {
        return api.getGamerById(firebaseId)
    }

    override suspend fun deleteGamerById(firebaseId: String): Response<Unit> {
        return api.deleteGamerById(firebaseId)
    }

    override suspend fun updateGamerById(
        firebaseId: String,
        gamer: ProfileDto
    ): Response<ProfileDto> {
        return api.updateGamerById(firebaseId, gamer)
    }

    override suspend fun postGamer(gamerProfile: ProfileDto): Response<ProfileDto> {
        return api.postGamer(gamerProfile)
    }
}