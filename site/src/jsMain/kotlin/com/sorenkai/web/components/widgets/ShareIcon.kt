package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.icons.fa.FaShare
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem

@Composable
fun ShareIcon(
    shares: MutableState<Int>,
    onShareClick: (WritingEntry) -> Unit,
    writing: WritingEntry
) {
    FaShare(
        size = IconSize.LG,
        modifier = Modifier
            .margin(1.cssRem)
            .color(color = ColorMode.current.toSitePalette().brand.accent)
            .cursor(Cursor.Pointer)
            .onClick {
                shares.value += 1
                onShareClick(writing)
            }
    )
}
