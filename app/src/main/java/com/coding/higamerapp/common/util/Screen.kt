package com.coding.higamerapp.common.util

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen("home_screen")
    object ProfileScreen : Screen("profile_screen")
    object ChatScreen : Screen("chat_screen")
    object SettingScreen : Screen("setting_screen")
    object ChatListScreen : Screen("chat_list_screen")
}