package com.mck.data.model

data class VideoDetails(
    //val authorDetails: UserModel,
    val videoLink: String,
    val description: String,
    var like: Int = 0,
    var comment: Int = 0,
    var share: Int = 0,
    var isLiked: Boolean = false,
    val tags: List<String> = emptyList(),
    var documentId: String = "",
    val audioModel: AudioModel? = null,
    val comments: List<Comment> = emptyList()
){
    // No-argument constructor for Firestore deserialization
    constructor() : this("", "", 0, 0, 0,false, emptyList(),"" )
}

