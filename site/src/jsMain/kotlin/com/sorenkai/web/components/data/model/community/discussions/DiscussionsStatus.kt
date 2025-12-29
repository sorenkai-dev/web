package com.sorenkai.web.components.data.model.community.discussions

import kotlinx.serialization.Serializable

@Serializable
enum class DiscussionsStatus {
    VISIBLE,
    HIDDEN_BY_REPORTS,
    HIDDEN_BY_MODERATOR,
    DELETED_BY_AUTHOR,
    DELETED_BY_MODERATOR
}
