package com.mck.discovery

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.mck.core.DestinationRoute.DISCOVERY_ROUTE
import com.mck.discovery.model.HomeViewModel
import com.mck.discovery.repository.VideoRepository
import com.mck.discovery.screen.Navigation

fun NavGraphBuilder.discoveryNavGraph(navController: NavController) {
    composable(route = DISCOVERY_ROUTE) {
        //HomeScreen(navController)
        val navController = rememberNavController()
        val viewModelFactory = HomeViewModel.provideFactory(VideoRepository())
        val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
        Navigation(navController = navController,viewModel = viewModel)

    }

}