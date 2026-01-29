package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.api.content.ContactContent
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
fun initContactEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Contacto",
            description =
                "Ponte en contacto con Soren Kai — escritor, tecnólogo y creador que explora la IA, " +
                    "la ética y el sentido de pertenencia. Comunícate para iniciar una conversación o explorar " +
                    "posibles colaboraciones.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/contact")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ContactPage() {
    val breakpoint = LocalBreakpoint.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            ContactContent(breakpoint, "es")
        }
    )
}
