package com.sorenkai.web.api.dto.reports

import com.sorenkai.web.components.data.model.report.TargetType
import kotlinx.serialization.Serializable

@Serializable
data class ReportCreateRequest(
    val targetType: TargetType,
    val targetId: String,
    val reason: String
)
