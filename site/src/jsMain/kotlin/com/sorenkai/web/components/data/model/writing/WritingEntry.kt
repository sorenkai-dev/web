package com.sorenkai.web.components.data.model.writing

import com.sorenkai.web.api.dto.discussions.UserData
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class WritingEntry(
    val id: String,
    val title: String,
    val slug: String,
    val language: String,
    val author: UserData? = null,
    val synopsis: String? = null,
    val writingsStatus: WritingsStatus,
    val writingsType: WritingsType,
    val salesLink: String? = null,
    val content: String? = null,
    val coverUrl: String? = null,
    val authorId: String? = null,
    val tags: List<String> = emptyList(),
    val featured: Boolean? = null,
    val seoDescription: String? = null,
    val stats: Stats = Stats(),
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant? = null,
    @Contextual val publishedAt: Instant? = null
)
