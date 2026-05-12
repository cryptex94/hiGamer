package com.coding.higamerapp.feature_gamers.presentation.gamers_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.avatar
import com.coding.higamerapp.common.util.Constants.champions
import com.coding.higamerapp.common.util.Constants.langList
import com.coding.higamerapp.common.util.Constants.serverList
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.ui.theme.Panna
import com.coding.higamerapp.ui.theme.RedOrange

@Composable
fun GamerEntrySpecs(
    entry: GamerDto,
    gamerSpecs: MutableState<Boolean>,
) {
        Dialog(onDismissRequest = {
            gamerSpecs.value = false
        }) {
                    Card(
                        backgroundColor = Panna,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.5f)
                            .pointerInput(Unit){
                                               detectTapGestures(
                                                   onTap = {gamerSpecs.value = false}

                                               )
                            },
                        border = BorderStroke(2.dp, RedOrange)
                    ) {
                        Image(
                            painter = painterResource(id = avatar[entry.avatar]),
                            contentDescription = null,
                            alpha = 0.2f
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = entry.username,
                                    fontSize = 18.sp,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                                if (entry.language != null)
                                    Text(
                                        text = langList[entry.language],
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily.Serif,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                            }
                            Row(
                                modifier = Modifier.weight(1f, true),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    Modifier.weight(1f, true),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = when (entry.tier) {
                                            0 -> stringResource(R.string.iron)
                                            1 -> stringResource(R.string.bronze)
                                            2 -> stringResource(R.string.silver)
                                            3 -> stringResource(R.string.gold)
                                            4 -> stringResource(R.string.platinum)
                                            5 -> stringResource(R.string.diamond)
                                            else -> stringResource(R.string.any_tier)
                                        },
                                        fontSize = 16.sp,
                                        color = Color.DarkGray,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = when (entry.role) {
                                            0 -> stringResource(R.string.top)
                                            1 -> stringResource(R.string.jungle)
                                            2 -> stringResource(R.string.mid)
                                            3 -> stringResource(R.string.adc)
                                            4 -> stringResource(R.string.support)
                                            else -> stringResource(R.string.any_tier)
                                        },
                                        fontSize = 16.sp,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        fontFamily = FontFamily.Serif,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = stringResource(serverList[entry.server]),
                                        fontSize = 16.sp,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        fontFamily = FontFamily.Serif,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Column(
                                    Modifier.weight(1f, true),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (entry.champions?.isNotEmpty() == true && entry.champions[0] != null) {
                                        Text(
                                            text = champions[entry.champions[0]!!],
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            color = Color.DarkGray,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (entry.champions?.isNotEmpty() == true &&
                                        entry.champions.size > 1
                                        && entry.champions[1] != null
                                    ) {
                                        Text(
                                            text = champions[entry.champions[1]!!],
                                            fontSize = 16.sp,
                                            maxLines = 1,
                                            fontFamily = FontFamily.Serif,
                                            color = Color.DarkGray,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (entry.team)
                                        stringResource(id = R.string.teammates)
                                    else stringResource(id = R.string.no_teammates),
                                    fontSize = 16.sp,
                                    color = Color.DarkGray,
                                    maxLines = 1,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
    }
}