package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.LeadParagraphStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.P

@Composable
fun LeadParagraph(
    breakpoint: Breakpoint,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val size = if (breakpoint <= Breakpoint.SM) {
        1.15.cssRem
    } else if (breakpoint <= Breakpoint.MD) {
        1.25.cssRem
    } else {
        1.4.cssRem
    }
    P (
        attrs = LeadParagraphStyle.toModifier()
            .then(modifier)
            .fontSize(size)
            .toAttrs()
    ) {
        content()
    }
}
