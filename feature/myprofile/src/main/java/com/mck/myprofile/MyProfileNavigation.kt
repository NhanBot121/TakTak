package com.mck.myprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE

fun NavGraphBuilder.myProfileNavGraph(navController: NavHostController) {
    composable(route = MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }
}