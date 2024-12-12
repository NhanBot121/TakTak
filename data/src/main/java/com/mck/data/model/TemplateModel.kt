package com.mck.data.model

import android.net.Uri

data class TemplateModel(
    val id: Long,
    val name: String,
    val hint: String,
    val mediaUrl: String
){
    fun parseMediaLink(): Uri = Uri.parse("file:///android_asset/templateimages/${mediaUrl}")
}