package com.coding.higamerapp.feature_chat.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.higamerapp.feature_chat.model.ChatMessage
import com.coding.higamerapp.ui.theme.RedOrange

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MessageCard(message: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment =
        if (message.isMine) Alignment.End
        else Alignment.Start,
    ) {
        Card(
            modifier = Modifier.widthIn(min = 80.dp, max = 220.dp),
            shape = cardShape(message),
            backgroundColor =
            if (message.isMine)
                RedOrange
            else Color.LightGray,
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = message.text,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = if (message.isMine)
                    TextAlign.End
                else TextAlign.Start
            )
        }
        Text(
            text = message.timestamp.toDate().toLocaleString(),
            fontSize = 12.sp,
        )
    }
}


@Composable
fun cardShape(message: ChatMessage): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return if (message.isMine)
        roundedCorners.copy(topEnd = CornerSize(0))
    else roundedCorners.copy(topStart = CornerSize(0))
}
