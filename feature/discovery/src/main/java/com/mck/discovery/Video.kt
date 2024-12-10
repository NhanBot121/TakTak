package com.mck.discovery
data class Video(
    val title: String = "",
    val description: String = "",
    val tags: List<String> = emptyList(),
    val videoLink: String? = null,
)


