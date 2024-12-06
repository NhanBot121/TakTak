package com.example.data.repository

import android.util.Log
import com.example.data.model.VideoDetails
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VideoRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveVideoDetails(videoDetails: VideoDetails) {
        val videoData = hashMapOf(
            "videoLink" to videoDetails.videoLink,
            "description" to videoDetails.description,
            "like" to videoDetails.like,
            "comment" to videoDetails.comment,
            "share" to videoDetails.share,
            "isLiked" to videoDetails.isLiked
        )

        firestore.collection("video")
            .add(videoData)
            .addOnSuccessListener { documentReference ->
                println("Video details saved with ID: ${documentReference.id}")
            }
            .await()
    }

    suspend fun getVideoDetails(): List<VideoDetails> {
        val snapshot = firestore.collection("video").get().await()
        val videoList = mutableListOf<VideoDetails>()

        // Collect all videos
        for (document in snapshot.documents) {
            val video = document.toObject(VideoDetails::class.java)
            video?.let { videoList.add(it) }
        }
        return videoList
    }

    suspend fun updateLike(like: Int, videoId: Int, user: String){
        val videoRef = firestore.collection(user).document("video$videoId")
        videoRef.update("like", like).await()
    }
}