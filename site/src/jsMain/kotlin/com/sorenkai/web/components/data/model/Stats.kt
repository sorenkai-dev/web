package com.sorenkai.web.components.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val shareCount: Int = 0,
    val salesClicks: Int = 0
)
