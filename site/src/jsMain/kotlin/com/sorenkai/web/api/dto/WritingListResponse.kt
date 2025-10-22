package com.sorenkai.web.api.dto

import com.sorenkai.web.components.data.model.WritingEntry
import kotlinx.serialization.Serializable

@Serializable
data class WritingListResponse(
    val items: List<WritingEntry>
)
