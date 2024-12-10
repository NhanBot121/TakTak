package com.mck.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mck.home.VideoScreen
import com.mck.core.DestinationRoute.FORMATTED_COMPLETE_VIDEO_ROUTE

import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.mck.core.DestinationRoute.PassedKey.USER_ID
import com.mck.core.DestinationRoute.PassedKey.VIDEO_INDEX

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(route = HOME_SCREEN_ROUTE) {
        //HomeScreen(navController)
        VideoScreen(navController)
    }

    composable(route = "home_screen_route/{userId}/{index}",
        arguments = listOf(
            navArgument("userId") { type = NavType.StringType },
            navArgument("index") { type = NavType.IntType }
        )
        ) {
        backstackEntry -> val userId = backstackEntry.arguments?.getString(USER_ID)
        val videoIndex = backstackEntry.arguments?.getInt(VIDEO_INDEX)
        if (userId != null && videoIndex != null) {
                VideoScreen(navController)
        }

        //VideoScreen(navController)

    }
}