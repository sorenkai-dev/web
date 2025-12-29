package com.sorenkai.web.components.data.model.community.discussions.dto

import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionModerationDto(
    val discussionId: String,
    val newStatus: DiscussionsStatus,
    val reason: String? = null
)
