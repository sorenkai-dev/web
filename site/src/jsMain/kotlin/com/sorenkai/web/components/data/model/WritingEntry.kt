package com.sorenkai.web.components.data.model

data class WritingEntry(
    val title: String,
    val publication: String,
    val date: String,
    val synopsis: String,
    val link: String? = null,      // optional, some may not be live yet
    val purchase: Boolean = false  // differentiate purchase vs read links
)
