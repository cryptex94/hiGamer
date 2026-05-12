package com.coding.higamerapp.feature_profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.roleList
import com.coding.higamerapp.common.util.Constants.serverList
import com.coding.higamerapp.common.util.Constants.tierList
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography
import kotlinx.coroutines.async

@ExperimentalMaterialApi
@Composable
fun SecondProfileScreen(
    viewModel: ProfileViewModel,
    scaffoldState: ScaffoldState
) {

    val courutineScope = rememberCoroutineScope()
    val teamMates by remember { (viewModel.teamMates) }
    val textInputName by remember { (viewModel.textNameInput) }

    val context = LocalContext.current

    var selectedTier by remember { mutableStateOf(tierList[viewModel.selectedTier.value]) }
    var selectedRole by remember { mutableStateOf(roleList[viewModel.selectedRole.value]) }
    var selectedServer by remember { mutableStateOf(serverList[viewModel.selectedServer.value]) }

    val nameError = stringResource(id = R.string.name_input_error)
    val nameSizeError = stringResource(id = R.string.name_size_error)

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.summoner_stats),
                style = Typography.h6,
                overflow = TextOverflow.Visible
            )
        }

            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = true),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = stringResource(id = R.string.tier),
                            fontWeight = FontWeight.Bold
                        )

                        ListItemPicker(
                            label = {
                                context.resources.getString(it)
                            },
                            value = selectedTier,
                            onValueChange = {
                                selectedTier = it
                                viewModel.setTier(tierList.indexOf(selectedTier))
                            },
                            list = tierList,
                            dividersColor = RedOrange,
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.role),
                            fontWeight = FontWeight.Bold,
                        )


                        ListItemPicker(
                            label = { context.resources.getString(it) },
                            value = selectedRole,
                            dividersColor = RedOrange,
                            onValueChange = {
                                selectedRole = it
                                viewModel.setRole(roleList.indexOf(it))
                            },
                            list = roleList
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Server",
                            fontWeight = FontWeight.Bold,
                        )

                        ListItemPicker(
                            label = { context.resources.getString(it) },
                            value = selectedServer,
                            onValueChange = {
                                selectedServer = it
                                viewModel.setServer(serverList.indexOf(it))
                            },
                            list = serverList,
                            dividersColor = RedOrange,
                        )
                    }
                }


                var openChampionsList = remember { mutableStateOf(false) }

                Button(
                    onClick = {
                        openChampionsList.value = true
                    },

                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = RedOrange,
                        contentColor = DarkGray
                    )
                )
                {
                    Text(
                        text = stringResource(id = R.string.best_champ),
                        style = Typography.button,
                        fontSize = 15.sp,

                        )
                }
                if (openChampionsList.value)
                    DialogChampionList(openChampionsList, viewModel)




                Spacer(modifier = Modifier.padding(0.dp, 15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {

                    Text(
                        text = stringResource(id = R.string.teammates),
                    )

                    Checkbox(
                        checked = teamMates,
                        onCheckedChange = {
                            viewModel.setTeamMates(teamMates)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = RedOrange,
                            uncheckedColor = Color.White,
                            checkmarkColor = DarkGray
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(0.dp, 30.dp))

            }

            Column(verticalArrangement = Arrangement.Bottom) {
                Button(
                    onClick = {
                        courutineScope.async {
                            when {
                                textInputName.isEmpty() -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = nameError
                                    )
                                }
                                textInputName.length > 30 -> {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = nameSizeError
                                    )
                                }
                                else -> {
                                    viewModel.saveDataStoreProfile()
                                    viewModel.postGamerToDatabase()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = RedOrange,
                        contentColor = DarkGray
                    )
                )
                {
                    Text(
                        text = stringResource(id = R.string.done),
                        style = Typography.button,
                        fontSize = 15.sp,

                        )
                }
            }
    }
}

