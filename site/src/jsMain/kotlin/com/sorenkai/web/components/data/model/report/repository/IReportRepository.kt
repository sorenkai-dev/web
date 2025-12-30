package com.sorenkai.web.components.data.model.report.repository

import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.components.data.model.report.dto.ReportCreateRequest

interface IReportRepository {
    suspend fun createReport(request: ReportCreateRequest): Report
}
