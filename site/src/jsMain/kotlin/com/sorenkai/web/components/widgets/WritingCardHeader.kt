package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.FeaturedStyle
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.unaryMinus
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCardHeader(
    breakpoint: Breakpoint,
    featured: Boolean,
    featuredText: String,
    title: String,
) {
    val parts = title.split(":", limit = 2)
    val mainTitle = parts.first().trim()
    val subTitle = parts.getOrNull(1)?.trim()
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (featured) {
            Row(
                modifier = FeaturedStyle.toModifier(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(featuredText)
            }
        }
        H3(
            attrs = Modifier
                .fillMaxWidth()
                .fontSize(if (breakpoint <= Breakpoint.SM) 1.20.cssRem else 1.35.cssRem)
                .margin(top = 1.cssRem, bottom = if (subTitle != null) -(1).cssRem else 1.cssRem)
                .textAlign(TextAlign.Center)
                .toAttrs()
        ) {
            Text(mainTitle)
        } // test
        subTitle?.let {
            H4(
                attrs = Modifier
                    .fillMaxWidth()
                    .fontSize(if (breakpoint <= Breakpoint.SM) 1.1.cssRem else 1.20.cssRem)
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) {
                Text(it)
            }
        }
    }
}
