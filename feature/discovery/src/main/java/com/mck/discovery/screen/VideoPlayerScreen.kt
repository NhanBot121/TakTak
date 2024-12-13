package com.mck.discovery.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.google.android.exoplayer2.util.Log
import com.mck.data.model.VideoDetails
import com.mck.discovery.InteractButtons
import com.mck.discovery.TikTokVideoPlayer
import com.mck.discovery.model.HomeViewModel

@Composable
fun VideoPlayerScreen(
    videoDetails: VideoDetails,
    onBack: () -> Unit,
    onAvatarClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    onShareClicked: () -> Unit,
    viewModel: HomeViewModel
) {
    if (videoDetails.videoLink.isBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Video link is invalid", color = Color.Red)
            Log.e("VideoPlayerScreen", "Invalid video link: ${videoDetails.videoLink}")
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Video Player
        TikTokVideoPlayer(
            video = videoDetails,
            isPlaying = true,
            onPlayPauseClick = { /* Handle play/pause */ },
            onBack = onBack,
            modifier = Modifier.fillMaxSize()
        )

        // Interact Buttons (Like, Comment, Share)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            InteractButtons(
                video = videoDetails,
                onAvatarClicked = onAvatarClicked,
                onCommentClicked = onCommentClicked,
                viewModel = viewModel,
                pageIndex = 0 // Sửa lại nếu cần quản lý theo index thực tế
            )
        }

        // Video Description and Tags
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = videoDetails.description,
                    color = Color.White
                )
                Text(
                    text = "Tags: ${videoDetails.tags.joinToString(", ")}",
                    color = Color.Gray
                )
            }
        }
    }
}