package com.mck.inbox

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.mck.core.DestinationRoute.INBOX_ROUTE

fun NavGraphBuilder.inboxNavGraph(navController: NavController) {
    composable(route = INBOX_ROUTE) {
        InboxScreen(navController)
    }

}