package com.coding.higamerapp.common.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coding.higamerapp.ActivityViewModel
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.feature_chat.presentation.components.ChatScreen
import com.coding.higamerapp.feature_chat_list.presentation.ChatListScreen
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.components.GamersScreen
import com.coding.higamerapp.feature_login.presentation.LoginScreen
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.feature_profile.presentation.components.ProfileScreen
import com.coding.higamerapp.feature_terms.TermsScreen
import com.coding.higamerapp.feature_terms.data.Terms
import com.coding.higamerapp.ui.theme.RedOrange
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyScreen(
    navController: NavHostController,
    activityViewModel: ActivityViewModel,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()
    val profileViewModel: ProfileViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            when (currentDestination) {
                Screen.ProfileScreen.route -> {}
                Screen.ChatScreen.route, Screen.SettingScreen.route -> TopBarNavigateUp(
                    navController = navController
                )
                Screen.LoginScreen.route -> TopBarLogin()
                else -> TopBar(navController = navController, activityViewModel)
            }
        },
        bottomBar = {
            if (currentDestination != Screen.LoginScreen.route && currentDestination != Screen.ProfileScreen.route
            )
                BottomNavigationBar(navController = navController)
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = RedOrange,
                    snackbarData = data
                )
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.LoginScreen.route,
            Modifier.padding(paddingValues)
        ) {
            composable(Screen.LoginScreen.route) {
                if (Terms.terms == true) {
                    LoginScreen(
                        navController = navController,
                        viewModel = hiltViewModel(),
                        activityViewModel = activityViewModel,
                        scaffoldState
                    )
                } else TermsScreen(navController = navController)
            }
            composable(Screen.HomeScreen.route) {
                GamersScreen(navController = navController, adViewModel = hiltViewModel())
            }
            composable(Screen.ChatListScreen.route) {
                ChatListScreen(navController = navController, scaffoldState)
            }

            composable(Screen.ProfileScreen.route) {
                ProfileScreen(profileViewModel, navController, scaffoldState)
            }
            composable(Screen.ChatScreen.route) {
                ChatScreen()
            }
        }
    }
}