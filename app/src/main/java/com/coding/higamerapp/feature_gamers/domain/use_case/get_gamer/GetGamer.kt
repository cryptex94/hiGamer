package com.coding.higamerapp.feature_gamers.domain.use_case.get_gamer

import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGamer @Inject constructor(
    private val repository: GamerRepository
) {
    operator fun invoke(firebaseId: String): Flow<Resource<GamerDto>> {
        return flow {
            try {
                emit(Resource.Loading())
                val gamer = repository.getGamerById(firebaseId)
                emit(Resource.Success(gamer))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An expected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }
    }
}