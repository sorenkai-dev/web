package com.sorenkai.web.components.data.model.community.discussions.repository

import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionCreateDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReadDto
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
interface IDiscussionRepository {
    suspend fun getDiscussions(
        writingSlug: String? = null,
        parentId: String? = null,
        pageSize: Int? = null,
        cursor: String? = null
    ): List<DiscussionReadDto>

    suspend fun createDiscussion(dto: DiscussionCreateDto): DiscussionReadDto

    suspend fun deleteDiscussion(discussionId: String): Boolean

    suspend fun reportDiscussion(discussionId: String): DiscussionReadDto

    suspend fun moderateDiscussion(discussionId: String, dto: DiscussionModerationDto): DiscussionReadDto

    suspend fun editDiscussion(discussionId: String, body: String): Boolean

    suspend fun likeDiscussion(discussionId: String): DiscussionReadDto

    suspend fun unlikeDiscussion(discussionId: String): DiscussionReadDto
}
