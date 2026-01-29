package com.sorenkai.web.pages.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.auth.Auth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import org.jetbrains.compose.web.dom.Text

@Page(routeOverride = "/logout/callback")
@Composable
fun LogoutCallbackPage(ctx: PageContext) {
    LaunchedEffect(Unit) {
        val returnUrl = Auth.oidcInstance.handleLogoutCallback()
        ctx.router.navigateTo(returnUrl ?: "/")
    }

    Text("Completing logout...")
}
