package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.content.GuidelinesContent
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout

@InitRoute
fun initCommGuideEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Community Guidelines",
            description = "Our Community Guidelines outline the values, expectations, and standards that shape " +
                "meaningful conversation across SorenKai.com. Full details coming soon."
        )
    )
}

@Page(routeOverride = "/en/policies/community")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun CommunityGuidelinesPage() {
    val breakpoint = LocalBreakpoint.current

    GuidelinesContent("en", breakpoint)
}
