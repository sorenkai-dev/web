package com.sorenkai.web.en.pages

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
fun initCommunityEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Community",
            description = "The Soren Kai Community — a space for writers, technologists, and dreamers to explore AI, " +
                "ethics, and belonging."
        )
    )
}

@Page(routeOverride = "/en/community")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun CommunityPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = { SectionHeader("Coming Soon", breakpoint) }
    )

}
