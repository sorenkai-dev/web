package com.sorenkai.web.api.dto.discussions

import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.Kind
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class DiscussionReadDto(
    val id: String,
    val kind: Kind,
    val lang: String = "en",
    val writingId: String? = null,
    val parentId: String? = null,
    val userId: String?,
    val author: AuthorDto? = null,
    val body: String,
    val status: DiscussionsStatus,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val isPinned: Boolean,
    val isLocked: Boolean,
    val likes: Int,
    val isLikedByMe: Boolean = false,
    val isReportedByMe: Boolean = false,
    val childCount: Int,
    val canDelete: Boolean = false,
    val canRestore: Boolean = false,
    val canReport: Boolean = false,
    val canModerate: Boolean = false
)
