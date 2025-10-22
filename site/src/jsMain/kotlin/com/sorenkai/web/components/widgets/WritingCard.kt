package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.WritingCardStyle
import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.i18n.Strings
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCard(writing: WritingEntry,
    onReadArticle: (title: String, slug: String) -> Unit
) {
    val enabled = when (writing.type) {
        Type.BOOK -> writing.salesLink != null
        Type.ARTICLE -> writing.status == Status.PUBLISHED
    }

    Box(
        WritingCardStyle.toModifier()
    ) {
        Column {
            H3 { Text(writing.title) }
            P { Text(writing.synopsis) }

            Spacer()

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
                Text(writing.actionLabel())
            }
        }
    }
}

private fun WritingEntry.actionLabel(): String = when (this.type) {
    Type.BOOK -> if (salesLink != null) Strings.current.purchaseBook else Strings.current.comingSoon
    Type.ARTICLE -> when (status) {
        Status.PUBLISHED -> Strings.current.readArticle
        Status.SUBMITTED -> Strings.current.comingSoon
        else -> Strings.current.notAvailable
    }
}
