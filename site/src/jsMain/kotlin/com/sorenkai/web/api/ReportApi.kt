package com.sorenkai.web.api

import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.components.data.model.report.dto.ReportCreateRequest
import kotlinx.serialization.json.Json

class ReportApi(private val apiClient: ApiClient) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun createReport(request: ReportCreateRequest): ApiResponse<Report> =
        apiClient.post("/v2/reports", body = request) { json.decodeFromString(it) }

    suspend fun getReports(): ApiResponse<List<Report>> =
        apiClient.get("/v2/admin/reports") { json.decodeFromString(it) }

    suspend fun resolveReport(id: String, action: String): ApiResponse<Boolean> {
        val body = js("({})")
        body.action = action
        return apiClient.patch("/v2/admin/reports/$id/resolve", body = body) { true }
    }
}
