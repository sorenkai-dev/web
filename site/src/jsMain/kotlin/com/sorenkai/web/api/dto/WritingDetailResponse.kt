package com.sorenkai.web.api.dto

import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class WritingDetail @OptIn(ExperimentalTime::class) constructor(
    val title: String,
    val slug: String,
    val synopsis: String? = null,
    val type: Type,
    val salesLink: String? = null,
    val content: String,
    val status: Status,
    val coverUrl: String? = null,
    val authorId: String,
    val publishedAt: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val stats: Stats
) {
    @Serializable
    data class Stats(
        val commentCount: Int,
        val viewCount: Int,
        val likeCount: Int
    )
}
