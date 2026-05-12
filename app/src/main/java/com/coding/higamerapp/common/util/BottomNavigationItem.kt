package com.coding.higamerapp.common.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.coding.higamerapp.R

sealed class BottomNavigationItem(
    @StringRes val name: Int,
    val route: String,
    @DrawableRes val icon: Int,
    var badge: MutableState<Boolean>
) {

    object Home : BottomNavigationItem(
        R.string.gamers,
        Screen.HomeScreen.route,
        R.drawable.ic_bottombar_sports_esports_24,
        mutableStateOf(false)
    )

    object Chat :
        BottomNavigationItem(
            R.string.chat,
            Screen.ChatListScreen.route,
            R.drawable.ic_bottombar_chat_24,
            mutableStateOf(false)
        )

    object Profile :
        BottomNavigationItem(
            R.string.profile,
            Screen.ProfileScreen.route,
            R.drawable.ic_bottombar_account_circle_24,
            mutableStateOf(false)
        )
}


