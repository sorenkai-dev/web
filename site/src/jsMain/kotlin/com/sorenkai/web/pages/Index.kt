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
import kotlinx.browser.localStorage
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
    LaunchedEffect(Unit) {
        val storedPref = localStorage.getItem("lang")

        if (storedPref != null) {
            // Preferred language exists — always honor it
            ctx.router.navigateTo("/$storedPref")
            return@LaunchedEffect
        }

        // No preference saved — use first-visit detection
        val preferredLang = window.navigator.language.lowercase()
        val lang = if (preferredLang.startsWith("es")) "es" else "en"

        localStorage.setItem("lang", lang)
        ctx.router.navigateTo("/$lang")
    }

    // Fallback for bots / no-JS
    Column(
        modifier = Modifier.fillMaxSize()
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
