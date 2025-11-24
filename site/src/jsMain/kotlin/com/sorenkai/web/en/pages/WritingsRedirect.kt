package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.api.state.OpenArticleState
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext

@Page("/en/writings/{slug}")
@Composable
fun WritingsRedirectPage(ctx: PageContext) {
    // Store the slug globally so WritingsPage knows to open the modal
    OpenArticleState.slug = ctx.route.params.getValue("slug")
    console.log("Redirecting to /en/writings/${ctx.route.params["slug"]}")
    console.log("OpenArticleState slug: ${OpenArticleState.slug}")

    // Immediately navigate to the actual Writings page
    LaunchedEffect(Unit) {
        ctx.router.navigateTo("/en/writings")
    }
}
