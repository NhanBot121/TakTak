package com.mck.discovery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.mck.core.DestinationRoute


fun NavGraphBuilder.friendsNavGraph(navController: NavController) {
    composable(route = DestinationRoute.DISCOVERY_ROUTE) {
        DiscoveryScreen(navController)
    }
}