package com.sorenkai.web.components.data.model.community.discussions

import com.sorenkai.web.api.dto.discussions.AuthorDto
import com.sorenkai.web.components.data.model.auth.UID
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Discussion(
    val id: String,
    val kind: Kind,
    val lang: String,
    val writingId: String? = null,
    val parentId: String? = null,
    val userId: String,
    val author: AuthorDto? = null,
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
    val isLikedByMe: Boolean = false,
    val isReportedByMe: Boolean = false,
    val childCount: Int = 0,
    val deletedAt: Instant? = null,
    val purgeAt: Instant? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
) {
    val isHidden: Boolean
        get() = status == DiscussionsStatus.HIDDEN_BY_REPORTS ||
            status == DiscussionsStatus.HIDDEN_BY_MODERATOR

    val isDeleted: Boolean
        get() = status == DiscussionsStatus.DELETED_BY_AUTHOR || status == DiscussionsStatus.DELETED_BY_MODERATOR

    val isVisible: Boolean
        get() = status == DiscussionsStatus.VISIBLE

    fun canRestore(currentUserId: String?): Boolean {
        if (currentUserId == null || userId != currentUserId) return false
        if (status != DiscussionsStatus.DELETED_BY_AUTHOR) return false
        val pAt = purgeAt ?: return false
        val now = Instant.fromEpochMilliseconds(js("Date.now()").unsafeCast<Double>().toLong())
        return now < pAt
    }
}
