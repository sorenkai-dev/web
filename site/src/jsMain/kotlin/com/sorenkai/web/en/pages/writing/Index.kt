package com.sorenkai.web.en.pages.writing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.state.writings.WritingState
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import kotlinx.browser.window

@InitRoute
fun initWritingEnIndexPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Writings",
            description = "Portfolio of essays, reflections, and technical contributions exploring technology, culture, " +
                "and belonging."
        )
    )
}

@Page(routeOverride = "/en/writing/index")
@Composable
fun WritingEnIndexPage(ctx: PageContext) {
    LaunchedEffect(Unit) {
        val path = window.location.pathname
        val id = path.substringAfterLast("/")
        WritingState.pedingId = id
        ctx.router.navigateTo("/en/writings")
    }
}
