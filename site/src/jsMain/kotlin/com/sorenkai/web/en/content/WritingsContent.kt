package com.sorenkai.web.en.content

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
fun WritingsHeroEnQuote() {
    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            content = {
                Text("“The fact of storytelling hints at a fundamental human unease, hints at human imperfection. Where there is perfection there is no story to tell.”")
            },
            citation = "— Ben Okri",
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
}

@Composable
fun WritingContentEn(breakpoint: Breakpoint, lang: String) {
    // 1. New State Variables for Modal Management
    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalSlug by remember { mutableStateOf("") }

    // Helper function to close the modal
    val closeModal = {
        showModal = false
        modalTitle = ""
        modalSlug = ""
    }

    LeadParagraph(breakpoint) {
        Text(
            "This page is a portfolio of my published and ongoing work. It brings together essays, reflections, " +
                "and contributions that explore the intersections of technology, culture, and belonging. Some pieces " +
                "are deeply technical, others more reflective, but all are united by the same question: what does it " +
                "mean to remain fully human in a world shaped by machines? Each entry includes a short synopsis and a " +
                "link to read or purchase the full work."
        )
    }

    var isLoading by remember { mutableStateOf(true)}
    var result by remember { mutableStateOf<ApiResponse<List<WritingEntry>>?>(null)}

    LaunchedEffect(Unit) {
        isLoading = true
        val json = Json { ignoreUnknownKeys = true }
        result = ApiClient.safeApiGet("/v1/writings") { responseText ->
            // Diagnostic logs are good, but omitting for brevity in the final code
            json.decodeFromString<WritingListResponse>(responseText).items
        }
        isLoading = false
    }

    when {
        isLoading -> {
            Column(
                // Fill width and add padding/margin as necessary
                modifier = Modifier.fillMaxWidth().padding(top = 24.px),
                // Optional: Align the text/bar to the center or start
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Loading Text
                P {
                    Text("Loading writings...")
                }

                // 2. Linear Progress Bar
                HorizontalProgressBar()
            }
        }
        else -> when (val res = result) {
            is ApiResponse.Success -> Column(
                Modifier.fillMaxWidth().gap(24.px)
            ){
                val sortedWritings = res.data.filter { it.language == "en" }.sortedByDescending { it.updatedAt }
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

            is ApiResponse.HttpError -> P { Text("Server error ${res.code}") }
            is ApiResponse.NetworkError -> P { Text("Network issue, please retry.") }
            is ApiResponse.UnknownError -> P { Text("Unexpected error occurred.") }
            null -> P { Text("Loading writings...") }
        }
    }

    // 3. Conditional Modal Rendering
    if (showModal && modalSlug.isNotEmpty()) {
        ModalOverlay(
            title = modalTitle,
            onClose = closeModal // Use the helper function here
        ) {
            // ArticleModal now loads and renders the content inside the overlay
            ArticleModal(slug = modalSlug, lang)
        }
    }
}
