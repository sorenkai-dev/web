package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.en.content.WritingContentEn
import com.sorenkai.web.en.content.WritingsHeroEnQuote
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.css.cssRem

@InitRoute
fun initWritingsEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Writings",
            description = "Portfolio of essays, reflections, and technical contributions exploring technology, culture, " +
                "and belonging."
        )
    )
}

@Page(routeOverride = "/en/writings")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun WritingsPage() {
    val breakpoint = LocalBreakpoint.current

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.cssRem),
        horizontalAlignment = Alignment.Start
    ) {
        WritingsHeroEnQuote()
        WritingContentEn(breakpoint, lang = "en")
    }
}
