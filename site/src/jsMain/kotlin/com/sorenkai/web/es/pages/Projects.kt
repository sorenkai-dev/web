package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.api.content.ProjectContent
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
fun initProjectsEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Proyectos",
            description = "Explora The Kindred — un ecosistema de plataformas e investigaciones creado por Soren Kai. " +
                "Desde KindredCircl y LumiKona hasta Kindred Labs y KindredAI, cada proyecto une empatía, ética y " +
                "tecnología.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/projects")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ProjectsPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize().margin(bottom = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = { ProjectContent("es", breakpoint) }
    )
}
