package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.content.AboutContent
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
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
fun initAboutEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Acerca de",
            description = "Soren Kai es autor y tecnólogo que explora la IA, la ética y el sentido de pertenencia a " +
                "través de la escritura y la innovación centrada en las personas.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/about")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun AboutPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize().margin(bottom = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AboutContent("es", breakpoint)
    }
}
