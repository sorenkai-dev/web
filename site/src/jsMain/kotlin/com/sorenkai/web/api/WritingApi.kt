package com.sorenkai.web.api

import com.sorenkai.web.api.dto.WritingDetailResponse
import com.sorenkai.web.api.dto.WritingListResponse
import com.sorenkai.web.components.data.model.writing.WritingEntry
import kotlinx.serialization.json.Json

class WritingApi(private val apiClient: ApiClient) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getTags(): ApiResponse<List<String>> =
        apiClient.get("/v2/tags") { json.decodeFromString(it) }

    suspend fun getWritings(
        tag: String? = null,
        limit: Int? = null,
        offset: Int? = null
    ): ApiResponse<List<WritingEntry>> {
        val params = org.w3c.dom.url.URLSearchParams()
        if (!tag.isNullOrBlank()) params.append("tag", tag)
        if (limit != null) params.append("limit", limit.toString())
        if (offset != null) params.append("offset", offset.toString())
        val query = params.toString()
        val qs = if (query.isNotEmpty()) "?$query" else ""

        return apiClient.get("/v2/writings$qs") {
            json.decodeFromString<WritingListResponse>(it).items
        }
    }

    suspend fun getWriting(slug: String): ApiResponse<WritingDetailResponse> =
        apiClient.get("/v2/writings/$slug") {
            json.decodeFromString<WritingDetailResponse>(it)
        }

    suspend fun incrementShare(slug: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$slug/share") { Unit }

    suspend fun like(slug: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$slug/like") { Unit }

    suspend fun unlike(slug: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$slug/unlike") { Unit }

    suspend fun incrementView(slug: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$slug/view") { Unit }

    suspend fun incrementSalesClick(slug: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$slug/click-sale") { Unit }
}
