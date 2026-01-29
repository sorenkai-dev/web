package com.sorenkai.web.api.dto.discussions

import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.Kind
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDto(
    val id: String,
    val kind: Kind,
    val lang: String = "en",
    val writingId: String? = null,
    val parentId: String? = null,
    val userId: String,
    val author: AuthorDto? = null,
    val body: String,
    val status: DiscussionsStatus,
    val reason: String? = null,
    val moderatedBy: UID? = null,
    val moderatedAt: Instant? = null,
    val reportedBy: Set<UID> = emptySet(),
    val isPinned: Boolean = false,
    val isLocked: Boolean = false,
    val likesCount: Int = 0,
    val isLikedByMe: Boolean = false,
    val childCount: Int = 0,
    val deletedAt: Instant? = null,
    val purgeAt: Instant? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)

@Serializable
data class UserData(
    val userId: String,
    val username: String,
    val displayName: String? = null
)

@Serializable
data class AuthorDto(
    val userId: String,
    val username: String,
    val displayName: String? = null
)
