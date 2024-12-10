package com.mck.discovery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VideoItem(video: Video, onVideoClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onVideoClick(video.videoLink ?: "")
            }
    ) {
        Column {
            Text(text = video.title, style = MaterialTheme.typography.bodyMedium)
            Text(text = video.description)
            Text(text = "Tags: ${video.tags.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


