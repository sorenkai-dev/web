package com.sorenkai.web.components.widgets.header

import androidx.compose.runtime.Composable
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun HeaderTitle(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .color(ColorMode.current.toSitePalette().brand.accent)
            .padding(left = 0.75.cssRem)
            .fontWeight(FontWeight.SemiBold)
    ) {
        Text(text)
    }
}
