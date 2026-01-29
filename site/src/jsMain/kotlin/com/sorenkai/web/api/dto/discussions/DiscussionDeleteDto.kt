package com.sorenkai.web.api.dto.discussions

import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDeleteDto(
    val discussionId: String
)
