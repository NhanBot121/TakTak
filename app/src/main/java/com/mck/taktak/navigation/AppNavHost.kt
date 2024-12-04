package com.mck.taktak.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import com.mck.core.DestinationRoute.AUTHENTICATION_ROUTE
import com.mck.core.DestinationRoute.HOME_SCREEN_ROUTE
import com.mck.core.DestinationRoute.FRIENDS_ROUTE
import com.mck.core.DestinationRoute.MY_PROFILE_ROUTE
import com.mck.core.DestinationRoute.CAMERA_ROUTE
import com.mck.core.DestinationRoute.COMMENT_BOTTOM_SHEET_ROUTE

import com.mck.friends.friendsNavGraph
import com.mck.home.homeNavGraph

//import com.mck.cameramedia.cameraMediaNavGraph
//import com.mck.commentlisting.commentListingNavGraph
//import com.mck.creatorprofile.creatorProfileNavGraph
//import com.mck.loginwithemailphone.loginEmailPhoneNavGraph
//import com.mck.myprofile.myProfileNavGraph
//import com.mck.setting.settingNavGraph


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
//        inboxNavGraph(navController)
//        authenticationNavGraph(navController)
//        loginEmailPhoneNavGraph(navController)
        friendsNavGraph(navController)
//        myProfileNavGraph(navController)
//        settingNavGraph(navController)
//        cameraMediaNavGraph(navController)
    }
}