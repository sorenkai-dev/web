package com.sorenkai.web.components.data.model.writing

import kotlinx.serialization.Serializable

@Serializable
data class WritingEntry(
    val title: String,
    val language: String,
    val slug: String,
    val synopsis: String,
    val writingsStatus: WritingsStatus,
    val writingsType: WritingsType,
    val salesLink: String? = null,
    val tags: List<String> = emptyList(),
    val featured: Boolean? = null,
    val seoDescription: String? = null,
    val stats: Stats = Stats(),
    val createdAt: String,
    // Make optional when backend omits the field entirely
    val updatedAt: String? = null,
    val publishedAt: String? = null
)
