package com.coding.higamerapp.feature_gamers.domain.use_case.get_gamers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import javax.inject.Inject

class GetGamers @Inject constructor(
    private val repository: GamerRepository,
    private val size: Int,
    private val role: Int?,
    private val tier: Int?,
    private val server: Int?,
    private val team: Boolean?,
    private val language: Int?
) : PagingSource<Int, GamerDto>() {

    override fun getRefreshKey(state: PagingState<Int, GamerDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamerDto> {
        val position = params.key ?: 0
        return try {
            val gamers = repository.getGamers(position, size, tier, role, server, team, language)
            LoadResult.Page(
                data = gamers,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (gamers.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}