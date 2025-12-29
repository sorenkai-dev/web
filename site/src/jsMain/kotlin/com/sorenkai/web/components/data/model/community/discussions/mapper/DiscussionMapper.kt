package com.sorenkai.web.components.data.model.community.discussions.mapper

import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReadDto

object DiscussionMapper {
    fun mapToReadDto(
        discussion: Discussion,
        currentUserId: UID?,
        isModerator: Boolean = false,
        rootPostIsVisible: Boolean = true
    ): DiscussionReadDto {
        val isAuthor = currentUserId != null && discussion.userId == currentUserId

        val isModerated = discussion.status == DiscussionsStatus.HIDDEN_BY_MODERATOR ||
            discussion.status == DiscussionsStatus.DELETED_BY_MODERATOR

        val displayBody = when {
            discussion.status == DiscussionsStatus.DELETED_BY_AUTHOR -> "Removed by author"
            discussion.status == DiscussionsStatus.DELETED_BY_MODERATOR -> "Removed by moderator"
            !rootPostIsVisible -> "[Post unavailable]"
            discussion.isHidden -> if (isModerator) {
                discussion.body
            } else {
                when (discussion.status) {
                    DiscussionsStatus.HIDDEN_BY_REPORTS -> "[Hidden by reports]"
                    DiscussionsStatus.HIDDEN_BY_MODERATOR -> "[Hidden by moderator]"
                    else -> discussion.body
                }
            }
            else -> discussion.body
        }

        // Identity hiding: "Author identity is retained internally but hidden from public DTOs after deletion."
        val displayUserId = if (discussion.isDeleted && !isModerator) {
            null
        } else {
            discussion.userId
        }

        val isMaskedByRoot = !rootPostIsVisible && !discussion.isDeleted

        return DiscussionReadDto(
            id = discussion.id,
            kind = discussion.kind,
            writingSlug = discussion.writingSlug,
            parentId = discussion.parentId,
            userId = displayUserId,
            body = displayBody,
            status = discussion.status,
            createdAt = discussion.createdAt,
            updatedAt = discussion.updatedAt,
            isPinned = discussion.isPinned,
            isLocked = discussion.isLocked,
            likes = discussion.likes,
            canDelete = currentUserId != null && (isAuthor || isModerator) && !discussion.isDeleted,
            canReport = currentUserId != null &&
                !isAuthor &&
                !discussion.reportedBy.contains(currentUserId) &&
                discussion.isVisible &&
                !discussion.isDeleted &&
                !isMaskedByRoot &&
                !isModerated,
            canModerate = isModerator
        )
    }
}
