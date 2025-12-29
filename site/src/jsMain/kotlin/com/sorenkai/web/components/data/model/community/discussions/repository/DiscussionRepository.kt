package com.sorenkai.web.components.data.model.community.discussions.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.DiscussionApi
import com.sorenkai.web.auth.Auth
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
    private val api: DiscussionApi
) : IDiscussionRepository {
    private fun getCurrentUser(): UID? {
        return if (Auth.instance.isAuthenticated()) "current-user-id" else null // placeholder
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

    override suspend fun getDiscussions(): List<DiscussionReadDto> {
        val currentUserId = getCurrentUser()
        val isMod = isModerator()

        val res = api.getDiscussions()
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

    override suspend fun deleteDiscussion(discussionId: String): DiscussionReadDto {
        val userId = getCurrentUser() ?: throw IllegalStateException("Must be authenticated")
        val isMod = isModerator()

        // We need to fetch it first to check ownership if not mod
        val allRes = api.getDiscussions()
        if (allRes !is ApiResponse.Success) throw RuntimeException("Failed to fetch discussions")
        val discussion = allRes.data.find { it.id == discussionId } ?: throw IllegalArgumentException("Not found")

        val isAuthor = discussion.userId == userId
        if (!isAuthor && !isMod) {
            throw IllegalStateException("Not authorized to delete")
        }

        val deleteDto = DiscussionDeleteDto(discussionId = discussionId)
        val res = api.deleteDiscussion(discussionId, deleteDto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to delete discussion")

        return mapToReadDto(res.data, allRes.data)
    }

    override suspend fun reportDiscussion(discussionId: String): DiscussionReadDto {
        val userId = getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

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

    override suspend fun editDiscussion(discussionId: String, body: String): DiscussionReadDto {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.editDiscussion(discussionId, body)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to edit discussion")

        return mapToReadDto(res.data)
    }

    private suspend fun mapToReadDto(discussion: Discussion, allDiscussions: List<Discussion>? = null): DiscussionReadDto {
        val currentUserId = getCurrentUser()
        val isMod = isModerator()

        val discussions = allDiscussions ?: (api.getDiscussions() as? ApiResponse.Success)?.data ?: emptyList()
        val discussionMap = discussions.associateBy { it.id }
        val rootPost = getRootPost(discussion, discussionMap)
        val rootPostIsVisible = rootPost?.isVisible ?: true

        return DiscussionMapper.mapToReadDto(discussion, currentUserId, isMod, rootPostIsVisible)
    }
}
