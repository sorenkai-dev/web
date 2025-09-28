package com.sorenkai.web.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.content.HomeContent
import com.sorenkai.web.components.content.HomeHeroQuote
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Home",
            description = "Soren Kai is a writer and technologist exploring how technology shapes human connection, identity, and belonging."
        )
    )
}

@Page(routeOverride = "/")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun HomePage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeroQuote(breakpoint)
        HomeContent(breakpoint)
    }

}