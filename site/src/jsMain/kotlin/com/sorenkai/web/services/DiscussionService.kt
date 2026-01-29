package com.sorenkai.web.services

import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.DiscussionModerationDto
import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class DiscussionService {
    /**
     * Posting & Commenting
     * Only authenticated users may:
     * Create discussion posts
     * Comment on discussion posts
     * Comment on writings
     */
    fun createDiscussion(
        userId: UID,
        dto: DiscussionCreateDto,
        generateId: () -> String,
        now: Instant
    ): Discussion {
        return Discussion(
            id = generateId(),
            kind = dto.kind,
            lang = dto.lang,
            writingId = dto.writingId,
            parentId = dto.parentId,
            userId = userId,
            body = dto.body,
            createdAt = now
        )
    }

    /**
     * Deletion
     * Users may delete their own posts or comments:
     * Soft delete only
     * Content replaced with "Removed by author" (handled in mapper)
     * Moderators/Admins may delete any post or comment:
     * Soft delete by default
     * Content replaced with "Removed by moderator" (handled in mapper)
     */
    fun deleteDiscussion(
        discussion: Discussion,
        isModerator: Boolean,
        moderatorId: UID?,
        now: Instant
    ): Discussion {
        if (discussion.isDeleted) {
            return discussion // Already deleted
        }

        val newStatus = if (isModerator) {
            DiscussionsStatus.DELETED_BY_MODERATOR
        } else {
            DiscussionsStatus.DELETED_BY_AUTHOR
        }

        return discussion.copy(
            status = newStatus,
            updatedAt = now,
            moderatedBy = if (isModerator) moderatorId else discussion.moderatedBy,
            moderatedAt = if (isModerator) now else discussion.moderatedAt
        )
    }

    /**
     * Reporting
     * Only authenticated users may report content.
     * Users may not report their own content.
     * A user may report a given post or comment only once.
     * After 3 unique reports:
     * Content is automatically set to HIDDEN_BY_REPORTS.
     */
    fun reportDiscussion(
        discussion: Discussion,
        userId: UID
    ): Discussion {
        if (discussion.userId == userId) {
            throw IllegalStateException("You cannot report your own content")
        }

        if (discussion.reportedBy.contains(userId)) {
            return discussion // Already reported by this user
        }

        // Once content has been moderated: Reporting is disabled.
        // Reporting becomes available again only if the author edits the content.
        if (discussion.status == DiscussionsStatus.HIDDEN_BY_MODERATOR ||
            discussion.status == DiscussionsStatus.DELETED_BY_MODERATOR) {
            throw IllegalStateException("Reporting is disabled for moderated content")
        }

        val newReportedBy = discussion.reportedBy + userId
        var newStatus = discussion.status

        // After 3 unique reports: Content is automatically set to HIDDEN_BY_REPORTS.
        if (newReportedBy.size >= 3 && discussion.status == DiscussionsStatus.VISIBLE) {
            newStatus = DiscussionsStatus.HIDDEN_BY_REPORTS
        }

        return discussion.copy(
            reportedBy = newReportedBy,
            status = newStatus
        )
    }

    /**
     * Moderation
     * Moderators/Admins may:
     * Hide content
     * Restore content
     * Delete content
     * Moderator actions override automatic report-based hiding.
     */
    fun moderateDiscussion(
        discussion: Discussion,
        moderatorId: UID,
        dto: DiscussionModerationDto,
        now: Instant
    ): Discussion {
        // Moderator actions override automatic report-based hiding.
        return discussion.copy(
            status = dto.newStatus,
            reason = dto.reason,
            moderatedBy = moderatorId,
            moderatedAt = now,
            updatedAt = now
        )
    }

    /**
     * Editing content (implied by "Reporting becomes available again only if the author edits the content")
     */
    fun editDiscussion(
        discussion: Discussion,
        newBody: String,
        now: Instant
    ): Discussion {
        if (discussion.isDeleted) {
            throw IllegalStateException("Cannot edit deleted content")
        }

        // Once content has been moderated: Reporting is disabled.
        // Reporting becomes available again only if the author edits the content.
        val shouldRestoreVisibility = (discussion.status == DiscussionsStatus.VISIBLE ||
            discussion.status == DiscussionsStatus.HIDDEN_BY_REPORTS)

        return discussion.copy(
            body = newBody,
            reportedBy = emptySet(), // Reset reports on edit
            status = if (shouldRestoreVisibility) DiscussionsStatus.VISIBLE else discussion.status,
            updatedAt = now
        )
    }
}
