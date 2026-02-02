package com.sorenkai.web.components.widgets.menus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.auth.IAuthProvider
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun AuthMenu(lang: String, auth: IAuthProvider, currentPath: String) {
    val authState = auth.authState.collectAsState().value

    Column(Modifier.gap(0.5.cssRem)) {
        if (authState == AuthState.Authenticated) {
            Span(
                Modifier
                    .cursor(Cursor.Pointer)
                    .onClick {
                        window.sessionStorage.setItem(
                            "postLogoutRedirectUri",
                            currentPath
                        )
                        auth.logout(currentPath)
                    }
                    .toAttrs()
            ) {
                Text(if (lang == "es") "Cerrar sesión" else "Logout")
            }
        } else {
            Span(
                Modifier
                    .cursor(Cursor.Pointer)
                    .onClick { auth.login(currentPath) }
                    .toAttrs()
            ) {
                Text(if (lang == "es") "Iniciar sesión" else "Login")
            }
        }
    }
}
