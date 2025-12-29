package com.sorenkai.web.components.data.model.community.discussions.dto

import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDeleteDto(
    val discussionId: String
)
