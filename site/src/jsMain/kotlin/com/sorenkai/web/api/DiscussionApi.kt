package com.sorenkai.web.api

import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.DiscussionDto
import com.sorenkai.web.api.dto.discussions.DiscussionModerationDto
import com.sorenkai.web.api.dto.discussions.DiscussionReportDto
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.report.Report
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class DiscussionApi(private val apiClient: ApiClient) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getDiscussions(
        lang: String,
        writingId: String? = null,
        parentId: String? = null,
        pageSize: Int? = null,
        cursor: String? = null,
        order: DiscussionOrder? = null
    ): ApiResponse<List<DiscussionDto>> {
        val params = mutableListOf<String>()
        params.add("lang=$lang")
        writingId?.let { params.add("writingId=$it") }
        parentId?.let { params.add("parentId=$it") }
        pageSize?.let { params.add("pageSize=$it") }
        cursor?.let { params.add("cursor=$it") }
        params.add("order=${order ?: DiscussionOrder.NEWEST}")

        val query = if (params.isNotEmpty()) "?" + params.joinToString("&") else ""
        return apiClient.get("/v2/community/discussions$query") {
            // The backend returns a Page object: data class Page(val items: List<Discussion>, val nextCursor: String?)
            // But current DiscussionApi.getDiscussions returns ApiResponse<List<Discussion>>.
            // I should check if I need to update the DTO or just extract items for now.
            // Based on the provided backend code: call.respond(body) where body is Page.
            // So it returns { "items": [...], "nextCursor": "..." }
            val jsonElement = json.parseToJsonElement(it).jsonObject
            val itemsElement = jsonElement["items"] ?: throw Exception("Missing items in response")
            json.decodeFromJsonElement<List<DiscussionDto>>(itemsElement)
        }
    }

    suspend fun createDiscussion(dto: DiscussionCreateDto): ApiResponse<DiscussionDto> =
        apiClient.post("/v2/community/discussions", body = json.encodeToString(DiscussionCreateDto.serializer(), dto)) { json.decodeFromString(it) }

    suspend fun deleteDiscussion(id: String): ApiResponse<DiscussionDto> =
        apiClient.delete("/v2/community/discussions/$id") { json.decodeFromString(it) }

    suspend fun restoreDiscussion(id: String): ApiResponse<DiscussionDto> =
        apiClient.post("/v2/community/discussions/$id/restore") { json.decodeFromString(it) }

    suspend fun reportDiscussion(dto: DiscussionReportDto): ApiResponse<Report> =
        apiClient.post("/v2/reports", body = json.encodeToString(DiscussionReportDto.serializer(), dto)) { json.decodeFromString(it) }

    suspend fun moderateDiscussion(id: String, dto: DiscussionModerationDto): ApiResponse<DiscussionDto> =
        apiClient.post("/v2/admin/discussions/$id/status", body = dto) { json.decodeFromString(it) }

    suspend fun editDiscussion(id: String, body: String): ApiResponse<DiscussionDto> =
        apiClient.patch("/v2/community/discussions/$id", body = js("({ body: body })")) { json.decodeFromString(it) }

    suspend fun like(id: String): ApiResponse<DiscussionDto> =
        apiClient.post("/v2/community/discussions/$id/like") { json.decodeFromString(it) }

    suspend fun unlike(id: String): ApiResponse<DiscussionDto> =
        apiClient.delete("/v2/community/discussions/$id/like") { json.decodeFromString(it) }
}
