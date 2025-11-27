package com.sorenkai.web.api.dto

import com.sorenkai.web.components.data.model.Stats
import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import kotlinx.serialization.Serializable

@Serializable
data class WritingDetailResponse (
    val title: String,
    val slug: String,
    val synopsis: String? = null,
    val type: Type,
    val salesLink: String? = null,
    val tags: List<String> = emptyList(),
    val featured: Boolean? = null,
    val seoDescription: String? = null,
    val content: String,
    val status: Status,
    val coverUrl: String? = null,
    val authorId: String,
    val publishedAt: String? = null,
    val createdAt: String,
    val updatedAt: String? = null,
    val stats: Stats
)
