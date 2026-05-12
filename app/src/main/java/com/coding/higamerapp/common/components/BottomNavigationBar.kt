package com.coding.higamerapp.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.BottomNavigationItem
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange

@ExperimentalMaterialApi
@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Chat,
        BottomNavigationItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = DarkGray,
            elevation = 5.dp
        ) {
            items.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(
                    selected = selected,
                    enabled = currentDestination?.hierarchy?.any { it.route == screen.route } == false,
                    onClick = {
                        navController.navigate(screen.route)
                        {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        screen.badge.value = false
                    },
                    label = {
                        Text(
                            when (screen.name) {
                                R.string.gamers -> stringResource(id = R.string.gamers)
                                R.string.chat -> stringResource(id = R.string.chat)
                                R.string.profile -> stringResource(id = R.string.profile)
                                else -> {
                                    stringResource(id = R.string.gamers)
                                }
                            }
                        )
                    },
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            if (screen.badge.value && currentDestination?.route != Screen.ChatListScreen.route) {
                                BadgeBox(
                                    /*      badgeContent = {
                                              Text(text = screen.badgeCount.value.toString())
                                          } */
                                ) {
                                    Icon(
                                        painter = painterResource(id = screen.icon),
                                        contentDescription = screen.route
                                    )
                                }
                            } else {
                                screen.badge.value = false
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = screen.route
                                )
                            }
                        }
                    },
                    selectedContentColor = RedOrange,
                    unselectedContentColor = Color.White
                )
            }
        }
    }
}

