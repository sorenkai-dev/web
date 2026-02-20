package com.sorenkai.web.components.widgets.menus

import androidx.compose.runtime.Composable
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.koin.compose.koinInject

@Composable
fun SettingsMenu(
    lang: String,
) {
    val auth = koinInject<IAuthProvider>()
    Column(Modifier
        .color(ColorMode.current.toSitePalette().brand.accent)
        .gap(1.5.cssRem)
    ) {
        ThemeMenu(lang)
        LanguageMenu()
        AuthMenu(lang, auth, window.location.pathname)
    }
}
