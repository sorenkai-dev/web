package com.sorenkai.web.components.data.model.report

import kotlinx.serialization.Serializable

@Serializable
enum class TargetType {
    WRITING,
    POST,
    COMMENT,
}
