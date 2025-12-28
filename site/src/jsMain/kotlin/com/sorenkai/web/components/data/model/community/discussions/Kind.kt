package com.sorenkai.web.components.data.model.community.discussions

import kotlinx.serialization.Serializable

@Serializable
enum class Kind {
    POST,
    COMMENT
}
