package com.sorenkai.web.components.data.model.community.discussions.dto

import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.Kind
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionReadDto(
    val id: String,
    val kind: Kind,
    val writingSlug: String? = null,
    val parentId: String? = null,
    val userId: String?,
    val body: String,
    val status: DiscussionsStatus,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val isPinned: Boolean,
    val isLocked: Boolean,
    val likes: Int,
    val canDelete: Boolean = false,
    val canReport: Boolean = false,
    val canModerate: Boolean = false
)
