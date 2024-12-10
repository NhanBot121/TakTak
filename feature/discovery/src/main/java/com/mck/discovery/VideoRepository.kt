package com.mck.discovery

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VideoRepository {

    private val db = FirebaseFirestore.getInstance()
    // Fetch videos by tag from Firestore
    suspend fun fetchVideosByTag(tag: String): List<Video> {
        return try {
            val querySnapshot = db.collection("video") // Collection name
                .whereArrayContains("tags", tag) // Query by tags
                .get()
                .await()

            val videos = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Video::class.java)
            }
            Log.d("VideoRepository", "Fetched videos: $videos")
            videos
        } catch (e: Exception) {
            Log.e("VideoRepository", "Error fetching videos: ${e.message}")
            emptyList()
        }
    }
}