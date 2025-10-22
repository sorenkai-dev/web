package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.components.widgets.SectionHeader
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
fun initCommunityEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Comunidad",
            description = "La Comunidad de Soren Kai — un espacio para escritores, tecnólogos y soñadores que exploran " +
                "la IA, la ética y el sentido de pertenencia.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/community")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun CommunityPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = { SectionHeader("Próximamente", breakpoint) }
    )

}
