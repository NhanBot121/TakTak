package com.mck.myprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mck.core.DestinationRoute.CREATOR_PROFILE_ROUTE
import com.mck.core.DestinationRoute.FORMATTED_COMPLETE_CREATOR_VIDEO_ROUTE

import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE
import com.mck.core.DestinationRoute.PassedKey.USER_ID
import com.mck.core.DestinationRoute.PassedKey.VIDEO_INDEX

fun NavGraphBuilder.myProfileNavGraph(navController: NavHostController) {


    composable(route = MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }


}