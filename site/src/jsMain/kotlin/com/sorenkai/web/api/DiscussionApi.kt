package com.sorenkai.web.api

import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionCreateDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionDeleteDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReportDto
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class DiscussionApi(private val apiClient: ApiClient) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getDiscussions(
        writingSlug: String? = null,
        parentId: String? = null,
        pageSize: Int? = null,
        cursor: String? = null
    ): ApiResponse<List<Discussion>> {
        val params = mutableListOf<String>()
        writingSlug?.let { params.add("writingSlug=$it") }
        parentId?.let { params.add("parentId=$it") }
        pageSize?.let { params.add("pageSize=$it") }
        cursor?.let { params.add("cursor=$it") }

        val query = if (params.isNotEmpty()) "?" + params.joinToString("&") else ""
        return apiClient.get("/v2/community/discussions$query") {
            // The backend returns a Page object: data class Page(val items: List<Discussion>, val nextCursor: String?)
            // But current DiscussionApi.getDiscussions returns ApiResponse<List<Discussion>>.
            // I should check if I need to update the DTO or just extract items for now.
            // Based on the provided backend code: call.respond(body) where body is Page.
            // So it returns { "items": [...], "nextCursor": "..." }
            val jsonElement = json.parseToJsonElement(it).jsonObject
            val itemsElement = jsonElement["items"] ?: throw Exception("Missing items in response")
            json.decodeFromJsonElement<List<Discussion>>(itemsElement)
        }
    }

    suspend fun createDiscussion(dto: DiscussionCreateDto): ApiResponse<Discussion> =
        apiClient.post("/v2/community/discussions", body = dto) { json.decodeFromString(it) }

    suspend fun deleteDiscussion(id: String): ApiResponse<Boolean> =
        apiClient.delete("/v2/community/discussions/$id") {
            val jsonElement = json.parseToJsonElement(it).jsonObject
            jsonElement["ok"]?.jsonPrimitive?.booleanOrNull ?: false
        }

    suspend fun reportDiscussion(id: String, dto: DiscussionReportDto): ApiResponse<Discussion> =
        apiClient.post("/v2/community/discussions/$id/report", body = dto) { json.decodeFromString(it) }

    suspend fun moderateDiscussion(id: String, dto: DiscussionModerationDto): ApiResponse<Discussion> =
        apiClient.post("/v2/community/discussions/$id/moderate", body = dto) { json.decodeFromString(it) }

    suspend fun editDiscussion(id: String, body: String): ApiResponse<Boolean> =
        apiClient.patch("/v2/community/discussions/$id", body = js("({ body: body })")) {
            val jsonElement = json.parseToJsonElement(it).jsonObject
            jsonElement["ok"]?.jsonPrimitive?.booleanOrNull ?: false
        }

    suspend fun like(id: String): ApiResponse<Discussion> =
        apiClient.post("/v2/community/discussions/$id/like") { json.decodeFromString(it) }

    suspend fun unlike(id: String): ApiResponse<Discussion> =
        apiClient.delete("/v2/community/discussions/$id/like") { json.decodeFromString(it) }
}
