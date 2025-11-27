package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem

@Composable
fun SettingsMenu(
    lang: String,
) {
    Column(Modifier
        .fillMaxWidth()
        .color(ColorMode.current.toSitePalette().brand.accent)
        .gap(1.5.cssRem)
    ) {
        ThemeMenu(lang)
        LanguageMenu()
    }
}
