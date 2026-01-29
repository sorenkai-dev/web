package com.sorenkai.web.components.data.model.writing

import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val likesCount: Int = 0,
    val isLikedByMe: Boolean = false,
    val shareCount: Int = 0,
    val salesClicks: Int = 0
)
