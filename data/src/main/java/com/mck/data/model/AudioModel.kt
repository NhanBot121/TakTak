package com.mck.data.model

data class AudioModel(
    val audioCoverImage:String,
    val isOriginal:Boolean,
    val audioAuthor:UserModel,
    val numberOfPost:Long,
    val originalVideoUrl:String,
)