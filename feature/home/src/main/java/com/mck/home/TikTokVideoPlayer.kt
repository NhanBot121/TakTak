package com.example.home

import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.mck.data.model.VideoDetails

@UnstableApi
@Composable
fun TikTokVideoPlayer(
    video: VideoDetails,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier
){
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(video.videoLink)
                this.setMediaItem(mediaItem)
                this.prepare()
                this.play()
            }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer.play() // Play the video if isPlaying is true
        } else {
            exoPlayer.pause() // Pause the video if isPlaying is false
        }
    }

    exoPlayer.playWhenReady = isPlaying
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    AndroidView(factory = {
        PlayerView(context).apply {
            hideController()
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player = exoPlayer

        }
    })
    DisposableEffect(Unit){
        onDispose { exoPlayer.release() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onPlayPauseClick()

            }
    ){
        if(isPlaying){

        } else {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Like",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}