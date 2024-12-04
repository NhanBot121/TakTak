package com.mck.taktak.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

import com.mck.taktak.navigation.BottomBarDestination

@Composable
fun BottomBar_v2(
    // To navigate
    navController: NavHostController,
    // To know where is at
    currentDestination: NavDestination?,
    //
    isDarkTheme: Boolean
) {
    NavigationBar(
        modifier = Modifier
            .height(52.dp)
            .shadow(elevation = 16.dp)
            .padding(top = 2.dp)
    ) {
        BottomBarDestination.entries.forEach {
            BottomItem_v2(it, navController, currentDestination, isDarkTheme)
        }
    }
}

@Composable
fun RowScope.BottomItem_v2(
    screen: BottomBarDestination,
    navController: NavHostController,
    currentDestination: NavDestination?,
    isDarkTheme: Boolean,
) {

    // Check if the current item is selected
    val isCurrentBottomItemSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    // Define the icon size and offset
    val (iconSize, offsetY) = if (screen == BottomBarDestination.ADD) Pair(42.dp, (-8).dp)
    else Pair(22.dp, 0.dp)

    // Get the icon for the item
    var icon: Int = screen.unFilledIcon

    // Do something with the screen object
    screen.apply {
        if (this == BottomBarDestination.ADD) {
            if (isDarkTheme) darkModeIcon?.let { icon = it }
        } else {
            if (isCurrentBottomItemSelected) {
                filledIcon?.let { icon = it }
            }
        }
    }

    NavigationBarItem(
        modifier = Modifier.offset(y = -BottomBarItemVerticalOffset),
        label = {
            screen.title?.let {
                Text(
                    modifier = Modifier.offset(y = BottomBarItemVerticalOffset.times(1.85f)),
                    text = stringResource(id = screen.title),
                    style = MaterialTheme.typography.labelSmall,
                    softWrap = false,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isCurrentBottomItemSelected) 1f else 0.7f)
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                Modifier
                    .padding(bottom = 9.dp)
                    .size(iconSize)
                    .offset(y = offsetY),
                tint = Color.Unspecified,
            )
        },

        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.surface,
            selectedIconColor = MaterialTheme.colorScheme.secondary,
            selectedTextColor = MaterialTheme.colorScheme.secondary
        ),
        selected = isCurrentBottomItemSelected,
        onClick = {
            screen.route.let {
                navController.navigate(it) {
                    launchSingleTop = true
                }
            }
        }
    )

}

private val BottomBarItemVerticalOffset = 10.dp