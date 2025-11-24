package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.en.content.PrivacyEnContent
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
fun initPrivacyEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Privacy Policy",
            description = "Read the Privacy Policy for Soren Kai to understand how we handle your data, protect your privacy, and maintain transparency across our digital platforms."
        )
    )
}

@Page(routeOverride = "/en/policies/privacy")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun PrivacyPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrivacyEnContent(breakpoint)
    }
}
