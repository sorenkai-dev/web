package com.sorenkai.web.components.widgets


import androidx.compose.runtime.*
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingDetail
import com.sorenkai.web.util.renderMarkdown
import com.varabyte.kobweb.compose.foundation.layout.Column
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun ArticleModal(
    slug: String
) {
    var article by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(slug) {
        try {
            isLoading = true
            val json = Json { ignoreUnknownKeys = true }
            val result = ApiClient.safeApiGet("/v1/writings/$slug"){
                json.decodeFromString<WritingDetail>(it)
            }

            article = when (result) {is ApiResponse.Success -> {
                    // Success! Extract the content field from the deserialized DTO.
                    result.data.content
                }
                is ApiResponse.HttpError -> {
                    "Failed to load article: HTTP ${result.code}. ${result.message}"
                }
                is ApiResponse.NetworkError -> {
                    "Network issue, please check your connection."
                }
                is ApiResponse.UnknownError -> {
                    "An unknown error occurred: ${result.message}"
                }
            }
        } catch (t: Throwable) {
            console.error("Failed to load article $slug", t)
            article = "Unable to load article. Please try again later."
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        P { Text("Loading article...") }
    } else {
        val content = article ?: ""
        Column {
            renderMarkdown(content)
        }
    }
}
