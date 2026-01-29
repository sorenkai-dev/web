package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteStyle
import com.sorenkai.web.CitationStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.TagElement
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLQuoteElement

@Composable
fun BlockQuote(
    content: @Composable () -> Unit,
    citation: String? = null,
    modifier: Modifier = Modifier
) {
    TagElement<HTMLQuoteElement>(
        tagName = "blockquote",
        applyAttrs = BlockquoteStyle.toModifier().then(modifier).toAttrs()
    ) {
        content()
        citation?.let {
            Span(
                attrs = CitationStyle.toModifier().toAttrs()
            ) {
                Text(citation)
            }
        }
    }
}
