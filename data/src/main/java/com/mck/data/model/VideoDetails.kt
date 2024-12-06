package com.example.data.model

data class VideoDetails(
    //val authorDetails: UserModel,
    val videoLink: String,
    val description: String,
    var like: Int = 0,
    var comment: Int = 0,
    var share: Int = 0,
    var isLiked: Boolean = false
){
    // No-argument constructor for Firestore deserialization
    constructor() : this("", "", 0, 0, 0)
}

