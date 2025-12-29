package com.sorenkai.web.components.data.model.community.discussions

import com.sorenkai.web.components.data.model.auth.UID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Discussion(
    val id: String,
    val kind: Kind,
    val writingSlug: String? = null,
    val parentId: String? = null,
    val userId: String,
    val body: String,
    // Moderation
    val status: DiscussionsStatus = DiscussionsStatus.VISIBLE,
    val reason: String? = null,
    val moderatedBy: UID? = null,
    val moderatedAt: Instant? = null,
    // Reporting
    val reportedBy: Set<UID> = emptySet(),
    // Admin
    val isPinned: Boolean = false,
    val isLocked: Boolean = false,
    val likes: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
) {
    val isDeleted: Boolean
        get() = status == DiscussionsStatus.DELETED_BY_AUTHOR || status == DiscussionsStatus.DELETED_BY_MODERATOR

    val isHidden: Boolean
        get() = status == DiscussionsStatus.HIDDEN_BY_REPORTS ||
            status == DiscussionsStatus.HIDDEN_BY_MODERATOR ||
            isDeleted

    val isVisible: Boolean
        get() = !isHidden
}
