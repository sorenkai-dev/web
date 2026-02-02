package com.sorenkai.web.api.dto.discussions

import kotlinx.serialization.Serializable

@Serializable
data class DiscussionReportDto(
    val targetType: String,
    val targetId: String,
    val reason: String
)
