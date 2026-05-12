package com.coding.higamerapp.feature_profile.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.ui.theme.RedOrange
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable

fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    val pagerState = rememberPagerState()
    val connectionError = stringResource(id = R.string.connection_error)

    LaunchedEffect(key1 = true) {
        profileViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProfileViewModel.UiEventProfile.NavigateOn -> {
                    navController.navigate(Screen.HomeScreen.route)
                }
                is ProfileViewModel.UiEventProfile.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = connectionError,
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            HorizontalPager(
                count = 2,
                state = pagerState
            ) { index ->
                when (index) {
                    0 -> FirstProfileScreen(viewModel = profileViewModel)
                    1 -> SecondProfileScreen(
                        viewModel = profileViewModel,
                        scaffoldState = scaffoldState
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = RedOrange
            )
        }
    }
}