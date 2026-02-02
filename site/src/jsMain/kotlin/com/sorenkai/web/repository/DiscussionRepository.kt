package com.sorenkai.web.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.DiscussionApi
import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.DiscussionModerationDto
import com.sorenkai.web.api.dto.discussions.DiscussionReportDto
import com.sorenkai.web.api.dto.discussions.mapper.DiscussionDomainMapper
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.auth.UID
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.repository.interfaces.IDiscussionRepository
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalTime::class)
class DiscussionRepository(
    private val api: DiscussionApi,
    private val auth: IAuthProvider
) : IDiscussionRepository {
    private val _discussions = MutableStateFlow<List<Discussion>>(emptyList())
    override val discussions: StateFlow<List<Discussion>> = _discussions.asStateFlow()

    private fun getCurrentUser(): UID? {
        return auth.getUserId()
    }

    override suspend fun fetchDiscussions(
        lang: String,
        writingId: String?,
        parentId: String?,
        pageSize: Int?,
        cursor: String?,
        order: DiscussionOrder?,
        append: Boolean
    ) {
        val effictiveOrder = if (parentId != null) DiscussionOrder.OLDEST else order
        val res = api.getDiscussions(lang, writingId, parentId, pageSize, cursor, effictiveOrder)
        if (res is ApiResponse.Success) {
            val newDiscussions = res.data.map { DiscussionDomainMapper.mapToDomain(it) }
            if (append) {
                val existingIds = _discussions.value.map { it.id }.toSet()
                val filteredNew = newDiscussions.filter { it.id !in existingIds }
                _discussions.value += filteredNew
            } else {
                _discussions.value = newDiscussions
            }
        }
    }

    override suspend fun createDiscussion(body: String, lang: String, dto: DiscussionCreateDto): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.createDiscussion(dto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to create discussion")

        val discussion = DiscussionDomainMapper.mapToDomain(res.data)
        _discussions.value += discussion
        return discussion
    }

    override suspend fun deleteDiscussion(discussionId: String): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.deleteDiscussion(discussionId)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to delete discussion")

        val updated = DiscussionDomainMapper.mapToDomain(res.data)
        _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
        return updated
    }

    override suspend fun restoreDiscussion(discussionId: String): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.restoreDiscussion(discussionId)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to restore discussion")

        val updated = DiscussionDomainMapper.mapToDomain(res.data)
        _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
        return updated
    }

    override suspend fun reportDiscussion(targetType: String, discussionId: String): Report {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val reportDto = DiscussionReportDto(targetType = targetType, targetId = discussionId, reason = "Reported by user")
        val res = api.reportDiscussion(reportDto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to report discussion")

        _discussions.value = _discussions.value.map {
            if (it.id == discussionId) it.copy(isReportedByMe = true) else it
        }

        return res.data
    }

    override suspend fun moderateDiscussion(discussionId: String, dto: DiscussionModerationDto): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")
        // Role check should be in repository or backend. Requirements say "Enforce auth and role checks".
        // Repository remains the boundary.

        val res = api.moderateDiscussion(discussionId, dto)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to moderate discussion")

        val updated = DiscussionDomainMapper.mapToDomain(res.data)
        _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
        return updated
    }

    override suspend fun editDiscussion(discussionId: String, body: String): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        val res = api.editDiscussion(discussionId, body)
        if (res !is ApiResponse.Success) throw RuntimeException("Failed to edit discussion")

        val updated = DiscussionDomainMapper.mapToDomain(res.data)
        _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
        return updated
    }

    override suspend fun likeDiscussion(discussionId: String): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        // Optimistic UI: Update local state immediately
        val currentDiscussions = _discussions.value
        _discussions.value = currentDiscussions.map {
            if (it.id == discussionId && !it.isLikedByMe) {
                it.copy(likes = it.likes + 1, isLikedByMe = true)
            } else it
        }

        return try {
            val res = api.like(discussionId)
            if (res !is ApiResponse.Success) {
                // Rollback if failed
                _discussions.value = currentDiscussions
                throw RuntimeException("Failed to like discussion")
            }
            val updated = DiscussionDomainMapper.mapToDomain(res.data)
            _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
            updated
        } catch (e: Exception) {
            // Rollback if failed
            _discussions.value = currentDiscussions
            throw e
        }
    }

    override suspend fun unlikeDiscussion(discussionId: String): Discussion {
        getCurrentUser() ?: throw IllegalStateException("Must be authenticated")

        // Optimistic UI: Update local state immediately
        val currentDiscussions = _discussions.value
        _discussions.value = currentDiscussions.map {
            if (it.id == discussionId && it.isLikedByMe) {
                it.copy(likes = (it.likes - 1).coerceAtLeast(0), isLikedByMe = false)
            } else it
        }

        return try {
            val res = api.unlike(discussionId)
            if (res !is ApiResponse.Success) {
                // Rollback if failed
                _discussions.value = currentDiscussions
                throw RuntimeException("Failed to unlike discussion")
            }
            val updated = DiscussionDomainMapper.mapToDomain(res.data)
            _discussions.value = _discussions.value.map { if (it.id == discussionId) updated else it }
            updated
        } catch (e: Exception) {
            // Rollback if failed
            _discussions.value = currentDiscussions
            throw e
        }
    }
}
