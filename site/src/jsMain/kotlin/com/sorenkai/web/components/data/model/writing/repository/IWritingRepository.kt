package com.sorenkai.web.components.data.model.writing.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingDetailResponse
import com.sorenkai.web.components.data.model.writing.WritingEntry

interface IWritingRepository {
    suspend fun getWritings(tag: String? = null, limit: Int? = null, offset: Int? = null): ApiResponse<List<WritingEntry>>

    suspend fun getWriting(slug: String): ApiResponse<WritingDetailResponse>

    suspend fun getTags(): ApiResponse<List<String>>

    suspend fun incrementShare(slug: String): ApiResponse<Unit>

    suspend fun like(slug: String): ApiResponse<Unit>

    suspend fun unlike(slug: String): ApiResponse<Unit>

    suspend fun incrementView(slug: String): ApiResponse<Unit>

    suspend fun incrementSalesClick(slug: String): ApiResponse<Unit>
}
