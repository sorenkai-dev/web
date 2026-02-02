package com.sorenkai.web.repository.interfaces

import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.report.Report
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalTime::class)
interface IDiscussionRepository {
    val discussions: StateFlow<List<Discussion>>

    suspend fun fetchDiscussions(
        lang: String,
        writingId: String? = null,
        parentId: String? = null,
        pageSize: Int? = null,
        cursor: String? = null,
        order: DiscussionOrder? = null,
        append: Boolean = false
    )

    suspend fun createDiscussion(body: String, lang: String, dto: DiscussionCreateDto): Discussion

    suspend fun deleteDiscussion(discussionId: String): Discussion

    suspend fun restoreDiscussion(discussionId: String): Discussion

    suspend fun reportDiscussion(targetType: String, discussionId: String): Report

    suspend fun moderateDiscussion(discussionId: String, dto: DiscussionModerationDto): Discussion

    suspend fun editDiscussion(discussionId: String, body: String): Discussion

    suspend fun likeDiscussion(discussionId: String): Discussion

    suspend fun unlikeDiscussion(discussionId: String): Discussion
}
