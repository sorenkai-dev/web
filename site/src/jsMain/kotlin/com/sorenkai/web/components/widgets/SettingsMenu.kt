package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.auth.Auth
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

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
        AuthMenu(lang)
    }
}

@Composable
private fun AuthMenu(lang: String) {
    val auth = Auth.instance
    val isAuthenticated = auth.isAuthenticated()

    Column(Modifier.gap(0.5.cssRem)) {
        if (isAuthenticated) {
            Span(
                Modifier
                    .cursor(Cursor.Pointer)
                    .onClick { auth.logout() }
                    .toAttrs()
            ) {
                Text(if (lang == "es") "Cerrar sesión" else "Logout")
            }
        } else {
            Span(
                Modifier
                    .cursor(Cursor.Pointer)
                    .onClick { auth.login() }
                    .toAttrs()
            ) {
                Text(if (lang == "es") "Iniciar sesión" else "Login")
            }
        }
    }
}
