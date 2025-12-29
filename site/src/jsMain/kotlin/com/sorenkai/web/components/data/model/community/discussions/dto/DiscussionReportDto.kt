package com.sorenkai.web.components.data.model.community.discussions.dto

import kotlinx.serialization.Serializable

@Serializable
data class DiscussionReportDto(
    val discussionId: String,
    val reason: String
)
