package com.sorenkai.web.api.dto.writings

import com.sorenkai.web.components.data.model.writing.WritingEntry
import kotlinx.serialization.Serializable

@Serializable
data class WritingListResponse(
    val items: List<WritingEntry>
)
