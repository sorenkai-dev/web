package com.sorenkai.web.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.content.WritingContent
import com.sorenkai.web.components.content.WritingsHeroQuote
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout

@InitRoute
fun initWritingsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Writings",
            description = "Portfolio of essays, reflections, and technical contributions exploring technology, culture, and belonging."
        )
    )
}

@Page
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun WritingsPage() {
    val breakpoint = LocalBreakpoint.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        WritingsHeroQuote(breakpoint)
        WritingContent(breakpoint)
    }
}
