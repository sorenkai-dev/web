package com.sorenkai.web.components.data.model.report

import kotlinx.serialization.Serializable

@Serializable
enum class ReportStatus {
    OPEN,
    IN_REVIEW,
    RESOLVED,
}
