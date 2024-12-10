package com.mck.myprofile.user

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val following: Int = 0,
    val followers: Int = 0,
    val likes: Int = 0,
    val videoThumbnailUrls: List<String> = emptyList(), // Danh sách các video thumbnail
    val videoUrls: List<String> = emptyList() // Danh sách các video URL


)