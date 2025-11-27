package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.sorenkai.web.ButtonStyle
import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import com.sorenkai.web.components.data.model.WritingEntry
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingActionButton(
    writing: WritingEntry,
    onReadArticle: (title: String, slug: String) -> Unit,
    onViewToggle: (WritingEntry) -> Unit,
    onSalesClick: (WritingEntry) -> Unit,
    views: MutableState<Int>,
    lang: String
) {
    val enabled = when (writing.type) {
        Type.BOOK -> writing.salesLink != null
        Type.ARTICLE -> writing.status == Status.PUBLISHED
    }
    val text = texts[lang] ?: error("Language not supported: $lang")

    Button(
        modifier =
            ButtonStyle.toModifier()
                .color(Colors.White),
        enabled = enabled,
        onClick = {
            when (writing.type) {
                Type.ARTICLE -> {
                    onReadArticle(writing.title, writing.slug)
                    onViewToggle(writing)
                    views.value += 1
                }
                Type.BOOK -> {
                    writing.salesLink?.let { url ->
                        window.open(url, "_blank")
                        onSalesClick(writing)
                    }
                }
            }
        }
    ) {
        Text(
            if (writing.type == Type.ARTICLE) {
                if (writing.status == Status.PUBLISHED) {
                    text["readArticle"]!!
                } else {
                    text["comingSoon"]!!
                }
            } else {
                if (writing.salesLink.isNullOrBlank()) {
                    text["comingSoon"]!!
                } else {
                    text["buyBook"]!!
                }
            }
        )
    }
}

private val texts = mapOf(
    "en" to mapOf(
        "readArticle" to "Read Article",
        "comingSoon" to "Coming Soon",
        "buyBook" to "Buy Book",
    ),
    "es" to mapOf(
        "readArticle" to "Leer artículo",
        "comingSoon" to "Próximamente",
        "buyBook" to "Comprar libro",
    )
)
