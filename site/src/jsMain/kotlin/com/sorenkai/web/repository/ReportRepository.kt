package com.sorenkai.web.repository

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.ReportApi
import com.sorenkai.web.api.dto.reports.ReportCreateRequest
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.repository.interfaces.IReportRepository

class ReportRepository(
    private val api: ReportApi,
    private val auth: IAuthProvider
) : IReportRepository {
    override suspend fun createReport(request: ReportCreateRequest): Report {
        val userId = auth.getUserId() ?: throw IllegalStateException("Must be authenticated")

        // Basic validation as requested: "Repository must enforce authentication and validation before calling the API."
        if (request.targetId.isBlank()) {
            throw IllegalArgumentException("Target ID cannot be blank")
        }
        if (request.reason.isBlank()) {
            throw IllegalArgumentException("Reason cannot be blank")
        }

        val response = api.createReport(request)
        return when (response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.HttpError -> throw RuntimeException("Failed to create report: ${response.message}")
            is ApiResponse.NetworkError -> throw RuntimeException("Network error: ${response.message}")
            is ApiResponse.UnknownError -> throw RuntimeException("Unknown error: ${response.message}")
        }
    }
}
