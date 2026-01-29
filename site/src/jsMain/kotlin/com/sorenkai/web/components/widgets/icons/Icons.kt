package com.sorenkai.web.components.widgets.icons

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.util.Res
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.dom.Span

@Composable
fun SubstackIcon(
    modifier: Modifier
) {
    // Render as a masked inline element so it inherits color from LinkStyle (via currentColor)
    val maskedModifier = modifier.display(DisplayStyle.InlineBlock)
    Span(
        attrs = maskedModifier.toAttrs {
            // Accessibility
            attr("role", "img")
            attr("aria-label", "Substack")
            attr("title", "Substack")

            // Apply mask styles without overwriting existing modifier styles (e.g., width/height from .size)
            style {
                property("background-color", "currentColor")
                property("-webkit-mask-image", "url(${Res.Icon.SUBSTACK})")
                property("mask-image", "url(${Res.Icon.SUBSTACK})")
                property("-webkit-mask-repeat", "no-repeat")
                property("mask-repeat", "no-repeat")
                property("-webkit-mask-position", "center")
                property("mask-position", "center")
                property("-webkit-mask-size", "contain")
                property("mask-size", "contain")
            }
        }
    )
}
