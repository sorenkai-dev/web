package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.api.content.WritingContent
import com.sorenkai.web.api.state.OpenArticleState
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
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
fun initWritingsEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Escritos",
            description = "Portafolio de ensayos, reflexiones y contribuciones técnicas que exploran la tecnología, la " +
                "cultura y el sentido de pertenencia.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/writings")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun WritingsPage() {
    val breakpoint = LocalBreakpoint.current
    val pendingSlug = OpenArticleState.slug

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.cssRem),
        horizontalAlignment = Alignment.Start
    ) {
        if (pendingSlug != null) {
            WritingContent(breakpoint, "es", pendingSlug)
            OpenArticleState.slug = null
        } else {
            WritingContent(breakpoint, "es")
        }
    }
}
