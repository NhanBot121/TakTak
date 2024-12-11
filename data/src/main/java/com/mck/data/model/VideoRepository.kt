package com.mck.data.model

import android.util.Log
import com.mck.data.model.VideoDetails
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VideoRepository {
    private val firestore = FirebaseFirestore.getInstance()


    suspend fun getVideoDetails(userId: String): List<VideoDetails> {
        val snapshot = firestore.collection(userId).get().await()
        val videoList = mutableListOf<VideoDetails>()

        // Collect all videos
        for (document in snapshot.documents) {
            val video = document.toObject(VideoDetails::class.java)
            video?.let { it ->
                val commentsSnapshot = firestore.collection(userId)
                    .document(document.id)
                    .collection("commentList")
                    .get()
                    .await()
                val comments = commentsSnapshot.mapNotNull { it.toObject(Comment::class.java) }

                val updatedVideo = it.copy(
                    comments = comments
                )
                Log.d("VideoRepository", "Comments: ${updatedVideo.comments}")
                videoList.add(updatedVideo)
            }


        }
        return videoList
    }


    suspend fun updateLike(like: Int, videoId: Int, user: String){
        val videoRef = firestore.collection(user).document("video$videoId")
        videoRef.update("like", like).await()
    }


}