package com.sorenkai.web.api.dto.discussions

import kotlinx.serialization.Serializable

@Serializable
data class DiscussionReportDto(
    val discussionId: String,
    val reason: String
)
