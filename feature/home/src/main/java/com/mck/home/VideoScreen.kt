package com.mck.home

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.mck.data.model.VideoRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mck.data.model.VideoDetails

@ExperimentalMaterial3Api
@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    navController: NavHostController,
    userId: String = "video",
    index: Int = 0
) {
    val viewModelFactory = HomeViewModel.provideFactory( VideoRepository())
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val videos by viewModel.videoLiveData.observeAsState(emptyList<VideoDetails>())

    var isPlayingIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        viewModel.getVideos(userId)
    }

    // Use VerticalPager instead of LazyColumn
    if (videos.isNotEmpty()) {
        val pagerState = rememberPagerState(
            initialPage = index,
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
                        onAvatarClicked = {
                            navController.navigate("profile/${video.userId}")
                        },
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




