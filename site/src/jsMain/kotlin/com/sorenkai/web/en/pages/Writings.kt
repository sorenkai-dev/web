package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.content.WritingContent
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
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

@Page(routeOverride = "/en/writings/{id?}")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun WritingsPage(ctx: PageContext) {
    val id = ctx.route.params["id"]
    val breakpoint = LocalBreakpoint.current

    // 1. Check for the 'slug' query parameter upon initial load
    val slugFromQuery = ctx.route.params["slug"]

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.cssRem),
        horizontalAlignment = Alignment.Start
    ) {
        console.log("WritingsPage: slugFromQuery=$slugFromQuery, idFromPath=$id")
        WritingContent(
            breakpoint,
            "en",
            slugFromQuery,
            idFromUrl = id
        )
    }
}
