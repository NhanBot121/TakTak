package com.mck.discovery.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mck.discovery.model.HomeViewModel
import com.mck.data.model.VideoDetails


@Composable
fun Navigation(navController: NavHostController,
               viewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = "video_search_screen") {
        // Screen for video search
        composable("video_search_screen") {
            VideoSearchScreen(navController = navController)
        }

        // Screen for video player
        composable(
            "video_player_screen/{videoLink}/{description}/{like}/{comment}/{share}/{isLiked}/{tags}",
            arguments = listOf()
        ) { backStackEntry ->
            val videoLink = backStackEntry.arguments?.getString("videoLink") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val like = backStackEntry.arguments?.getString("like")?.toIntOrNull() ?: 0
            val comment = backStackEntry.arguments?.getString("comment")?.toIntOrNull() ?: 0
            val share = backStackEntry.arguments?.getString("share")?.toIntOrNull() ?: 0
            val isLiked = backStackEntry.arguments?.getString("isLiked")?.toBoolean() ?: false
            val tags = backStackEntry.arguments?.getString("tags")?.split(",") ?: emptyList()

            val videoDetails = VideoDetails(
                videoLink = videoLink,
                description = description,
                like = like,
                comment = comment,
                share = share,
                isLiked = isLiked,
                tags = tags
            )
            VideoPlayerScreen(
                videoDetails = videoDetails,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }, // Quay lại màn hình tìm kiếm
                onAvatarClicked = { /* Xử lý khi avatar được nhấn */ },
                onCommentClicked = { /* Xử lý khi comment được nhấn */ },
                onShareClicked = { /* Xử lý khi share được nhấn */ }
            )
        }
    }
}