package com.example.home

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.mck.data.model.VideoRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mck.data.model.VideoDetails
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
) {
    val viewModelFactory = HomeViewModel.provideFactory( VideoRepository())
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val videos by viewModel.videoLiveData.observeAsState(emptyList<VideoDetails>())

    var isPlayingIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        viewModel.getVideos()
    }

    // Use VerticalPager instead of LazyColumn
    if (videos.isNotEmpty()) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0F,
            pageCount = { videos.size }
        )

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { pageIndex ->
            val video = videos[pageIndex]
            val isPlaying = pageIndex == isPlayingIndex
            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()
            var showBottomSheet by remember { mutableStateOf(false) }
            // Each page contains a video player
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Video Player
                TikTokVideoPlayer(
                    video, isPlaying = isPlaying,
                    onPlayPauseClick = {
                        isPlayingIndex = if (isPlayingIndex == pageIndex) -1 else pageIndex
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter) // Align to the top
                )

                // Video Info Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = video.description,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Interact Buttons Section
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    InteractButtons(
                        video = video,
                        onAvatarClicked = {},
                        viewModel = viewModel,
                        pageIndex = pageIndex,
                        onCommentClicked = {showBottomSheet = true},
                        modifier = Modifier
                    )
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        CommentScreen(video)

                    }
                }
            }

        }
        } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.Black)
        }
    }
}




