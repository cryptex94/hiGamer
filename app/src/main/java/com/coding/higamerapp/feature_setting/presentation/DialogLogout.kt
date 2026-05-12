package com.coding.higamerapp.feature_setting.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.coding.higamerapp.ActivityViewModel
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.feature_setting.LogoutViewModel
import com.coding.higamerapp.ui.theme.LightRed
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun DialogLogout(
    navController: NavController,
    openDialogLogout: MutableState<Boolean>,
    logoutViewModel: LogoutViewModel,
    activityViewModel: ActivityViewModel
) {
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = {
            openDialogLogout.value = false
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { openDialogLogout.value = false }) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = LightRed
                        )
                    }
                    TextButton(onClick = {
                        scope.launch {
                            logoutViewModel.signOut()
                            activityViewModel.roomRegistration?.remove()
                            activityViewModel.stopRepeatingTask()
                            navController.popBackStack()
                            navController.navigate(Screen.LoginScreen.route)
                            openDialogLogout.value = false
                        }
                    }) {
                        Text(
                            text = "LOG OUT",
                            color = LightRed
                        )
                    }

                }
            }
        }

    }
}