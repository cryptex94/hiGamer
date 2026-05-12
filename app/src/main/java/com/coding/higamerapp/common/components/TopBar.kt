package com.coding.higamerapp.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coding.higamerapp.ActivityViewModel
import com.coding.higamerapp.R
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.feature_setting.presentation.DialogLogout
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography
import kotlinx.coroutines.async

@ExperimentalMaterialApi
@Composable
fun TopBar(
    navController: NavController,
    activityViewModel: ActivityViewModel
) {

    val openDialogLogout = remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = DarkGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "hiGamer",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp),
                    style = Typography.h3
                )
            }
            Row {
                IconButton(
                    onClick = { openDialogLogout.value = true },
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Logout",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    if (openDialogLogout.value)
                        DialogLogout(
                            navController = navController,
                            openDialogLogout = openDialogLogout, hiltViewModel(), activityViewModel
                        )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TopBarLogin(
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = DarkGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "hiGamer",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp),
                    style = Typography.h3
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TopBarConfirmation(
    navController: NavController,
    viewModel: ProfileViewModel,
    scaffoldState: ScaffoldState
) {
    val courutineScope = rememberCoroutineScope()

    val selectedTier by remember { (viewModel.selectedTier) }
    val selectedServer by remember { (viewModel.selectedServer) }
    val selectedRole by remember { (viewModel.selectedRole) }
    val textInputName by remember { (viewModel.textNameInput) }

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = DarkGray
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Undone",
                        tint = RedOrange
                    )
                }
                Text(
                    text = "Set up your profile",
                    color = Color.White,
                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                    style = Typography.h3
                )
                IconButton(
                    onClick = {
                        courutineScope.async {
                            when {
                                selectedTier == -1 -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please, insert a tier.",
                                    )
                                }
                                selectedRole == -1 -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please, insert a role.",
                                    )
                                }
                                selectedServer == -1 -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please, select a server.",
                                    )
                                }
                                textInputName.isEmpty() -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please, insert a name.",
                                    )
                                }
                                textInputName.length > 30 -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Please, use a shorter name.",
                                    )
                                }
                                else -> {
                                    viewModel.saveDataStoreProfile()
                                    viewModel.postGamerToDatabase()
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
                )
                {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = RedOrange
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarNavigateUp(navController: NavController) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = DarkGray
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                )
                {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Undone",
                        tint = RedOrange
                    )
                }
                Text(
                    text = "hiGamer",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp, 0.dp),
                    style = Typography.h3
                )
            }
        }
    }
}
