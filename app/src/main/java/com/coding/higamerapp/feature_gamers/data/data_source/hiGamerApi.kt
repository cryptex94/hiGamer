package com.coding.higamerapp.feature_gamers.data.data_source

import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import retrofit2.Response
import retrofit2.http.*

interface hiGamerApi {
    @GET("/api/v1/league_of_legends/gamers")
    suspend fun getGamers(
        @Query("page") pageNumber: Int,
        @Query("size") size: Int,
        @Query("tier") tier: Int?,
        @Query("role") role: Int?,
        @Query("server") server: Int?,
        @Query("team") team: Boolean?,
        @Query("language") language: Int?
    ): List<GamerDto>

    @GET("/api/v1/league_of_legends/gamers/{firebaseId}")
    suspend fun getGamerById(@Path("firebaseId") firebaseId: String): GamerDto

    @DELETE("/api/v1/league_of_legends/gamers/{firebaseId}")
    suspend fun deleteGamerById(@Path("firebaseId") firebaseId: String): Response<Unit>

    @POST("/api/v1/league_of_legends/gamers")
    suspend fun postGamer(@Body gamerProfile: ProfileDto): Response<ProfileDto>

    @PUT("/api/v1/league_of_legends/gamers/{firebaseId}")
    suspend fun updateGamerById(
        @Path("firebaseId") firebaseId: String,
        @Body gamerProfile: ProfileDto
    ): Response<ProfileDto>

}