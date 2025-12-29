package com.sorenkai.web.components.data.model.community.discussions.dto

import com.sorenkai.web.components.data.model.community.discussions.Kind
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCreateDto(
    val kind: Kind,
    val body: String,
    val writingSlug: String? = null,
    val parentId: String? = null
)
