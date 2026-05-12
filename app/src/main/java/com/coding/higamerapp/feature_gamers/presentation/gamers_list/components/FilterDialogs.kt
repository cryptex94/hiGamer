package com.coding.higamerapp.feature_gamers.presentation.gamers_list.components

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
import androidx.paging.compose.LazyPagingItems
import com.coding.higamerapp.R
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.GamerListViewModel
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography

@Composable
fun DialogFilterByTier(
    gamersListItems: LazyPagingItems<GamerDto>,
    openDialogTier: MutableState<Boolean>,
    viewModel: GamerListViewModel
) {
    Dialog(
        onDismissRequest = {
            openDialogTier.value = false
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
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.filter_tier) + ":",
                        modifier = Modifier
                            .padding(7.dp),
                        fontSize = 16.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                    TextButton(
                        onClick = { openDialogTier.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(0)
                        viewModel.setTextFilterTier(0)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.iron),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(1)
                        viewModel.setTextFilterTier(1)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.bronze),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(2)
                        viewModel.setTextFilterTier(2)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.silver),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(3)
                        viewModel.setTextFilterTier(3)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.gold),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(4)
                        viewModel.setTextFilterTier(4)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.platinum),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(5)
                        viewModel.setTextFilterTier(5)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.diamond),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingTier(null)
                        viewModel.setTextFilterTier(6)
                        gamersListItems.refresh()
                        openDialogTier.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.any_tier),
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


@Composable
fun DialogFilterByRole(
    gamersListItems: LazyPagingItems<GamerDto>,
    viewModel: GamerListViewModel,
    openDialogRole: MutableState<Boolean>
) {
    Dialog(
        onDismissRequest = {
            openDialogRole.value = false
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
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.filter_role) + ":",
                        modifier = Modifier
                            .padding(7.dp),
                        fontSize = 16.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                    TextButton(
                        onClick = { openDialogRole.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(0)
                        viewModel.setTextFilterRole(0)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.top),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(1)
                        viewModel.setTextFilterRole(1)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.jungle),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(2)
                        viewModel.setTextFilterRole(2)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.mid),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(3)
                        viewModel.setTextFilterRole(3)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.adc),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(4)
                        viewModel.setTextFilterRole(4)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.support),
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        color = RedOrange,
                        style = Typography.button
                    )
                }
                Divider()
                TextButton(
                    onClick = {
                        viewModel.setSearchingRole(null)
                        viewModel.setTextFilterRole(5)
                        gamersListItems.refresh()
                        openDialogRole.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.any_role),
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

@Composable
fun DialogFilterByLang(
    gamersListItems: LazyPagingItems<GamerDto>,
    viewModel: GamerListViewModel,
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
                        text = stringResource(id = R.string.filter_language) + ":",
                        modifier = Modifier
                            .padding(7.dp),
                        fontSize = 16.sp,
                        color = RedOrange,
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
                        viewModel.setSearchingLang(0)
                        viewModel.setTextFilterLang(0)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(1)
                        viewModel.setTextFilterLang(1)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(2)
                        viewModel.setTextFilterLang(2)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(3)
                        viewModel.setTextFilterLang(3)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(4)
                        viewModel.setTextFilterLang(4)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(5)
                        viewModel.setTextFilterLang(5)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(6)
                        viewModel.setTextFilterLang(6)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(7)
                        viewModel.setTextFilterLang(7)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(8)
                        viewModel.setTextFilterLang(8)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(9)
                        viewModel.setTextFilterLang(9)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(10)
                        viewModel.setTextFilterLang(10)
                        gamersListItems.refresh()
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
                        viewModel.setSearchingLang(null)
                        viewModel.setTextFilterLang(11)
                        gamersListItems.refresh()
                        openDialogLang.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 5.dp),
                    contentPadding = PaddingValues(3.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.any_language),
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