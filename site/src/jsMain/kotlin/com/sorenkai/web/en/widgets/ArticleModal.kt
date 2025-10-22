package com.sorenkai.web.en.widgets


import androidx.compose.runtime.*
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingDetail
import com.sorenkai.web.components.widgets.HorizontalProgressBar
import com.sorenkai.web.util.renderMarkdown
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun ArticleModal(
    slug: String,
    lang: String
) {
    val text = articleText[lang] ?: articleText["en"]!!
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
                is ApiResponse.HttpError ->
                    "${text["httpErrorPrefix"]} HTTP ${result.code}. ${result.message}"
                is ApiResponse.NetworkError -> {
                    text["networkError"]
                }
                is ApiResponse.UnknownError -> {
                    "${text["unknownError"]} ${result.message}"
                }
            }
        } catch (t: Throwable) {
            console.error("${text["httpError"]} $slug", t)
            article = text["fatalError"]
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
            Column(
                // Fill width and add padding/margin as necessary
                modifier = Modifier.fillMaxWidth().padding(top = 24.px),
                // Optional: Align the text/bar to the center or start
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Loading Text
                P {
                    Text(text["loading"]!!)
                }

                // 2. Linear Progress Bar
                HorizontalProgressBar()
            }
    } else {
        val content = article ?: ""
        Div(
            attrs = Modifier
                .onClick { event ->
                    // Stop the click event from bubbling up to the modal's backdrop/overlay.
                    event.stopPropagation()
                }
                .toAttrs()
        ) {
            // Your existing content structure
            Column {
                renderMarkdown(content)
            }
        }
    }
}

private val articleText = mapOf(
    "en" to mapOf(
        "loading" to "Loading article...",
        "httpError" to "Failed to load article: ",
        "networkError" to "Network issue, please check your connection.",
        "unknownError" to "An unknown error occurred: %s",
        "fatalError" to "Unable to load article. Please try again later."
    ),
    "es" to mapOf(
        "loading" to "Cargando artículo...",
        "httpError" to "Error al cargar el artículo: HTTP %s. %s",
        "networkError" to "Problema de red, por favor verifique su conexión.",
        "unknownError" to "Ocurrió un error desconocido: %s",
        "fatalError" to "No se pudo cargar el artículo. Inténtelo nuevamente más tarde."
    )
)
