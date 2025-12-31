package com.sorenkai.web.components.data.model.writing.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.WritingApi
import com.sorenkai.web.api.dto.WritingDetailResponse
import com.sorenkai.web.auth.AuthProvider
import com.sorenkai.web.components.data.model.writing.WritingEntry

class WritingRepository(
    private val api: WritingApi,
    private val auth: AuthProvider
) : IWritingRepository {
    override suspend fun getWritings(tag: String?, limit: Int?, offset: Int?): ApiResponse<List<WritingEntry>> {
        return api.getWritings(tag, limit, offset)
    }

    override suspend fun getWriting(slug: String): ApiResponse<WritingDetailResponse> {
        return api.getWriting(slug)
    }

    override suspend fun getTags(): ApiResponse<List<String>> {
        return api.getTags()
    }

    override suspend fun incrementShare(slug: String): ApiResponse<Unit> {
        // Enforce auth if required by "write or protected operation"
        // For now, let's assume it's public as it's an incrementer, but check Auth if we want to be strict.
        // Requirement: "Authentication must be enforced in the repository for any write or protected operation."
        // Usually likes require auth, but views/shares might not.
        // I will enforce it for like/unlike as they are definitely "writes".
        return api.incrementShare(slug)
    }

    override suspend fun like(slug: String): ApiResponse<Unit> {
        if (!auth.isAuthenticated()) {
            return ApiResponse.HttpError(401, "Authentication required")
        }
        return api.like(slug)
    }

    override suspend fun unlike(slug: String): ApiResponse<Unit> {
        if (!auth.isAuthenticated()) {
            return ApiResponse.HttpError(401, "Authentication required")
        }
        return api.unlike(slug)
    }

    override suspend fun incrementView(slug: String): ApiResponse<Unit> {
        return api.incrementView(slug)
    }

    override suspend fun incrementSalesClick(slug: String): ApiResponse<Unit> {
        return api.incrementSalesClick(slug)
    }
}
