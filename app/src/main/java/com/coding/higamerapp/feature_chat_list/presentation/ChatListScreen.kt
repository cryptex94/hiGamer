package com.coding.higamerapp.feature_chat_list.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.avatar
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.feature_chat.data.data_source.GamerDetail
import com.coding.higamerapp.feature_chat_list.domain.model.ChatRoom
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.components.GamerEntrySpecs
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ChatListScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: ChatListViewModel = hiltViewModel()
) {

    val state by remember { mutableStateOf(viewModel.state)}
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val scope = rememberCoroutineScope()
    val chatDeleted = stringResource(id = R.string.chat_deleted)
    val undo = stringResource(id = R.string.undo)
    val gamerSpecs = remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch {
                viewModel.updateGamersInfo()
                viewModel.getChatList()
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.rooms.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_list),
                        fontSize = 24.sp,
                        style = Typography.h1,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(R.mipmap.empty_list_foreground),
                        contentDescription = "Empty List",
                        modifier = Modifier
                            .height(160.dp)
                            .width(160.dp)
                    )
                }
            }
        }

        if(gamerSpecs.value)
        GamerEntrySpecs(entry = GamerDetail.gamerDetailToGamerDto(), gamerSpecs = gamerSpecs)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val sortedRooms = state.value.rooms.sortedByDescending {
                it.timestamp
            }
            items(sortedRooms) { item ->
                RowChatList(
                    entry = item,
                    navController = navController,
                    viewModel,
                    gamerSpecs,
                    onDeleteClick = {
                        viewModel.deleteChatRoom(item)
                        viewModel.deleteNotifyFromFirestore(item.firebaseId)
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = chatDeleted,
                                actionLabel = undo,
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.restoreChatRoom()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun RowChatList(
    entry: ChatRoom,
    navController: NavController,
    viewModel: ChatListViewModel,
    gamerSpecs : MutableState<Boolean>,
    onDeleteClick: () -> Unit
) {

    val courutineScope = rememberCoroutineScope()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(DarkGray)
                .clickable {
                    if (!gamerSpecs.value) {
                        navController.navigate(Screen.ChatScreen.route)
                        GamerDetail.setGamerDetail(entry)
                        courutineScope.launch {
                            viewModel.updateUnreadMessage(false, entry.firebaseId)
                        }
                    }

                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Image(
                        modifier = Modifier
                            .background(Color.White, CircleShape)
                            .pointerInput(Unit) {
                                detectTapGestures (
                                    onTap = {
                                        GamerDetail.setGamerDetail(entry)
                                        gamerSpecs.value = true
                                    }
                                    )
                            },
                        painter = painterResource(avatar[entry.avatar]),
                        contentDescription = "avatar",
                    )
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Column(modifier = Modifier.fillMaxWidth(0.6f)) {


                    Text(
                        text = entry.username,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.LightGray,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )


                    Text(
                        text = entry.lastMessage,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.LightGray,
                        maxLines = 1,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            Row {
                if (entry.unreadMessage) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mark_chat_unread),
                        contentDescription = "New Message",
                        tint = RedOrange
                    )
                }
            }
            Row {
                IconButton(
                    onClick = onDeleteClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Chat",
                        tint = RedOrange
                    )
                }
            }
        }
    }

}


