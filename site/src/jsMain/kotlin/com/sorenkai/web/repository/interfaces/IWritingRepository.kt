package com.sorenkai.web.repository.interfaces

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.writings.WritingDetailResponse
import com.sorenkai.web.components.data.model.writing.WritingEntry

interface IWritingRepository {
    suspend fun getWritings(tag: String? = null, limit: Int? = null, offset: Int? = null): ApiResponse<List<WritingEntry>>

    suspend fun getWriting(id: String): ApiResponse<WritingDetailResponse>

    suspend fun resolveSlug(slug: String): ApiResponse<WritingDetailResponse>

    suspend fun getTags(): ApiResponse<List<String>>

    suspend fun incrementShare(id: String): ApiResponse<Unit>

    suspend fun like(id: String): ApiResponse<Unit>

    suspend fun unlike(id: String): ApiResponse<Unit>

    suspend fun incrementView(id: String): ApiResponse<Unit>

    suspend fun incrementSalesClick(id: String): ApiResponse<Unit>
}
