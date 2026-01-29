package com.sorenkai.web.components.data.model.report

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class Report(
    val id: String,
    val targetType: TargetType,
    val targetId: String,
    val reason: String,
    val createdAt: Instant,
    val createdBy: String,
    val status: ReportStatus = ReportStatus.OPEN,
)
