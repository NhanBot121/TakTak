package com.example.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mck.data.model.VideoDetails

@Composable
fun CommentScreen(video: VideoDetails){
    Text(text = "Comments for ${video.description}")

}