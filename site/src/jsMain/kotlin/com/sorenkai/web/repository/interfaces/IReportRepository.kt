package com.sorenkai.web.repository.interfaces

import com.sorenkai.web.api.dto.reports.ReportCreateRequest
import com.sorenkai.web.components.data.model.report.Report

interface IReportRepository {
    suspend fun createReport(request: ReportCreateRequest): Report
}
