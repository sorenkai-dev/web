package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.content.HomeContent
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
fun initHomeEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Inicio",
            description = "Soren Kai es escritor y tecnólogo que explora cómo la tecnología moldea la conexión humana, " +
                "la identidad y el sentido de pertenencia.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun HomePage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize().margin(bottom = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeContent("es", breakpoint)
    }

}
