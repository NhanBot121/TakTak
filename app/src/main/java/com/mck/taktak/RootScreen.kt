package com.mck.taktak

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mck.core.DestinationRoute
import com.mck.theme.TikTokTheme
import com.mck.taktak.component.BottomBar
import com.mck.taktak.navigation.AppNavHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
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

    // Modal Bottom Sheet state
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    // Applying theme and system UI changes
    TikTokTheme(darkTheme = false) {
        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colors.background)
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                // Define the content of your bottom sheet here
//                BottomSheetContent()
            },
            sheetShape = MaterialTheme.shapes.large,
            scrimColor = ModalBottomSheetDefaults.scrimColor
        ) {
            Scaffold(
                bottomBar = {
                    if (isShowBottomBar) {
                        BottomBar(navController, currentDestination, isDarkTheme = false)
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AppNavHost(
                        navController = navController,
//                        onShowBottomSheet = {
//                            coroutineScope.launch { sheetState.show() }
//                        },
//                        onHideBottomSheet = {
//                            coroutineScope.launch { sheetState.hide() }
//                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SetupSystemUi(systemUiController: SystemUiController, systemBarColor: Color) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}

//@Composable
//fun BottomSheetContent() {
//    // Example content for the bottom sheet
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "This is the bottom sheet content", style = MaterialTheme.typography.body1)
//    }
//}
