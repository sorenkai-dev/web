package com.sorenkai.web.api.dto.discussions.mapper

import com.sorenkai.web.api.dto.discussions.AuthorDto
import com.sorenkai.web.api.dto.discussions.DiscussionReadDto
import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus

object DiscussionMapper {
    fun mapToReadDto(
        discussion: Discussion,
        currentUserId: UID?,
        isModerator: Boolean = false
    ): DiscussionReadDto {
        val isAuthor = currentUserId != null && discussion.userId == currentUserId

        val isModerated = discussion.status == DiscussionsStatus.HIDDEN_BY_MODERATOR ||
            discussion.status == DiscussionsStatus.DELETED_BY_MODERATOR

        val displayBody = when {
            discussion.status == DiscussionsStatus.DELETED_BY_AUTHOR -> "Removed by author"
            discussion.status == DiscussionsStatus.DELETED_BY_MODERATOR -> "Removed by moderator"
            discussion.status == DiscussionsStatus.HIDDEN_BY_REPORTS -> if (isModerator) discussion.body else "[Hidden by reports]"
            discussion.status == DiscussionsStatus.HIDDEN_BY_MODERATOR -> if (isModerator) discussion.body else "[Hidden by moderator]"
            else -> discussion.body
        }

        // Identity hiding: "Author identity is retained internally but hidden from public DTOs after deletion."
        val displayAuthor =
            if (discussion.isDeleted && !isModerator) {
                null
            } else {
                discussion.author?.let {
                    AuthorDto(
                        userId = it.userId,
                        username = it.username,
                        displayName = it.displayName
                    )
                }
            }

        return DiscussionReadDto(
            id = discussion.id,
            kind = discussion.kind,
            writingId = discussion.writingId,
            parentId = discussion.parentId,
            userId = if (discussion.isDeleted && !isModerator) null else discussion.userId,
            author = displayAuthor,
            body = displayBody,
            status = discussion.status,
            createdAt = discussion.createdAt,
            updatedAt = discussion.updatedAt,
            isPinned = discussion.isPinned,
            isLocked = discussion.isLocked,
            likes = discussion.likes,
            isLikedByMe = discussion.isLikedByMe,
            isReportedByMe = discussion.isReportedByMe,
            childCount = discussion.childCount,
            canDelete = currentUserId != null && (isAuthor || isModerator) && !discussion.isDeleted,
            canRestore = discussion.canRestore(currentUserId),
            canReport = currentUserId != null &&
                !isAuthor &&
                !discussion.reportedBy.contains(currentUserId) &&
                discussion.isVisible &&
                !discussion.isDeleted &&
                !isModerated,
            canModerate = isModerator
        )
    }
}
