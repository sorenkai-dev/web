package com.sorenkai.web.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.components.layouts.PageLayoutData
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import kotlinx.browser.window
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@InitRoute
fun initLanguageRouter(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Language Selection",
            description = "Select your preferred language to continue to the correct version of Soren Kai's site.",
            robotsContent = "noindex,follow"
        )
    )
}

@Page(routeOverride = "/")
@Composable
fun LanguageRouterPage(ctx: PageContext) {
    // Simple redirect logic
    LaunchedEffect(Unit) {
        val preferredLang = window.navigator.language
        when {
            preferredLang.startsWith("es") -> ctx.router.navigateTo("/es")
            else -> ctx.router.navigateTo("/en")
        }
    }

    // Fallback for non-JS users or bots
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alignItems(AlignItems.Center)
            .justifyContent(JustifyContent.Center)
            .padding(2.cssRem)
    ) {
        H1 { Text("Choose your language") }
        Ul {
            Li { A("/en") { Text("English") } }
            Li { A("/es") { Text("Español") } }
        }
    }
}
