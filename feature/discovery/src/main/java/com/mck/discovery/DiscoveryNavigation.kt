package com.mck.discovery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.mck.core.DestinationRoute


fun NavGraphBuilder.discoveryNavGraph(navController: NavController) {
    composable(route = DestinationRoute.DISCOVERY_ROUTE) {
        DiscoveryScreen(navController)
    }
    composable("videoPlayer/{videoUrl}") { backStackEntry ->
        val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
        VideoPlayerScreen(videoUrl = videoUrl, onBack = { navController.popBackStack() })
    }
}