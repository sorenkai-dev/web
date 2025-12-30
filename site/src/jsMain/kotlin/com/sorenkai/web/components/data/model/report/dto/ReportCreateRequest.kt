package com.sorenkai.web.components.data.model.report.dto

import com.sorenkai.web.components.data.model.report.TargetType
import kotlinx.serialization.Serializable

@Serializable
data class ReportCreateRequest(
    val targetType: TargetType,
    val targetId: String,
    val reason: String
)
