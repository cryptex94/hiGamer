package com.coding.higamerapp.feature_gamers.presentation.gamers_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.QUERY_PAGE_SIZE
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import com.coding.higamerapp.feature_gamers.domain.use_case.get_gamers.GetGamers
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class GamerListViewModel @Inject constructor(
    private val repository: GamerRepository,
) : ViewModel() {

    var gamers: Flow<PagingData<GamerDto>> = Pager(PagingConfig(pageSize = QUERY_PAGE_SIZE)) {
        GetGamers(
            repository,
            QUERY_PAGE_SIZE,
            _searchingRole,
            _searchingTier,
            Profile.server,
            null,
            _searchingLang
        )
    }.flow
        .cachedIn(viewModelScope)
        .map { list ->
            list.filter {
                it.firebaseId != myID
            }
        }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private var _searchingRole: Int? = null

    private var _searchingTier: Int? = null

    private var _searchingLang: Int? = null

    private val myID = FirebaseAuth.getInstance().currentUser?.uid

    private val _textFilterTier = mutableStateOf(R.string.tier)
    val textFilterTier: MutableState<Int> = _textFilterTier

    private val _textFilterRole = mutableStateOf(R.string.role)
    val textFilterRole: MutableState<Int> = _textFilterRole

    private val _textFilterLang = mutableStateOf(R.string.language)
    val textFilterLang: MutableState<Int> = _textFilterLang


    private suspend fun getGamers() {
        _isRefreshing.emit(true)
        try {
            gamers = Pager(PagingConfig(pageSize = QUERY_PAGE_SIZE)) {
                GetGamers(
                    repository,
                    QUERY_PAGE_SIZE,
                    _searchingRole,
                    _searchingTier,
                    Profile.server,
                    null,
                    _searchingLang
                )
            }.flow
                .cachedIn(viewModelScope)
                .map { list ->
                    list.filter {
                        it.firebaseId != myID
                    }
                }
            _isRefreshing.emit(false)
        } catch (e: IOException) {
            _isRefreshing.emit(false)
        }
    }


    fun setSearchingTier(index: Int?) {
        _searchingTier = index
    }

    fun setSearchingRole(index: Int?) {
        _searchingRole = index
    }

    fun setSearchingLang(index: Int?) {
        _searchingLang = index
    }

    fun setTextFilterTier(index: Int) {
        when (index) {
            0 -> _textFilterTier.value = R.string.iron
            1 -> _textFilterTier.value = R.string.bronze
            2 -> _textFilterTier.value = R.string.silver
            3 -> _textFilterTier.value = R.string.gold
            4 -> _textFilterTier.value = R.string.platinum
            5 -> _textFilterTier.value = R.string.diamond
            6 -> _textFilterTier.value = R.string.tier
        }
    }

    fun setTextFilterRole(index: Int) {
        when (index) {
            0 -> _textFilterRole.value = R.string.top
            1 -> _textFilterRole.value = R.string.jungle
            2 -> _textFilterRole.value = R.string.mid
            3 -> _textFilterRole.value = R.string.adc
            4 -> _textFilterRole.value = R.string.support
            5 -> _textFilterRole.value = R.string.role
        }
    }

    fun setTextFilterLang(index: Int) {
        when (index) {
            0 -> _textFilterLang.value = R.string.english
            1 -> _textFilterLang.value = R.string.french
            2 -> _textFilterLang.value = R.string.spanish
            3 -> _textFilterLang.value = R.string.german
            4 -> _textFilterLang.value = R.string.italian
            5 -> _textFilterLang.value = R.string.portuguese
            6 -> _textFilterLang.value = R.string.polish
            7 -> _textFilterLang.value = R.string.russian
            8 -> _textFilterLang.value = R.string.korean
            9 -> _textFilterLang.value = R.string.chinese
            10 -> _textFilterLang.value = R.string.arabic
            11 -> _textFilterLang.value = R.string.language

        }
    }

}