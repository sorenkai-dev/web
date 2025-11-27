package com.sorenkai.web.en.widgets


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.SpinnerStyle
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingDetailResponse
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.util.renderMarkdown
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
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
            val result = ApiClient.safeApiGet("/v1/writings/$slug") {
                json.decodeFromString<WritingDetailResponse>(it)
            }

            article = when (result) { is ApiResponse.Success -> {
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
            modifier = Modifier.fillMaxWidth().padding(top= 1.cssRem, bottom = 2.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) { Text(text["loading"]!!) }
            Image(
                src = Res.Img.LOGO,
                modifier = SpinnerStyle.toModifier()
            )
        }
    } else {
        val content = article ?: ""
        Div(
            attrs = Modifier
                .onClick { event ->
                    event.stopPropagation()
                }
                .toAttrs()
        ) {
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
