package com.sorenkai.web.api.dto

import com.sorenkai.web.components.data.model.writing.Stats
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import com.sorenkai.web.components.data.model.writing.WritingsType
import kotlinx.serialization.Serializable

@Serializable
data class WritingDetailResponse (
    val title: String,
    val slug: String,
    val synopsis: String? = null,
    val writingsType: WritingsType,
    val salesLink: String? = null,
    val tags: List<String> = emptyList(),
    val featured: Boolean? = null,
    val seoDescription: String? = null,
    val content: String,
    val writingsStatus: WritingsStatus,
    val coverUrl: String? = null,
    val authorId: String,
    val publishedAt: String? = null,
    val createdAt: String,
    val updatedAt: String? = null,
    val stats: Stats
)
