package com.sorenkai.web.components.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WritingEntry(
    val title: String,
    val slug: String,
    val synopsis: String,
    val status: Status,
    val type: Type,
    val salesLink: String? = null,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
enum class Status { DRAFT, PUBLISHED, ARCHIVED, SUBMITTED }

@Serializable
enum class Type { ARTICLE, BOOK }
