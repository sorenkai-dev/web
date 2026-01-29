package com.sorenkai.web.components.widgets.icons

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.icons.fa.FaHeart
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import org.jetbrains.compose.web.css.cssRem

@Composable
fun LikeIcon(
    liked: Boolean,
    onClick: () -> Unit,
) {
    FaHeart(
        size = IconSize.LG,
        modifier = Modifier
            .margin(1.cssRem)
            .color(color = Colors.Red)
            .cursor(Cursor.Pointer)
            .onClick { onClick() },
        style = if (liked) IconStyle.FILLED else IconStyle.OUTLINE
    )
}
