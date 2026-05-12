package com.coding.higamerapp.feature_login.presentation

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.higamerapp.R
import com.coding.higamerapp.common.Resource
import com.coding.higamerapp.feature_gamers.domain.use_case.post_gamer.PostGamer
import com.coding.higamerapp.feature_login.presentation.util.AuthResultCode
import com.coding.higamerapp.feature_login.presentation.util.UserRepository
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.coding.higamerapp.feature_profile.domain.model.ProfileDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    userRepository: UserRepository,
    private val postGamer: PostGamer,
) : FirebaseAuthManager, ViewModel() {
    //Used to decide if we need to launch the login intent
    private val _isAnonymousUser = MutableStateFlow(userRepository())
    val isAnonymousUser = _isAnonymousUser.asStateFlow()

    //Used to perform appropriate action based on the login result
    private val _authResultCode = MutableStateFlow(AuthResultCode.NOT_APPLICABLE)
    val authResultCode = _authResultCode.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    override fun buildLoginIntent(): Intent {
        return AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.FacebookBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .enableAnonymousUsersAutoUpgrade()
            .setIsSmartLockEnabled(false)
            .setTheme(R.style.SplashScreenTheme)
            .build()
    }

    override fun onLoginResult(result: FirebaseAuthUIAuthenticationResult) {

        val response: IdpResponse? = result.idpResponse

        if (result.resultCode == Activity.RESULT_OK) {
            _isAnonymousUser.value = false
            _authResultCode.value = AuthResultCode.OK
            return
        }


        val userPressedBackButton = (response == null)
        if (userPressedBackButton) {
            _authResultCode.value = AuthResultCode.CANCELLED
            return
        }

        when (response?.error?.errorCode) {
            ErrorCodes.NO_NETWORK -> {
                _authResultCode.value = AuthResultCode.NO_NETWORK
            }
            /*           ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> {

                           val nonAnonymousCredForLinking: AuthCredential =
                               response.credentialForLinking!!

                           viewModelScope.launch {
                               handleMergeConflict(nonAnonymousCredForLinking = nonAnonymousCredForLinking)
                           }
                       }
             */
            else -> {
                _authResultCode.value = AuthResultCode.ERROR
            }
        }
    }


    suspend fun postGamerToDatabase() {

        val gamer = ProfileDto(
            username = Profile.name,
            role = Profile.role,
            firebaseId = UserRepository.myFirebaseId,
            tier = Profile.tier,
            avatar = Profile.avatar,
            server = Profile.server,
            team = Profile.team,
            language = Profile.language,
            champions = Profile.champions
        )

        try {
            postGamer(gamer).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() != 200) {
                            if (result.data?.code() == 400) {
                                _eventFlow.emit(UiEvent.Navigate)

                            } else {
                                _eventFlow.emit(UiEvent.ShowSnackBar)
                            }
                        } else {
                            _eventFlow.emit(UiEvent.Navigate)
                        }
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnackBar)
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            _eventFlow.emit(UiEvent.ShowSnackBar)
        }
    }

    fun failureEvent() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackBar)
        }
    }

    sealed class UiEvent {
        object ShowSnackBar : UiEvent()
        object Navigate : UiEvent()
    }
}