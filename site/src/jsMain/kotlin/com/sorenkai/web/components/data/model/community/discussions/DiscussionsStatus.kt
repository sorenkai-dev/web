package com.sorenkai.web.components.data.model.community.discussions

import kotlinx.serialization.Serializable

@Serializable
enum class DiscussionsStatus {
    VISIBLE, // normal
    HIDDEN, // hidden from public, visible to admin
    REMOVED, // removed content placeholder
}
