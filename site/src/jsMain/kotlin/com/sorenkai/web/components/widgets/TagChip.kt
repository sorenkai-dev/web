package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.ChipStyle
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun TagChip(
    tag: String,
    onClick: () -> Unit
) {
    val (type, rawValue) = tag.split(":", limit = 2).let { it[0] to it[1] }
    val value = rawValue
        .replace("-", " ")
        .replaceFirstChar { it.uppercase() }
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
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(value)
    }
}
