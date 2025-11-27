package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.ChipStyle
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun TagChip(
    breakpoint: Breakpoint,
    tag: String,
    onClick: () -> Unit,
) {
    val (type, rawValue) = tag.split(":", limit = 2).let { it[0] to it[1] }
    val value = "#${rawValue.replace("-", " ")}"
    val bgColor =
        when (type) {
            "theme" -> ColorMode.current.toSitePalette().brand.themeColor
            "topic" -> ColorMode.current.toSitePalette().brand.topicColor
            "series" -> ColorMode.current.toSitePalette().brand.seriesColor
            "concept" -> ColorMode.current.toSitePalette().brand.conceptColor
            "project" -> ColorMode.current.toSitePalette().brand.projectColor
            "type" -> ColorMode.current.toSitePalette().brand.bookColor
            else -> ColorMode.current.toSitePalette().brand.topicColor
        }

    Span(
        attrs = ChipStyle.toModifier()
            .backgroundColor(bgColor)
            .fontSize( if (breakpoint <= Breakpoint.SM) 0.65.cssRem else 0.75.cssRem)
            .fontWeight(FontWeight.Bold)
            .minWidth(if (breakpoint <= Breakpoint.SM) 3.cssRem else 5.cssRem )
            .margin(topBottom = 0.5.cssRem, leftRight = 0.25.cssRem)
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(value)
    }
}
