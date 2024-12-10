package com.mck.data.model

data class Comment(
    val userId: String,
    val content: String?,
    //val createdAt: String,
    //val totalLike: Long,
    //val totalDisLike: Long,
    //val threadCount: Int,
    //val thread: List<Comment>
){
    constructor() : this("", "")

}