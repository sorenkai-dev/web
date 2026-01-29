package com.sorenkai.web.components.widgets.menus

import androidx.compose.runtime.Composable
import com.sorenkai.web.LinkStyle
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationTimingFunction.Companion.EaseInOut
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Transition
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun NavLink(
    path: String,
    text: String,
    isTrigger: Boolean = false,
    modifier: Modifier = LinkStyle.toModifier(),
    onClick: (() -> Unit)? = null,
    expanded: Boolean? = null,
) {
    if (isTrigger) {
        val triggerModifier = modifier
            .attr("role", "button")
            .attr("tabindex", "0")
            .attr("aria-haspopup", "true")
            .let { if (expanded != null) it.attr("aria-expanded", expanded.toString()) else it }
            .onClick { onClick?.invoke() }
            .onKeyDown {
                when (it.key) {
                    "Enter", " " -> { it.preventDefault(); onClick?.invoke() }
                    "Escape" -> { it.preventDefault(); onClick?.invoke() }
                }
            }
        Span(attrs = triggerModifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.35.cssRem)
            .toAttrs()
        ) {
            Text(text)

            // ▼ Animated caret
            Svg(
                attrs = Modifier
                    .size(0.8.em)
                    .transition { Transition( "transform", 0.2.s, EaseInOut) }
                    .transform { rotate(if (expanded == true) 180.deg else 0.deg) }
                    .styleModifier {
                        property("viewBox", "0 0 30 30")
                    }
                    .toAttrs()
            ) {
                Path {
                    attr("d", "M2 7 L6 15 L10 7 Z")
                    attr("stroke", "none")
                    attr("fill", "currentColor")
                    attr("stroke-width", "2")
                    attr("stroke-linecap", "miter")
                    attr("stroke-linejoin", "miter")
                }
            }
        }
    } else {
        // For regular links, honor the provided modifier and allow an optional onClick
        // so parent menus (e.g., side menu) can close themselves on navigation.
        val linkModifier = modifier.onClick { onClick?.invoke() }
        Link(path, text, modifier = linkModifier)
    }
}
