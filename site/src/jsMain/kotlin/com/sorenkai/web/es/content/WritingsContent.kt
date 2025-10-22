package com.sorenkai.web.es.content

import androidx.compose.runtime.*
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingListResponse
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.components.widgets.*
import com.sorenkai.web.en.widgets.ArticleModal
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingsHeroEsQuote() {
    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            content = {
                Text("“El hecho de contar historias insinúa una inquietud humana fundamental, una señal de " +
                    "imperfección humana. Donde hay perfección, no hay historia que contar.”")
            },
            citation = "— Ben Okri",
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
}

@Composable
fun WritingContentEs(breakpoint: Breakpoint, lang: String) {
    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalSlug by remember { mutableStateOf("") }

    val closeModal = {
        showModal = false
        modalTitle = ""
        modalSlug = ""
    }

    LeadParagraph(breakpoint) {
        Text(
            "Esta página es un portafolio de mis trabajos publicados y en desarrollo. Reúne ensayos, " +
                "reflexiones y contribuciones que exploran las intersecciones entre tecnología, cultura y " +
                "pertenencia. Algunas piezas son profundamente técnicas, otras más reflexivas, pero todas comparten " +
                "la misma pregunta: ¿qué significa seguir siendo plenamente humanos en un mundo moldeado por las " +
                "máquinas? Cada entrada incluye una breve sinopsis y un enlace para leer o adquirir el trabajo completo."
        )
    }

    var isLoading by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<ApiResponse<List<WritingEntry>>?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        val json = Json { ignoreUnknownKeys = true }
        result = ApiClient.safeApiGet("/v1/writings") { responseText ->
            json.decodeFromString<WritingListResponse>(responseText).items
        }
        isLoading = false
    }

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 24.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                P { Text("Cargando escritos...") }
                HorizontalProgressBar()
            }
        }
        else -> when (val res = result) {
            is ApiResponse.Success -> Column(
                Modifier.fillMaxWidth().gap(24.px)
            ) {
                val sortedWritings = res.data.filter { it.language=="es" }.sortedByDescending { it.updatedAt }
                sortedWritings.forEach { writing ->
                    WritingCard(
                        writing = writing,
                        onReadArticle = { title, slug ->
                            modalTitle = title
                            modalSlug = slug
                            showModal = true
                        },
                        lang = lang
                    )
                }
            }

            is ApiResponse.HttpError -> P { Text("Error del servidor ${res.code}") }
            is ApiResponse.NetworkError -> P { Text("Problema de red, por favor intente de nuevo.") }
            is ApiResponse.UnknownError -> P { Text("Ocurrió un error inesperado.") }
            null -> P { Text("Cargando escritos...") }
        }
    }

    if (showModal && modalSlug.isNotEmpty()) {
        ModalOverlay(
            title = modalTitle,
            onClose = closeModal
        ) {
            ArticleModal(slug = modalSlug, lang)
        }
    }
}
