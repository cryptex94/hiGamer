package com.coding.higamerapp.feature_login.presentation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.coding.higamerapp.ActivityViewModel
import com.coding.higamerapp.MainActivity
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.feature_login.presentation.util.AuthResultCode
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.token
import com.coding.higamerapp.feature_profile.domain.model.Profile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@ExperimentalMaterialApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    activityViewModel: ActivityViewModel,
    scaffoldState: ScaffoldState
) {
    val courutine = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Error occurred. Check your connection and try restarting the app.",
                    )
                }
                is LoginViewModel.UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        val isAnonymousUser = viewModel.isAnonymousUser.collectAsState().value
        val authResultCode by viewModel.authResultCode.collectAsState()

        val loginLauncher = rememberLauncherForActivityResult(
            viewModel.buildLoginActivityResult()
        ) { result ->
            if (result != null) {
                viewModel.onLoginResult(result = result)
                if (result.resultCode == Activity.RESULT_OK) {
                    courutine.launch(Dispatchers.Main) {
                        val user =
                            FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.await()
                        myFirebaseId = FirebaseAuth.getInstance().currentUser?.uid
                        token = user?.token
                        navController.popBackStack()
                        activityViewModel.listenForChatRooms(navController)
                        if (Profile.name != null && Profile.role != null && Profile.tier != null
                            && Profile.server != null && Profile.team != null && Profile.avatar != null
                        ) {
                            //La post qui viene chiamata da OnResume
                            navController.navigate(Screen.HomeScreen.route)
                        } else {
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                    }
                }
            }
        }
        if (isAnonymousUser && authResultCode != AuthResultCode.CANCELLED) {
            LaunchedEffect(true) {
                loginLauncher.launch(viewModel.buildLoginIntent())
            }
        }
        if (authResultCode == AuthResultCode.CANCELLED) {
            val activity = LocalContext.current as MainActivity
            activity.finish()
        }

        LaunchedEffect(true) {
            if (isAnonymousUser) return@LaunchedEffect
            withContext(Dispatchers.IO) {
                try {
                    val user =
                        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.await()
                    myFirebaseId = FirebaseAuth.getInstance().currentUser?.uid
                    token = user?.token
                    activityViewModel.listenForChatRooms(navController)
                    if (Profile.name != null && Profile.role != null && Profile.tier != null
                        && Profile.server != null && Profile.team != null && Profile.avatar != null
                    ) {
                        viewModel.postGamerToDatabase()
                    } else {
                        withContext(Dispatchers.Main) {
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                    }
                } catch (e: Exception) {
                    viewModel.failureEvent()
                }
            }
        }
    }
}

