package com.mck.taktak

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetDefaults

import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

import com.google.accompanist.navigation.material.ModalBottomSheetLayout

import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mck.core.DestinationRoute
import com.mck.theme.TikTokTheme
import com.mck.taktak.component.BottomBar
import com.mck.taktak.navigation.AppNavHost


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun RootScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination
    val context = LocalContext.current

    // Determine if the bottom bar should be shown
    val isShowBottomBar = when (currentDestination?.route) {
        DestinationRoute.HOME_SCREEN_ROUTE,
        DestinationRoute.INBOX_ROUTE,
        DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE,
        DestinationRoute.DISCOVERY_ROUTE,
        DestinationRoute.AUTHENTICATION_ROUTE,
        DestinationRoute.MY_PROFILE_ROUTE, null -> true
        else -> false
    }

    // Dark mode based on route
    val darkMode = when (currentDestination?.route) {
        DestinationRoute.HOME_SCREEN_ROUTE,
        DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE,
        DestinationRoute.CAMERA_ROUTE, null -> true
        else -> false
    }

    // BackHandler logic for the home screen
    if (currentDestination?.route == DestinationRoute.HOME_SCREEN_ROUTE) {
        BackHandler {
            (context as? Activity)?.finish()
        }
    }

    // Applying theme and system UI changes
    TikTokTheme(darkTheme = true) {
        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colorScheme.background)

        // Using ModalBottomSheetLayout for bottom sheet navigation
//        ModalBottomSheetLayout(
//            sheetShape = MaterialTheme.shapes.large,
//            sheetElevation = ModalBottomSheetDefaults.Elevation,
//            sheetBackgroundColor = MaterialTheme.colorScheme.surface,
//            sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
//            scrimColor = ModalBottomSheetDefaults.scrimColor,
//            bottomSheetNavigator = bottomSheetNavigator,
//        ) {
            Scaffold(
                bottomBar = {
                    if (isShowBottomBar) {
                        BottomBar(navController, currentDestination, isDarkTheme = darkMode)
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    AppNavHost(navController = navController)
                }
            }
//        }
    }
}


@Composable
fun SetupSystemUi(systemUiController: SystemUiController, systemBarColor: Color) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberBottomSheetNavigator(): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}
