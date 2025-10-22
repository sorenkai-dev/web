package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.SectionHeadingStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text

@Composable
fun SectionHeader(
    text: String,
    breakpoint: Breakpoint,
    modifier: Modifier = Modifier
) {
    val size = if (breakpoint <= Breakpoint.SM) 1.6.cssRem
                else if (breakpoint <= Breakpoint.MD) 1.75.cssRem
                else 2.cssRem
    H2 (
        attrs = SectionHeadingStyle.toModifier()
            .then(modifier)
            .fontSize(size)
            .toAttrs()
    ) {
        Text(text)
    }
}
