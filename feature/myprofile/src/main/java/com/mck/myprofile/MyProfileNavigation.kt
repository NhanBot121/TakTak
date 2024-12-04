package com.mck.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE

fun NavGraphBuilder.myProfileNavGraph(navController: NavController) {
    composable(route = MY_PROFILE_ROUTE) {
        MyProfileScreen(navController)
    }

}