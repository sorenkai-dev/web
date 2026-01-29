package com.sorenkai.web.repository

import com.sorenkai.web.api.AdminApi
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.ReportApi
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.admin.AdminOk
import com.sorenkai.web.components.data.model.admin.AnalyticsStub
import com.sorenkai.web.components.data.model.admin.DashboardSummary
import com.sorenkai.web.components.data.model.admin.HealthOverview
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import com.sorenkai.web.repository.interfaces.IAdminRepository

class AdminRepository(
    private val adminApi: AdminApi,
    private val reportApi: ReportApi,
    private val auth: IAuthProvider
) : IAdminRepository {
    private fun checkAdmin() {
        if (auth.authState != AuthState.Authenticated) {
            throw IllegalStateException("Must be authenticated")
        }
        // Placeholder for role check
        // In the future: if (!auth.hasRole("admin")) throw IllegalStateException("Not authorized")
    }

    private fun <T> handleResponse(res: ApiResponse<T>, errorMsg: String): T {
        return when (res) {
            is ApiResponse.Success -> res.data
            is ApiResponse.HttpError -> throw RuntimeException("$errorMsg: ${res.message} (HTTP ${res.code})")
            is ApiResponse.NetworkError -> throw RuntimeException("$errorMsg: Network error")
            is ApiResponse.UnknownError -> throw RuntimeException("$errorMsg: ${res.message}")
        }
    }

    override suspend fun getDashboardSummary(): DashboardSummary {
        checkAdmin()
        return handleResponse(adminApi.getDashboardSummary(), "Failed to fetch dashboard summary")
    }

    override suspend fun getHealth(): HealthOverview {
        checkAdmin()
        return handleResponse(adminApi.getHealth(), "Failed to fetch health status")
    }

    override suspend fun getAnalytics(): AnalyticsStub {
        checkAdmin()
        return handleResponse(adminApi.getAnalytics(), "Failed to fetch analytics")
    }

    override suspend fun moderateDiscussion(id: String, status: DiscussionsStatus, reason: String?): Boolean {
        checkAdmin()
        return handleResponse(adminApi.setDiscussionStatus(id, status, reason), "Failed to moderate discussion")
    }

    override suspend fun setPinned(id: String, pinned: Boolean): Boolean {
        checkAdmin()
        return handleResponse(adminApi.setDiscussionPinned(id, pinned), "Failed to set pinned status")
    }

    override suspend fun setLocked(id: String, locked: Boolean): Boolean {
        checkAdmin()
        return handleResponse(adminApi.setDiscussionLocked(id, locked), "Failed to set locked status")
    }

    override suspend fun createWriting(writing: WritingEntry): AdminOk {
        checkAdmin()
        return handleResponse(adminApi.createWriting(writing), "Failed to create writing")
    }

    override suspend fun setWritingStatus(id: String, status: WritingsStatus): AdminOk {
        checkAdmin()
        return handleResponse(adminApi.setWritingStatus(id, status), "Failed to set writing status")
    }

    override suspend fun setWritingFeatured(id: String, featured: Boolean): AdminOk {
        checkAdmin()
        return handleResponse(adminApi.setWritingFeatured(id, featured), "Failed to set featured status")
    }

    override suspend fun deleteWriting(id: String): AdminOk {
        checkAdmin()
        return handleResponse(adminApi.deleteWriting(id), "Failed to delete writing")
    }

    override suspend fun getReports(): List<Report> {
        checkAdmin()
        return handleResponse(reportApi.getReports(), "Failed to fetch reports")
    }

    override suspend fun resolveReport(id: String, action: String): Boolean {
        checkAdmin()
        return handleResponse(reportApi.resolveReport(id, action), "Failed to resolve report")
    }
}
