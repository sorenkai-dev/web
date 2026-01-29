package com.sorenkai.web.api.dto.discussions

import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.Kind
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.serialization.Serializable

@OptIn(ExperimentalTime::class)
@Serializable
data class DiscussionCreateDto(
    val kind: Kind,
    val body: String,
    val lang: String,
    val userId: String,
    val status: DiscussionsStatus = DiscussionsStatus.VISIBLE,
    val createdAt: Instant = Instant.fromEpochMilliseconds(js("Date.now()").unsafeCast<Double>().toLong()),
    val writingId: String? = null,
    val parentId: String? = null
)
