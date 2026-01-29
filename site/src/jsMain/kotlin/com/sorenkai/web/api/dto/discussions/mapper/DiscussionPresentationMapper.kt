package com.sorenkai.web.api.dto.discussions.mapper

import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.api.dto.discussions.DiscussionReadDto

class DiscussionPresentationMapper {
    fun mapToReadDtos(
        discussions: List<Discussion>,
        currentUserId: UID?,
        isModerator: Boolean
    ): List<DiscussionReadDto> {
        return discussions.map { discussion ->
            DiscussionMapper.mapToReadDto(
                discussion = discussion,
                currentUserId = currentUserId,
                isModerator = isModerator
            )
        }
    }
}
