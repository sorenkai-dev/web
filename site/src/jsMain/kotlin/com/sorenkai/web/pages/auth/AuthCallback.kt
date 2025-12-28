package com.sorenkai.web.pages.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.auth.Auth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import org.jetbrains.compose.web.dom.Text

@Page(routeOverride = "/auth/callback")
@Composable
fun AuthCallbackPage(ctx: PageContext) {
    LaunchedEffect(Unit) {
        val returnUrl = Auth.oidcInstance.handleCallback()
        ctx.router.navigateTo(returnUrl ?: "/")
    }

    // You could show a loading spinner here
    Text("Completing login...")
}
