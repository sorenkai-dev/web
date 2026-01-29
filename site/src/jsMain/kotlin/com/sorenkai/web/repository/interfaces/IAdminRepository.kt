package com.sorenkai.web.repository.interfaces

import com.sorenkai.web.components.data.model.admin.AdminOk
import com.sorenkai.web.components.data.model.admin.AnalyticsStub
import com.sorenkai.web.components.data.model.admin.DashboardSummary
import com.sorenkai.web.components.data.model.admin.HealthOverview
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.data.model.writing.WritingsStatus

interface IAdminRepository {
    // Dashboard & System
    suspend fun getDashboardSummary(): DashboardSummary

    suspend fun getHealth(): HealthOverview

    suspend fun getAnalytics(): AnalyticsStub

    // Discussions
    suspend fun moderateDiscussion(id: String, status: DiscussionsStatus, reason: String?): Boolean

    suspend fun setPinned(id: String, pinned: Boolean): Boolean

    suspend fun setLocked(id: String, locked: Boolean): Boolean

    // Writings
    suspend fun createWriting(writing: WritingEntry): AdminOk

    suspend fun setWritingStatus(id: String, status: WritingsStatus): AdminOk

    suspend fun setWritingFeatured(id: String, featured: Boolean): AdminOk

    suspend fun deleteWriting(id: String): AdminOk

    // Reports
    suspend fun getReports(): List<Report>

    suspend fun resolveReport(id: String, action: String): Boolean
}
