package com.coding.higamerapp.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.champions
import com.coding.higamerapp.feature_profile.presentation.ProfileViewModel
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography

@Composable
fun DialogChampionList(openChampionsList: MutableState<Boolean>, viewModel: ProfileViewModel) {

    Dialog(onDismissRequest = { openChampionsList.value = false }) {

        var bestChampions by remember {
            mutableStateOf(
                viewModel.bestChamp.value
            )
        }

        var listChampions by remember {
            mutableStateOf(
                (champions.indices).mapIndexed { index, item ->
                    if (bestChampions?.size == 1) {
                        if (bestChampions?.get(0) == index)
                            ChampionList(
                                name = champions[index],
                                isSelected = true
                            )
                        else ChampionList(
                            name = champions[index],
                            isSelected = false
                        )
                    } else if (bestChampions?.size == 2) {
                        if (bestChampions?.get(0) == index || bestChampions?.get(1) == index)
                            ChampionList(
                                name = champions[index],
                                isSelected = true
                            )
                        else
                            ChampionList(
                                name = champions[index],
                                isSelected = false
                            )
                    } else
                        ChampionList(
                            name = champions[index],
                            isSelected = false
                        )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.8f).fillMaxWidth(0.9f)
                .background(DarkGray, RoundedCornerShape(10.dp))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.select_champ))
            }


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listChampions.forEachIndexed { index, champion ->
                    if (champion.isSelected)
                        Card(
                            modifier = Modifier
                                .weight(0.7f, false)
                                .height(30.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = RedOrange,
                            contentColor = DarkGray
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = listChampions[index].name,
                                    color = DarkGray,
                                    style = Typography.button
                                )
                                IconButton(onClick = {
                                   listChampions = listChampions.mapIndexed { i, championList ->
                                        if(index == i)
                                            championList.copy(isSelected = !championList.isSelected)
                                        else championList
                                    }

                                    val selectedChampions: MutableList<Int?>? = mutableListOf()
                                    listChampions.forEachIndexed { index, championList ->
                                        if (championList.isSelected) {
                                            selectedChampions?.add(0, index)
                                        }
                                    }
                                    viewModel.setBestChamp(selectedChampions)

                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        tint = DarkGray,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }
                }
            }


            LazyColumn(modifier = Modifier.weight(1f, false)) {
                items(listChampions.size) { i ->

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            listChampions = listChampions.mapIndexed { j, item ->
                                var counter = 0
                                listChampions.forEach {
                                    if (it.isSelected)
                                        counter += 1
                                }
                                if (i == j && counter < 2) {
                                    item.copy(isSelected = !item.isSelected)
                                } else if (i == j && counter > 1 && item.isSelected)
                                    item.copy(isSelected = !item.isSelected)
                                else item
                            }
                            val selectedChampions: MutableList<Int?>? = mutableListOf()
                            var counter = 0
                            listChampions.forEachIndexed { index, championList ->
                                if (championList.isSelected) {
                                    selectedChampions?.add(counter, index)
                                    counter++
                                }
                            }
                            viewModel.setBestChamp(selectedChampions)
                        }
                        .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Text(text = listChampions[i].name)
                        if (listChampions[i].isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Selected",
                                tint = RedOrange,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
