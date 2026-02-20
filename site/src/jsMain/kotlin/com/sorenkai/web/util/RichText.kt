package com.sorenkai.web.util

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.silk.components.text.SpanText

@Composable
fun RichText(content: String, modifier: Modifier = Modifier) {
    val parts = content.split(Regex("(?<=[*_])|(?=[*_])"))
    var italic = false
    var emphasis = false
    parts.forEach { part ->
        when {
            part == "*" -> {
                emphasis = !emphasis
            }

            emphasis -> {
                SpanText(
                    part,
                    modifier = Modifier
                        .fontWeight(600)
                        .then(modifier)
                )
            }

            part == "_" -> {
                italic = !italic
            }

            italic -> {
                SpanText(
                    part,
                    modifier = Modifier
                        .fontStyle(FontStyle.Italic)
                        .fontWeight(600)
                        .then(modifier)
                )
            }

            else -> {
                SpanText(part)
            }
        }
    }
}
