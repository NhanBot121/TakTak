package com.mck.discovery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mck.data.model.VideoDetails
import com.mck.discovery.model.HomeViewModel


@Composable
fun InteractButtons(
    modifier: Modifier = Modifier,
    video: VideoDetails,
    onAvatarClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    viewModel: HomeViewModel,
    pageIndex: Int
){
    val like = remember { mutableIntStateOf(video.like) }
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User profile",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable { onAvatarClicked() }
        )

        // Like Button
        IconButton(onClick = {
            if(!video.isLiked){
                like.intValue = video.like + 1
                video.like = like.intValue
                viewModel.updateLike(like.intValue, video.documentId, "video")
                video.isLiked = true
            } else {
                like.intValue = video.like - 1
                video.like = like.intValue
                viewModel.updateLike(like.intValue, video.documentId, "video")
                video.isLiked = false
            }

        }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = if(!video.isLiked) Color.White else Color.Red
            )
        }
        Text("${like.intValue}", color = Color.White)

        // Comment Button
        IconButton(onClick = {
            onCommentClicked()
        }) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Comment",
                tint = Color.White
            )
        }
        Text("${video.comment}", color = Color.White)

        // Share Button
        IconButton(onClick = {
            //viewModel.shareVideo(video)
        }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }
        Text("${video.share}", color = Color.White)
    }
}