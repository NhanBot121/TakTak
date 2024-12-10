package com.mck.myprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mck.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.mck.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE

import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE
import com.mck.core.DestinationRoute.PassedKey.USER_ID
import com.mck.core.DestinationRoute.PassedKey.VIDEO_INDEX

fun NavGraphBuilder.myProfileNavGraph(navController: NavController) {


    composable(route = MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }

    composable(route = "$CREATOR_PROFILE_ROUTE/{$USER_ID}",
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.LongType }
        )
    ) {
        MyProfileScreen(
            //onClickNavIcon = { navController.navigateUp() },
            navController = navController
        )
    }

    composable(route = FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE,
        arguments = listOf(
            navArgument(USER_ID) { type = NavType.LongType },
            navArgument(VIDEO_INDEX) { type = NavType.IntType }
        )
    ) {

    }

}