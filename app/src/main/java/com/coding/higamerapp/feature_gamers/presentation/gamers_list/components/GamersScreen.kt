package com.coding.higamerapp.feature_gamers.presentation.gamers_list.components

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.avatar
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.common.util.showInterstitial
import com.coding.higamerapp.feature_chat.data.data_source.GamerDetail
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.GamerListViewModel
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.InterstitialAdViewModel
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GamersScreen(
    viewModel: GamerListViewModel = hiltViewModel(),
    navController: NavController,
    adViewModel: InterstitialAdViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val gamersListItems: LazyPagingItems<GamerDto> = viewModel.gamers.collectAsLazyPagingItems()


    LaunchedEffect(true) {
        if (showInterstitial) {
            delay(2500)
            adViewModel.launchAd(context)
            showInterstitial = false
        }
    }

    Column(modifier = Modifier.padding(15.dp, bottom = 0.dp)) {
        FilterBySection(gamersListItems, viewModel)
        GamerList(gamersListItems, navController = navController, viewModel)
    }
}

@ExperimentalFoundationApi
@Composable
fun GamerList(
    gamersListItems: LazyPagingItems<GamerDto>,
    navController: NavController,
    viewModel: GamerListViewModel
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            gamersListItems.refresh()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (gamersListItems.itemSnapshotList.isEmpty() && !isRefreshing) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.empty_list),
                    fontSize = 24.sp,
                    style = Typography.h1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.swipe_refresh),
                    modifier = Modifier.padding(5.dp),
                    fontSize = 16.sp,
                    style = Typography.body1,
                    fontWeight = FontWeight.Normal
                )
                Image(
                    painter = painterResource(R.mipmap.empty_list_foreground),
                    contentDescription = "Empty List",
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                )
            }
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(gamersListItems.itemCount) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    gamersListItems[index]?.let {
                        GamerEntry(
                            entry = it,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GamerEntry(
    entry: GamerDto,
    navController: NavController,
) {

    var toState by remember { mutableStateOf(ComponentState.Released) }
    val transition: Transition<ComponentState> = updateTransition(targetState = toState, label = "")
    val gamerSpecs = remember { mutableStateOf(false) }

// Defines a float animation to scale x,y
    val scalex: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = 900f) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.90f else 1f
    }
    val scaley: Float by transition.animateFloat(
        transitionSpec = { spring(stiffness = 900f) }, label = ""
    ) { state ->
        if (state == ComponentState.Pressed) 0.90f else 1f
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        border = BorderStroke(1.dp, RedOrange),
        modifier = Modifier
            .graphicsLayer {
                scaleX = scalex;
                scaleY = scaley
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        toState = ComponentState.Pressed
                        if (!gamerSpecs.value) {
                            navController.navigate(Screen.ChatScreen.route)
                            GamerDetail.setGamerDetail(entry)
                        }
                    },
                    onLongPress = {
                            gamerSpecs.value = true
                            toState = ComponentState.Pressed
                    }
                )
            }
    )
    {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Image(
                    painter = painterResource(avatar[entry.avatar]),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, CircleShape)
                )
            }

            Text(
                text = entry.username,
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = when (entry.tier) {
                    0 -> stringResource(R.string.tier) + ": " + stringResource(R.string.iron)
                    1 -> stringResource(R.string.tier) + ": " + stringResource(R.string.bronze)
                    2 -> stringResource(R.string.tier) + ": " + stringResource(R.string.silver)
                    3 -> stringResource(R.string.tier) + ": " + stringResource(R.string.gold)
                    4 -> stringResource(R.string.tier) + ": " + stringResource(R.string.platinum)
                    5 -> stringResource(R.string.tier) + ": " + stringResource(R.string.diamond)
                    else -> stringResource(R.string.tier) + ": " + stringResource(R.string.any_tier)
                },
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = when (entry.role) {
                    0 -> stringResource(R.string.role) + ": " + stringResource(R.string.top)
                    1 -> stringResource(R.string.role) + ": " + stringResource(R.string.jungle)
                    2 -> stringResource(R.string.role) + ": " + stringResource(R.string.mid)
                    3 -> stringResource(R.string.role) + ": " + stringResource(R.string.adc)
                    4 -> stringResource(R.string.role) + ": " + stringResource(R.string.support)
                    else -> stringResource(R.string.role) + ": " + stringResource(R.string.any_tier)
                },
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    if (gamerSpecs.value)
        GamerEntrySpecs(
            entry,
            gamerSpecs
        )
    else toState = ComponentState.Released
}

@Composable
fun FilterBySection(
    gamersListItems: LazyPagingItems<GamerDto>,
    viewModel: GamerListViewModel
) {

    val textFilterTier by remember { viewModel.textFilterTier }
    val textFilterRole by remember { viewModel.textFilterRole }
    val textFilterLang by remember { viewModel.textFilterLang }


    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val openDialogTier = remember { mutableStateOf(false) }
        val openDialogRole = remember { mutableStateOf(false) }
        val openDialogLang = remember { mutableStateOf(false) }
        val tier = stringResource(R.string.tier)
        val role = stringResource(R.string.role)
        val language = stringResource(R.string.language)

        val list = listOf(tier, role, language)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.look_for_gamer),
                color = Color.White,
                textAlign = TextAlign.Start,
                letterSpacing = 0.sp,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
            )
            LazyRow(
                Modifier.padding(0.dp, 0.dp, 20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(items = list, itemContent = { item ->
                    when (item) {
                        tier -> {
                            OutlinedButton(
                                onClick = { openDialogTier.value = true },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .height(35.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = RedOrange),
                                elevation = ButtonDefaults.elevation(6.dp, 8.dp, 0.dp)
                            ) {
                                if (openDialogTier.value)
                                    DialogFilterByTier(
                                        gamersListItems,
                                        openDialogTier = openDialogTier,
                                        viewModel = viewModel
                                    )

                                Text(
                                    text = stringResource(id = textFilterTier),
                                    color = DarkGray,
                                    style = Typography.button,
                                    textAlign = TextAlign.Start
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Open Tier",
                                    tint = DarkGray,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                        role -> {
                            OutlinedButton(
                                onClick = { openDialogRole.value = true },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .height(35.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = RedOrange),
                                elevation = ButtonDefaults.elevation(6.dp, 8.dp, 0.dp)

                            ) {
                                if (openDialogRole.value)
                                    DialogFilterByRole(
                                        gamersListItems,
                                        hiltViewModel(),
                                        openDialogRole = openDialogRole
                                    )
                                Text(
                                    text = stringResource(id = textFilterRole),
                                    color = DarkGray,
                                    textAlign = TextAlign.Start,
                                    style = Typography.button
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Open Tier",
                                    tint = DarkGray,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                        language -> {
                            OutlinedButton(
                                onClick = { openDialogLang.value = true },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .height(35.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = RedOrange),
                                elevation = ButtonDefaults.elevation(6.dp, 8.dp, 0.dp)
                            ) {
                                if (openDialogLang.value)
                                    DialogFilterByLang(
                                        gamersListItems,
                                        viewModel = viewModel,
                                        openDialogLang
                                    )

                                Text(
                                    text = stringResource(id = textFilterLang),
                                    color = DarkGray,
                                    style = Typography.button,
                                    textAlign = TextAlign.Start
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Open Lang",
                                    tint = DarkGray,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                    }
                })
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = 5.dp),
                color = RedOrange
            )
        }
    }
}

enum class ComponentState { Pressed, Released }




