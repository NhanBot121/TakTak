package com.mck.taktak.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mck.discovery.discoveryNavGraph
import com.mck.home.homeNavGraph
import com.mck.inbox.inboxNavGraph
import com.mck.myprofile.myProfileNavGraph
import com.mck.cameramedia.cameraMediaNavGraph

import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeNavGraph(navController)
//        commentListingNavGraph(navController)
//        creatorProfileNavGraph(navController)
        discoveryNavGraph(navController)
        inboxNavGraph(navController)
//        authenticationNavGraph(navController)
//        loginEmailPhoneNavGraph(navController)
        myProfileNavGraph(navController)
//        settingNavGraph(navController)
        cameraMediaNavGraph(navController)
    }
}