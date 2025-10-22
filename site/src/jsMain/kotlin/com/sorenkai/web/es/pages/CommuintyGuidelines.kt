package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initCommGuideEsPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Directrices de la Comunidad",
            description = "Nuestras Directrices de la Comunidad establecen los valores, expectativas y normas que dan " +
                "forma a conversaciones significativas en SorenKai.com. Los detalles completos estarán disponibles " +
                "pronto.",
            lang = "es"
        )
    )
}

@Page(routeOverride = "/es/policies/community")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun CommunityGuidelinesPage() {
    val breakpoint = LocalBreakpoint.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        H1 (attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
            content = { Text("Próximamente") })
    }
}
