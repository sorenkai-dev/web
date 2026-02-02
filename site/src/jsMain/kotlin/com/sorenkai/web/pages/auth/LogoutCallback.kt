package com.sorenkai.web.pages.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.auth.Auth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Text

@Page(routeOverride = "/logout/callback")
@Composable
fun LogoutCallbackPage(ctx: PageContext) {
    LaunchedEffect(Unit) {
        Auth.oidcInstance.handleLogoutCallback()
        val returnUrl = window.sessionStorage.getItem("postLogoutRedirectUri")
        window.sessionStorage.removeItem("postLogoutRedirectUri")
        ctx.router.navigateTo(returnUrl ?: "/")
    }

    Text("Completing logout...")
}
