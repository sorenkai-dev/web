package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.ChipStyle
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun CategoryChip(
    category: String,
    categoryLabel: String,
    onClick: () -> Unit,
) {
    val bgColor =
        when (category) {
            "theme" -> ColorMode.current.toSitePalette().brand.themeColor
            "topic" -> ColorMode.current.toSitePalette().brand.topicColor
            "series" -> ColorMode.current.toSitePalette().brand.seriesColor
            "concept" -> ColorMode.current.toSitePalette().brand.conceptColor
            "project" -> ColorMode.current.toSitePalette().brand.projectColor
            "type" -> ColorMode.current.toSitePalette().brand.bookColor
            else -> ColorMode.current.toSitePalette().brand.allColor
        }
    Span(
        attrs = ChipStyle.toModifier()
            .backgroundColor(bgColor)
            .boxShadow(
                4.px,
                4.px,
                8.px,
                color = ColorMode.current.toSitePalette().brand.shadow
            )
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(categoryLabel)
    }

}
