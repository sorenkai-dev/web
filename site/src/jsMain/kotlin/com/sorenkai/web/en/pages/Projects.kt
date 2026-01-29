package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.content.ProjectContent
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
fun initProjectsEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Projects",
            description = "Explore The Kindred — an ecosystem of platforms and research by Soren Kai. From KindredCircl " +
                "and LumiKona to Kindred Labs and KindredAI, every project unites empathy, ethics, and technology."
        )
    )
}

@Page(routeOverride = "/en/projects")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ProjectsPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize().margin(bottom = 4.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            ProjectContent("en", breakpoint)
        }
    )
}
