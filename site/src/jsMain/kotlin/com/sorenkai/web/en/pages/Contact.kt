package com.sorenkai.web.en.pages

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
fun initContactEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            "Contact",
            description =
                "Get in touch with Soren Kai — writer, technologist, and creator exploring AI, " +
                    "ethics, and belonging. Reach out to start a conversation or explore potential collaborations."
        )
    )
}

@Page(routeOverride = "/en/contact")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ContactPage() {
    val breakpoint = LocalBreakpoint.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            ContactContent(breakpoint, "en")
        }
    )
}
