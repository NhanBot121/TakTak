package com.mck.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.VideoScreen

import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(route = HOME_SCREEN_ROUTE) {
        //HomeScreen(navController)
        VideoScreen()

    }

}