package com.coding.higamerapp.feature_profile.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.common.util.Constants.PROFILE_AVATAR
import com.coding.higamerapp.common.util.Constants.PROFILE_CHAMPIONS
import com.coding.higamerapp.common.util.Constants.PROFILE_LANGUAGE
import com.coding.higamerapp.common.util.Constants.PROFILE_NAME
import com.coding.higamerapp.common.util.Constants.PROFILE_ROLE
import com.coding.higamerapp.common.util.Constants.PROFILE_SERVER
import com.coding.higamerapp.common.util.Constants.PROFILE_TEAM
import com.coding.higamerapp.common.util.Constants.PROFILE_TIER
import com.coding.higamerapp.feature_gamers.domain.use_case.post_gamer.PostGamer
import com.coding.higamerapp.feature_gamers.domain.use_case.update_gamer.UpdateGamer
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.coding.higamerapp.feature_profile.data.DataStoreManager
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val updateGamer: UpdateGamer,
    private val postGamer: PostGamer,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEventProfile>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _textNameInput = mutableStateOf(Profile.name ?: "")
    val textNameInput: MutableState<String> = _textNameInput

    private val _selectedServer = mutableStateOf(Profile.server ?: 0)
    val selectedServer: MutableState<Int> = _selectedServer

    private val _selectedRole = mutableStateOf(Profile.role ?: 0)
    val selectedRole: MutableState<Int> = _selectedRole

    private val _selectedTier = mutableStateOf(Profile.tier ?: 0)
    val selectedTier: MutableState<Int> = _selectedTier

    private val _selectedAvatar = mutableStateOf(Profile.avatar ?: 0)
    val selectedAvatar: MutableState<Int> = _selectedAvatar

    private val _selectedLanguage = mutableStateOf(Profile.language)
    val selectedLanguage: MutableState<Int?> = _selectedLanguage

    private val _bestChamp = mutableStateOf(Profile.champions)
    val bestChamp: MutableState<List<Int?>?> = _bestChamp

    private val _teamMates = mutableStateOf(Profile.team ?: false)
    val teamMates: MutableState<Boolean> = _teamMates

    suspend fun saveDataStoreProfile() {
        if (_textNameInput.value != "") {
            dataStoreManager.storeValue(PROFILE_NAME, _textNameInput.value)
            dataStoreManager.storeValue(PROFILE_ROLE, _selectedRole.value)
            dataStoreManager.storeValue(PROFILE_TIER, _selectedTier.value)
            dataStoreManager.storeValue(PROFILE_TEAM, _teamMates.value)
            dataStoreManager.storeValue(PROFILE_AVATAR, _selectedAvatar.value)
            dataStoreManager.storeValue(PROFILE_SERVER, _selectedServer.value)
            dataStoreManager.storeValue(PROFILE_LANGUAGE, _selectedLanguage.value.toString())
            dataStoreManager.storeValue(PROFILE_CHAMPIONS, _bestChamp.value.toString())


            Profile.name = _textNameInput.value
            Profile.role = _selectedRole.value
            Profile.tier = _selectedTier.value
            Profile.server = _selectedServer.value
            Profile.avatar = _selectedAvatar.value
            Profile.team = _teamMates.value
            Profile.language = _selectedLanguage.value
            Profile.champions = _bestChamp.value

        }
    }

    fun setTier(index: Int) {
        _selectedTier.value = index
    }

    fun setRole(index: Int) {
        _selectedRole.value = index
    }

    fun setName(name: String) {
        _textNameInput.value = name
    }

    fun setServer(index: Int) {
        _selectedServer.value = index
    }

    fun setLanguage(index: Int?) {
        _selectedLanguage.value = index
    }

    fun setTeamMates(checked: Boolean) {
        _teamMates.value = !checked
    }

    fun setAvatar(index: Int) {
        _selectedAvatar.value = index
    }

    fun setBestChamp(list: List<Int?>?) {
        _bestChamp.value = list
    }

    suspend fun postGamerToDatabase() {
        val gamer = ProfileDto(
            username = Profile.name,
            role = Profile.role,
            firebaseId = myFirebaseId,
            tier = Profile.tier,
            avatar = Profile.avatar,
            server = Profile.server,
            team = Profile.team,
            language = Profile.language,
            champions = Profile.champions
        )

        postGamer(gamer).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data?.code() != 200) {
                        if (result.data?.code() == 400) {
                            updateGamerOnDatabase()
                        } else {
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventProfile.ShowSnackBar)
                            }
                        }
                    } else {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventProfile.NavigateOn)
                        }
                    }
                }
                is Resource.Error -> {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEventProfile.ShowSnackBar)
                    }
                }
                else -> {}
            }
        }
    }


    private suspend fun updateGamerOnDatabase() {
        myFirebaseId?.let {
            val gamer = ProfileDto(
                username = _textNameInput.value,
                role = _selectedRole.value,
                firebaseId = myFirebaseId,
                tier = _selectedTier.value,
                avatar = _selectedAvatar.value,
                server = _selectedServer.value,
                team = _teamMates.value,
                language = Profile.language,
                champions = Profile.champions,
            )

            updateGamer(myFirebaseId!!, gamer).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() != 200) {
                            if (result.data?.code() == 404) {
                                postGamerToDatabase()
                            } else {
                                viewModelScope.launch {
                                    _eventFlow.emit(UiEventProfile.ShowSnackBar)
                                }
                            }
                        } else {
                            viewModelScope.launch {
                                _eventFlow.emit(UiEventProfile.NavigateOn)
                            }
                        }
                    }
                    is Resource.Error -> {
                        viewModelScope.launch {
                            _eventFlow.emit(UiEventProfile.ShowSnackBar)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    sealed class UiEventProfile {
        object NavigateOn : UiEventProfile()
        object ShowSnackBar : UiEventProfile()
    }
}