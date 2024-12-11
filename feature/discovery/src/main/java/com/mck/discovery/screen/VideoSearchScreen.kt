package com.mck.discovery.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.exoplayer2.util.Log
import com.mck.data.model.VideoDetails
import com.mck.discovery.model.HomeViewModel
import com.mck.discovery.repository.VideoRepository


@Composable
fun VideoSearchScreen(navController: NavHostController) {
    val viewModelFactory = HomeViewModel.provideFactory(VideoRepository())
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val videos by viewModel.videoLiveData.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())


    var query by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getVideos()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    Log.d("VideoSearchScreen", "Query updated: $query")
                },
                placeholder = {
                    Text("Search for videos")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true
            )
            Button(
                onClick = {
                    if (query.isBlank()) {
                        Log.e("VideoSearchScreen", "Query is empty!")
                    } else {
                        isSearching = true
                        viewModel.searchVideos(query)
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Search")
            }
        }

        // List of videos
        val videosToDisplay = if (isSearching) searchResults else videos

        if (videosToDisplay.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(videosToDisplay) { video ->
                    VideoItem(video = video, navController = navController)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text("No videos found", color = Color.Black)
            }
        }
    }
}
@Composable
fun VideoItem(video: VideoDetails, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp)
            .clickable {
                try {
                    // Mã hóa URL
                    val encodedVideoLink = java.net.URLEncoder.encode(video.videoLink, "UTF-8")
                    val encodedDescription = java.net.URLEncoder.encode(video.description, "UTF-8")
                    val encodedTags =
                        video.tags.joinToString(",") { java.net.URLEncoder.encode(it, "UTF-8") }

                    // Điều hướng
                    navController.navigate(
                        "video_player_screen/$encodedVideoLink/$encodedDescription/${video.like}/${video.comment}/${video.share}/${video.isLiked}/$encodedTags"
                    )
                } catch (e: Exception) {
                    Log.e("VideoItem", "Error encoding URL: ${e.message}")
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = video.description,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Likes: ${video.like} | Comments: ${video.comment} | Shares: ${video.share}",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
