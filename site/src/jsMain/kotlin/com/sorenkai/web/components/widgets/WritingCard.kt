package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.WritingCardStyle
import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.components.util.formatIsoDateToHumanReadable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCard(
    writing: WritingEntry,
    onReadArticle: (title: String, slug: String) -> Unit,
    lang: String
) {
    val enabled = when (writing.type) {
        Type.BOOK -> writing.salesLink != null
        Type.ARTICLE -> writing.status == Status.PUBLISHED
    }
    val text = writingText[lang] ?: error("Language not supported: $lang")
    val title = writing.title
    val synopsis = writing.synopsis
    val updatedAt = formatIsoDateToHumanReadable(writing.updatedAt)

    Box(
        WritingCardStyle.toModifier()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.px)
        ) {
            H3 { Text(title) }
            P { Text(synopsis) }

            Spacer()
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("${text["lastPublished"]}: $updatedAt" )
                Button(
                    enabled = enabled,
                    onClick = {
                    if (writing.type == Type.ARTICLE) {
                        // NEW: Invoke the callback, passing the necessary data
                        onReadArticle(writing.title, writing.slug)
                    }
                    // FUTURE: Handle book click action here
                    }
                ) {
                    Text(
                        if (writing.type == Type.ARTICLE) {
                            if(writing.status == Status.PUBLISHED) text["readArticle"]!!
                            else text["comingSoon"]!!
                        } else text["buyBook"]!!
                    )
                }

            }
        }
    }
}

private val writingText = mapOf(
    "en" to mapOf(
        "lastPublished" to "Last Published:",
        "readArticle" to "Read Article",
        "comingSoon" to "Coming Soon",
        "buyBook" to "Buy Book"
    ),
    "es" to mapOf(
        "lastPublished" to "Última publicación:",
        "readArticle" to "Leer artículo",
        "comingSoon" to "Próximamente",
        "buyBook" to "Comprar libro"
    )
)
