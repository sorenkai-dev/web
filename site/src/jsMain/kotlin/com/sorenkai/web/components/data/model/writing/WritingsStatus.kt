package com.sorenkai.web.components.data.model.writing

import kotlinx.serialization.Serializable

@Serializable
enum class WritingsStatus {
    DRAFT,
    PUBLISHED,
    ARCHIVED,
    SUBMITTED
}
