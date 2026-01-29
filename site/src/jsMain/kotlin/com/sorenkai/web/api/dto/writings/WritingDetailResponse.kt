package com.sorenkai.web.api.dto.writings

import com.sorenkai.web.api.dto.discussions.UserData
import com.sorenkai.web.components.data.model.writing.Stats
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import com.sorenkai.web.components.data.model.writing.WritingsType
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class WritingDetailResponse(
    val id: String,
    val title: String,
    val slug: String,
    val language: String,
    val author: UserData? = null,
    val synopsis: String? = null,
    val writingsType: WritingsType,
    val salesLink: String? = null,
    val content: String,
    val writingsStatus: WritingsStatus,
    val coverUrl: String? = null,
    val authorId: String,
    @Contextual val publishedAt: Instant? = null,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant? = null,
    val stats: Stats,
    val tags: List<String> = emptyList(),
    val featured: Boolean? = null,
    val seoDescription: String? = null,
)
