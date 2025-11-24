package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.es.content.PrivacyEsContent
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
fun initPrivacyEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Política de Privacidad",
            description = "Lea la Política de Privacidad de Soren Kai para entender cómo manejamos sus datos, " +
                "protegemos su privacidad y mantenemos la transparencia en nuestras plataformas digitales.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/policies/privacy")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun PrivacyPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrivacyEsContent(breakpoint)
    }
}
