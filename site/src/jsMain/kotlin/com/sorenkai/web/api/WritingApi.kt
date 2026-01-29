package com.sorenkai.web.api

import com.sorenkai.web.api.dto.writings.WritingDetailResponse
import com.sorenkai.web.api.dto.writings.WritingListResponse
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

    suspend fun getWriting(id: String): ApiResponse<WritingDetailResponse> =
        apiClient.get("/v2/writings/$id") {
            json.decodeFromString<WritingDetailResponse>(it)
        }

    suspend fun resolveSlug(slug: String): ApiResponse<WritingDetailResponse> =
        apiClient.get("/v2/writings?slug=$slug") {
            json.decodeFromString<WritingDetailResponse>(it)
        }

    suspend fun incrementShare(id: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$id/share") { Unit }

    suspend fun like(id: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$id/like") { Unit }

    suspend fun unlike(id: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$id/unlike") { Unit }

    suspend fun incrementView(id: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$id/view") { Unit }

    suspend fun incrementSalesClick(id: String): ApiResponse<Unit> =
        apiClient.post("/v2/writings/$id/click-sale") { Unit }
}
