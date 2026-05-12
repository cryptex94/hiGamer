package com.coding.higamerapp.feature_profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coding.higamerapp.R
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography

@Composable
fun DialogLanguage(
    viewModel: ProfileViewModel,
    openDialogLang: MutableState<Boolean>
) {
    Dialog(
        onDismissRequest = {
            openDialogLang.value = false
        }
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = Color.DarkGray
        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.select_language) + ":",
                        modifier = Modifier
                            .padding(7.dp),
                        fontSize = 16.sp,
                        color = Color.White,
                        style = Typography.button
                    )
                    TextButton(
                        onClick = { openDialogLang.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
                TextButton(
                    onClick = {
                        viewModel.setLanguage(0)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.english),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(1)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.french),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(2)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.spanish),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(3)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.german),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(4)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.italian),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(5)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.portuguese),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(6)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.polish),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(7)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.russian),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(8)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.korean),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(9)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.chinese),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(10)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.arabic),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setLanguage(null)
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.others),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
            }
        }
    }
}