package com.mck.discovery.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mck.data.model.VideoDetails
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
        return snapshot.documents.mapNotNull { document ->
            document.toObject(VideoDetails::class.java)?.copy(documentId = document.id)
        }
    }
    suspend fun updateLike(like: Int, documentId: String) {
        try {
            val videoRef = firestore.collection("video").document(documentId)
            videoRef.update("like", like).await()
            Log.d("VideoRepository", "Updated likes for documentId: $documentId to $like")
        } catch (e: Exception) {
            Log.e("VideoRepository", "Error updating like: ${e.message}")
            throw e
        }
    }
    suspend fun searchVideos(query: String): List<VideoDetails> {
        try {
            val snapshot = firestore.collection("video")
                .whereEqualTo("description", query)
                .get()
                .await()

            val videoList = mutableListOf<VideoDetails>()
            for (document in snapshot.documents) {
                val video = document.toObject(VideoDetails::class.java)
                video?.let { videoList.add(it) }
            }
            Log.d("VideoRepository", "Search results: $videoList")
            return videoList
        } catch (e: Exception) {
            Log.e("VideoRepository", "Error during search: ${e.message}")
            return emptyList()
        }
    }
    suspend fun getVideosByTag(tags: String): List<VideoDetails> {
        val snapshot = firestore.collection("video")
            .whereArrayContains("tags", tags)  // Assuming you store tags as an array in Firestore
            .get()
            .await()

        val videoList = mutableListOf<VideoDetails>()
        for (document in snapshot.documents) {
            val video = document.toObject(VideoDetails::class.java)
            video?.let { videoList.add(it) }
        }
        return videoList
    }

}