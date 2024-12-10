package com.mck.discovery
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun DiscoveryScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var videoList by remember { mutableStateOf<List<Video>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val videoRepository = VideoRepository()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Search bar
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Search by tag") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Search button
        Button(
            onClick = {
                coroutineScope.launch {
                    videoList = videoRepository.fetchVideosByTag(searchText)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of videos
        LazyColumn {
            items(videoList) { video ->
                VideoItem(video = video, onVideoClick = { url ->
                    navController.navigate("videoPlayer/${Uri.encode(url)}")
                })
            }
        }
    }
}
