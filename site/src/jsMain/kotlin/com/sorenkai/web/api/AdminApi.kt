package com.sorenkai.web.api

import com.sorenkai.web.components.data.model.admin.AdminOk
import com.sorenkai.web.components.data.model.admin.AnalyticsStub
import com.sorenkai.web.components.data.model.admin.DashboardSummary
import com.sorenkai.web.components.data.model.admin.HealthOverview
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import kotlinx.serialization.json.Json

class AdminApi(private val apiClient: ApiClient) {
    private val json = Json { ignoreUnknownKeys = true }

    // Dashboard & System
    suspend fun getDashboardSummary(): ApiResponse<DashboardSummary> =
        apiClient.get("/v2/admin/dashboard") { json.decodeFromString(it) }

    suspend fun getHealth(): ApiResponse<HealthOverview> =
        apiClient.get("/v2/admin/health") { json.decodeFromString(it) }

    suspend fun getAnalytics(): ApiResponse<AnalyticsStub> =
        apiClient.get("/v2/admin/analytics") { json.decodeFromString(it) }

    // Discussions
    suspend fun setDiscussionStatus(id: String, status: DiscussionsStatus, reason: String?): ApiResponse<Boolean> {
        val body = js("({})")
        body.status = status.name
        if (reason != null) body.reason = reason
        return apiClient.patch("/v2/admin/discussions/$id/status", body = body) {
            // Backend responds with mapOf("ok" to true)
            true
        }
    }

    suspend fun setDiscussionPinned(id: String, pinned: Boolean): ApiResponse<Boolean> {
        val body = js("({})")
        body.value = pinned
        return apiClient.patch("/v2/admin/discussions/$id/pin", body = body) { true }
    }

    suspend fun setDiscussionLocked(id: String, locked: Boolean): ApiResponse<Boolean> {
        val body = js("({})")
        body.value = locked
        return apiClient.patch("/v2/admin/discussions/$id/lock", body = body) { true }
    }

    // Writings
    suspend fun createWriting(writing: WritingEntry): ApiResponse<AdminOk> =
        apiClient.post("/v2/admin/writings", body = writing) { json.decodeFromString(it) }

    suspend fun setWritingStatus(id: String, status: WritingsStatus): ApiResponse<AdminOk> {
        val body = js("({})")
        body.status = status.name
        return apiClient.patch("/v2/admin/writings/$id/status", body = body) { json.decodeFromString(it) }
    }

    suspend fun setWritingFeatured(id: String, featured: Boolean): ApiResponse<AdminOk> {
        val body = js("({})")
        body.featured = featured
        return apiClient.patch("/v2/admin/writings/$id/featured", body = body) { json.decodeFromString(it) }
    }

    suspend fun deleteWriting(id: String): ApiResponse<AdminOk> =
        apiClient.delete("/v2/admin/writings/$id") { json.decodeFromString(it) }
}
