package com.mck.data.model

import android.net.Uri

data class ContentCreatorFollowingModel(
    val userModel: UserModel,
    val coverVideo: VideoModel
) {
    fun parseVideo(): Uri = Uri.parse("asset:///videos/${coverVideo.videoLink}")
}