package com.coding.higamerapp.feature_profile.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.avatar
import com.coding.higamerapp.common.util.fromLangToAbbreviation
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FirstProfileScreen(
    viewModel: ProfileViewModel
) {
    val openDialogAvatar = remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val textInputName by remember { (viewModel.textNameInput) }
    val selectedLang by remember { (viewModel.selectedLanguage) }
    val selectedAvatar by remember { (viewModel.selectedAvatar) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val openDialogLang = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.set_up_profile),
                style = Typography.h6,
                overflow = TextOverflow.Visible
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusRequester.requestFocus()
                }
                .focusRequester(focusRequester)
                .verticalScroll(rememberScrollState())
                .focusable(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                onClick = { openDialogAvatar.value = true },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                border = BorderStroke(0.dp, Color.Transparent)
            )
            {
                if (openDialogAvatar.value)
                    DialogAvatar(openDialogAvatar = openDialogAvatar, viewModel = viewModel)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Avatar",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = RedOrange
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Image(
                        painter = painterResource(id = avatar[selectedAvatar]),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, CircleShape)

                    )
                }
            }

            Spacer(modifier = Modifier.padding(0.dp, 10.dp))

            TextField(
                value = textInputName,
                onValueChange = {
                    viewModel.setName(it)
                    isError = textInputName.length > 30
                },
                label = { Text(stringResource(id = R.string.name)) },
                modifier = Modifier.fillMaxWidth(0.9f),
                singleLine = true,
                isError = isError,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedLabelColor = RedOrange,
                    unfocusedIndicatorColor = Color.White,
                    focusedLabelColor = RedOrange
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() })
            )

            Spacer(modifier = Modifier.padding(0.dp, 20.dp))

            Button(
                onClick = { openDialogLang.value = true },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(6.dp, 8.dp, 0.dp)
            ) {
                if (openDialogLang.value) {
                    DialogLanguage(viewModel = viewModel, openDialogLang = openDialogLang)
                }
                Text(
                    text = stringResource(id = R.string.language) + " " + fromLangToAbbreviation(
                        selectedLang
                    ),
                    color = DarkGray,
                    style = Typography.button,
                    textAlign = TextAlign.Start
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_language_24),
                    contentDescription = "Open Lang",
                    tint = DarkGray,
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 50.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusRequester.requestFocus()
                    }
                    .focusRequester(focusRequester)
                    .focusable()
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun DialogAvatar(
    openDialogAvatar: MutableState<Boolean>,
    viewModel: ProfileViewModel
) {
    Dialog(
        onDismissRequest = {
            openDialogAvatar.value = false
        }
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            itemsIndexed(avatar) { index, item ->
                Card(
                    modifier = Modifier
                        .clickable {
                            viewModel.setAvatar(index)
                            openDialogAvatar.value = false
                        }
                        .padding(5.dp)
                        .size(90.dp),

                    backgroundColor = Color.White,
                    shape = CircleShape
                ) {
                    Image(painter = painterResource(item), contentDescription = "avatar")
                }
            }
        }
    }
}

            