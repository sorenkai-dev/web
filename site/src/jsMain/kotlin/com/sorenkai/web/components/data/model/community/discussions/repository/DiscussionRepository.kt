package com.sorenkai.web.components.data.model.community.discussions.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.DiscussionApi
import com.sorenkai.web.auth.AuthProvider
import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionCreateDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionDeleteDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReadDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReportDto
import com.sorenkai.web.components.data.model.community.discussions.mapper.DiscussionMapper
import com.sorenkai.web.components.data.model.community.discussions.service.DiscussionService
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class DiscussionRepository(
    private val service: DiscussionService,
    private val api: DiscussionApi,
    private val auth: AuthProvider
) : IDiscussionRepository {
    private fun getCurrentUser(): UID? {
        return auth.getUserId()
    }

    private fun isModerator(): Boolean {
        return false // placeholder until roles are implemented
    }

    private fun getRootPost(discussion: Discussion, discussionMap: Map<String, Discussion>): Discussion? {
        var current = discussion
        while (current.kind == Kind.COMMENT && current.parentId != null) {
            current = discussionMap[current.parentId] ?: return null
        }
        return if (current.kind == Kind.POST) current else null
    }

    override suspend fun getDiscussions(
        writingSlug: String?,
        parentId: String?,
        pageSize: Int?,
        cursor: String?
    ): List<DiscussionReadDto> {
        val currentUserId = getCurrentUser()
        val isMod = isModerator()

        val res = api.getDiscussions(writingSlug, parentId, pageSize, cursor)
        if (res !is ApiResponse.Success) return emptyList()

        val discussions = res.data
        val discussionMap = discussions.associateBy { it.id }

        return discussions.map { discussion ->
            val rootPost = getRootPost(discussion, discussionMap)
            val rootPostIsVisible = rootPost?.isVisible ?: true

            DiscussionMapper.mapToReadDto(
                discussion = discussion,
                currentUserId = currentUserId,
                isModerator = isMod,
                rootPostIsVisible = rootPostIsVisible
            )
        }
    }

    override suspend fun createDiscussion(dto: DiscussionCreateDto): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.createDiscussion(dto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to create discussion")

        return mapToReadDto(res.data)
    }

    override suspend fun deleteDiscussion(discussionId: String): Boolean {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")
        // The backend `deleteOwn` handles ownership check.

        val res = api.deleteDiscussion(discussionId)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to delete discussion")

        return res.data
    }

    override suspend fun reportDiscussion(discussionId: String): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val reportDto = DiscussionReportDto(discussionId = discussionId, reason = "Reported by user")
        val res = api.reportDiscussion(discussionId, reportDto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to report discussion")

        return mapToReadDto(res.data)
    }

    override suspend fun moderateDiscussion(discussionId: String, dto: DiscussionModerationDto): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")
        if (!isModerator()) throw IllegalStateException("Not authorized")

        val res = api.moderateDiscussion(discussionId, dto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to moderate discussion")

        return mapToReadDto(res.data)
    }

    override suspend fun editDiscussion(discussionId: String, body: String): Boolean {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.editDiscussion(discussionId, body)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to edit discussion")

        return res.data
    }

    override suspend fun likeDiscussion(discussionId: String): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.like(discussionId)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to like discussion")

        return mapToReadDto(res.data)
    }

    override suspend fun unlikeDiscussion(discussionId: String): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.unlike(discussionId)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to unlike discussion")

        return mapToReadDto(res.data)
    }

    private suspend fun mapToReadDto(discussion: Discussion, allDiscussions: List<Discussion>? = null): DiscussionReadDto {
        val currentUserId = getCurrentUser()
        val isMod = isModerator()

        val discussions = allDiscussions ?: (api.getDiscussions(
            writingSlug = discussion.writingSlug,
            parentId = discussion.parentId
        ) as? ApiResponse.Success)?.data ?: emptyList()
        val discussionMap = discussions.associateBy { it.id }
        val rootPost = getRootPost(discussion, discussionMap)
        val rootPostIsVisible = rootPost?.isVisible ?: true

        return DiscussionMapper.mapToReadDto(discussion, currentUserId, isMod, rootPostIsVisible)
    }
}
