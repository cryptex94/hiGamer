package com.coding.higamerapp.feature_chat.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Constants.avatar
import com.coding.higamerapp.feature_chat.data.data_source.GamerDetail
import com.coding.higamerapp.feature_chat.presentation.ChatScreenViewModel
import com.coding.higamerapp.feature_gamers.data.dto.GamerDto
import com.coding.higamerapp.feature_gamers.presentation.gamers_list.components.GamerEntrySpecs
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalComposeUiApi
@Composable
fun ChatScreen(
    viewModel: ChatScreenViewModel = hiltViewModel()
) {

    var text by remember { mutableStateOf("") }

    val messages by remember { mutableStateOf(viewModel.stateMessage) }

    LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val labelMessage = stringResource(id = R.string.message)
    val gamerSpecs = remember { mutableStateOf(false) }

    val entry = GamerDto(
        GamerDetail.username!!,
        GamerDetail.role!!,
        GamerDetail.tier!!,
        GamerDetail.server!!,
        null,
        GamerDetail.team!!,
        GamerDetail.firebaseId!!,
        GamerDetail.language,
        GamerDetail.avatar!!,
        GamerDetail.champions,
    )

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .padding(5.dp)
            .focusRequester(focusRequester)
            .focusable()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() }
    ) {


        TopChatSection(focusRequester, gamerSpecs)


        val courutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        if(gamerSpecs.value)
        GamerEntrySpecs(entry = entry, gamerSpecs = gamerSpecs)


        LazyColumn(modifier = Modifier
            .clickable
                (
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() }
            .focusRequester(focusRequester)
            .weight(1f, true)
            .focusable(),
            state = listState) {

            val sortedMessages = messages.value.messages.sortedBy {
                it.timestamp
            }

            itemsIndexed(sortedMessages) { index, _ ->
                MessageCard(sortedMessages[index])
            }

            if (sortedMessages.isNotEmpty()) {
                courutineScope.launch {
                    listState.scrollToItem(sortedMessages.size - 1)
                }
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f, false)
                    .fillMaxWidth(0.9f),
                singleLine = false,
                label = { Text(labelMessage) },

                shape = RoundedCornerShape(8.dp),
                colors = customOutlinedTextFieldColors()
            )
            FloatingActionButton(
                onClick = {
                    if (text != "") {
                        viewModel.sendChatMessage(text)
                        text = ""
                    }
                },
                backgroundColor = RedOrange,
                contentColor = DarkGray

            )
            {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Icon"
                )
            }
        }
    }
}


@Composable
fun TopChatSection(
    focusRequester: FocusRequester,
    gamerSpecs: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource =
                remember { MutableInteractionSource() },
                indication = null
            ) {
                focusRequester.requestFocus()
            }
            .focusRequester(focusRequester)
            .focusable()
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .clickable {
            gamerSpecs.value = true
        }) {
            Image(
                painter = painterResource(avatar[GamerDetail.avatar!!]),
                contentDescription = "Avatar",
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp)
                    .background(Color.White, CircleShape)
            )
            Text(GamerDetail.username!!)
        }
        Divider(
            Modifier.fillMaxWidth(),
            color = Color.White
        )
    }
}


@Composable
fun customOutlinedTextFieldColors(
    textColor: Color = Color.White,
    disabledTextColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    cursorColor: Color = Color.White,
    focusedBorderColor: Color = RedOrange,
    unfocusedBorderColor: Color = RedOrange,
    unfocusedLabelColor: Color = Color.White,
    errorCursorColor: Color = Color.White,
    disabledPlaceholderColor: Color = Color.White,
) = TextFieldDefaults.outlinedTextFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
    unfocusedBorderColor = unfocusedBorderColor,
    focusedBorderColor = focusedBorderColor,
    unfocusedLabelColor = unfocusedLabelColor
)