package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.es.content.TermsEsContent
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
fun initToSEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Términos de Servicio",
            description = "Lea los Términos de Servicio de Soren Kai, que describen el uso del sitio, la propiedad del " +
                "contenido y las responsabilidades de los usuarios.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/policies/terms")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ToSPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       TermsEsContent(breakpoint)

    }
}
