package com.sorenkai.web.api.dto.discussions.mapper

import com.sorenkai.web.api.dto.discussions.DiscussionDto
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object DiscussionDomainMapper {
    fun mapToDomain(dto: DiscussionDto): Discussion {
        return Discussion(
            id = dto.id,
            kind = dto.kind,
            lang = dto.lang,
            writingId = dto.writingId,
            parentId = dto.parentId,
            userId = dto.userId,
            author = dto.author,
            body = dto.body,
            status = dto.status,
            reason = dto.reason,
            moderatedBy = dto.moderatedBy,
            moderatedAt = dto.moderatedAt,
            reportedBy = dto.reportedBy,
            isPinned = dto.isPinned,
            isLocked = dto.isLocked,
            likes = dto.likesCount,
            isLikedByMe = dto.isLikedByMe,
            childCount = dto.childCount,
            deletedAt = dto.deletedAt,
            purgeAt = dto.purgeAt,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }
}
