package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.en.content.HomeContentEn
import com.sorenkai.web.en.content.HomeHeroEnQuote
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initHomeEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Home",
            description = "Soren Kai is a writer and technologist exploring how technology shapes human connection, identity, and belonging."
        )
    )
}

@Page(routeOverride = "/en/")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun HomePage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize().margin(bottom = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeroEnQuote(breakpoint)
        HomeContentEn(breakpoint)
    }

}
