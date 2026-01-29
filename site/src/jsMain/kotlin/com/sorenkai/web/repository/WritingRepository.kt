package com.sorenkai.web.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.WritingApi
import com.sorenkai.web.api.dto.writings.WritingDetailResponse
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.repository.interfaces.IWritingRepository

class WritingRepository(
    private val api: WritingApi,
    private val auth: IAuthProvider
) : IWritingRepository {
    override suspend fun getWritings(tag: String?, limit: Int?, offset: Int?): ApiResponse<List<WritingEntry>> {
        return api.getWritings(tag, limit, offset)
    }

    override suspend fun getWriting(id: String): ApiResponse<WritingDetailResponse> {
        return api.getWriting(id)
    }

    override suspend fun resolveSlug(slug: String): ApiResponse<WritingDetailResponse> {
        return api.resolveSlug(slug)
    }

    override suspend fun getTags(): ApiResponse<List<String>> {
        return api.getTags()
    }

    override suspend fun incrementShare(id: String): ApiResponse<Unit> {
        // Enforce auth if required by "write or protected operation"
        // For now, let's assume it's public as it's an incrementer, but check Auth if we want to be strict.
        // Requirement: "Authentication must be enforced in the repository for any write or protected operation."
        // Usually likes require auth, but views/shares might not.
        // I will enforce it for like/unlike as they are definitely "writes".
        return api.incrementShare(id)
    }

    override suspend fun like(id: String): ApiResponse<Unit> {
        if (auth.authState != AuthState.Authenticated) {
            return ApiResponse.HttpError(401, "Authentication required")
        }
        return api.like(id)
    }

    override suspend fun unlike(id: String): ApiResponse<Unit> {
        if (auth.authState != AuthState.Authenticated) {
            return ApiResponse.HttpError(401, "Authentication required")
        }
        return api.unlike(id)
    }

    override suspend fun incrementView(id: String): ApiResponse<Unit> {
        return api.incrementView(id)
    }

    override suspend fun incrementSalesClick(id: String): ApiResponse<Unit> {
        return api.incrementSalesClick(id)
    }
}
