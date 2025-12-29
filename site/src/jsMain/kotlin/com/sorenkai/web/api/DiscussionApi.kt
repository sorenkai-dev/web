package com.sorenkai.web.api

import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionCreateDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionDeleteDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionModerationDto
import com.sorenkai.web.components.data.model.community.discussions.dto.DiscussionReportDto
import kotlinx.serialization.json.Json

class DiscussionApi {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getDiscussions(): ApiResponse<List<Discussion>> =
        ApiClient.get("/v2/community/discussions") { json.decodeFromString(it) }

    suspend fun createDiscussion(dto: DiscussionCreateDto): ApiResponse<Discussion> =
        ApiClient.post("/v2/community/discussions", body = dto) { json.decodeFromString(it) }

    suspend fun deleteDiscussion(id: String, dto: DiscussionDeleteDto): ApiResponse<Discussion> =
        ApiClient.post("/v2/community/discussions/$id/delete", body = dto) { json.decodeFromString(it) }

    suspend fun reportDiscussion(id: String, dto: DiscussionReportDto): ApiResponse<Discussion> =
        ApiClient.post("/v2/community/discussions/$id/report", body = dto) { json.decodeFromString(it) }

    suspend fun moderateDiscussion(id: String, dto: DiscussionModerationDto): ApiResponse<Discussion> =
        ApiClient.post("/v2/community/discussions/$id/moderate", body = dto) { json.decodeFromString(it) }

    suspend fun editDiscussion(id: String, body: String): ApiResponse<Discussion> =
        ApiClient.put("/v2/community/discussions/$id", body = js("({ body: body })")) { json.decodeFromString(it) }
}
