package com.mck.discovery

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.mck.data.model.VideoDetails


@OptIn(UnstableApi::class)
@Composable
fun TikTokVideoPlayer(
    video: VideoDetails,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    // Initialize the ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            if (video.videoLink.isNotBlank()) {
                val mediaItem = MediaItem.fromUri(video.videoLink)
                this.setMediaItem(mediaItem)
                this.prepare()
                this.playWhenReady = isPlaying
            } else {
                Log.e("TikTokVideoPlayer", "Video link is blank!")
            }
        }
    }

    // Handle play/pause state
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    // Clean up the ExoPlayer when the composable leaves the composition
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    // Video Player View
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onPlayPauseClick() }
    ) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Back Button
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Play Icon
        if (!isPlaying) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}