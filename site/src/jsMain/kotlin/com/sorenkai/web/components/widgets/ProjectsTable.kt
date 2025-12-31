package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

/**
 * A responsive "soft table" that presents project name and description
 * side-by-side on desktop and stacked on mobile.
 */
@Composable
fun ProjectsTable(projects: List<Pair<String, String>>, modifier: Modifier = Modifier) {
    val breakpoint = LocalBreakpoint.current

    Div(
        attrs = Modifier
            .fillMaxWidth()
            .display(DisplayStyle.Grid)
            .gridTemplateColumns {
                if (breakpoint >= Breakpoint.MD) {
                    size(25.percent) // left column for names
                    size(75.percent) // right column for descriptions
                } else {
                    size(100.percent) // stack on mobile
                }
            }
            .gap(1.cssRem)
            .then(modifier)
            .toAttrs()
    ) {
        projects.forEach { (name, desc) ->
            Span(
                attrs = Modifier
                    .fontWeight(600)
                    .fontSize(1.1.em)
                    .alignSelf(AlignSelf.Start)
                    .toAttrs()
            ) {
                Text(name)
            }
            P(attrs = Modifier.margin(bottom = 0.75.cssRem).toAttrs()) {
                Text(desc)
            }
        }
    }
}
