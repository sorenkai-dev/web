package com.sorenkai.web.api.dto.reports

import kotlinx.serialization.Serializable

@Serializable
data class ReportCreateRequest(
    val targetType: String,
    val targetId: String,
    val reason: String
)
