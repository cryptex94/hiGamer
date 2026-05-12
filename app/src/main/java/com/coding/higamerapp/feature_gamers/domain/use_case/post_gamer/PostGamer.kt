package com.coding.higamerapp.feature_gamers.domain.use_case.post_gamer

import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PostGamer @Inject constructor(
    private val repository: GamerRepository
) {
    operator fun invoke(gamerProfile: ProfileDto): Flow<Resource<Response<ProfileDto>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val gamer = repository.postGamer(gamerProfile)
                emit(Resource.Success(gamer))
            } catch (e: HttpException) {
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }
    }
}