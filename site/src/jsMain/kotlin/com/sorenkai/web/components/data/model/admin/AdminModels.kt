package com.sorenkai.web.components.data.model.admin

import kotlinx.serialization.Serializable

@Serializable
data class DashboardSummary(
    val reportsOpen: Int,
    val reportsInReview: Int,
    val reportsResolved: Int,
    val discussionsHiddenOrRemoved: Int,
    val writingsSubmitted: Int,
    val writingsPublished: Int
)

@Serializable
data class HealthOverview(
    val ok: Boolean
)

@Serializable
data class AnalyticsStub(
    val message: String = "Analytics placeholder"
)

@Serializable
data class AdminOk(
    val ok: Boolean = true
)
