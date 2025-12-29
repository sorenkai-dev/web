package com.sorenkai.web.components.data.model.community.discussions.repository

import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionCreateDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReadDto
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
interface IDiscussionRepository {
    suspend fun getDiscussions(): List<DiscussionReadDto>

    suspend fun createDiscussion(dto: DiscussionCreateDto): DiscussionReadDto

    suspend fun deleteDiscussion(discussionId: String): DiscussionReadDto

    suspend fun reportDiscussion(discussionId: String): DiscussionReadDto

    suspend fun moderateDiscussion(discussionId: String, dto: DiscussionModerationDto): DiscussionReadDto

    suspend fun editDiscussion(discussionId: String, body: String): DiscussionReadDto
}
