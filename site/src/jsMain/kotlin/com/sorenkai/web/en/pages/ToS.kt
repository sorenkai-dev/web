package com.sorenkai.web.en.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.en.content.TermsEnContent
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
fun initToSEnPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Terms of Service",
            description = "Read the Terms of Service for Soren Kai, outlining site usage, content ownership, and user responsibilities across our digital platforms."
        )
    )
}

@Page(routeOverride = "/en/policies/terms")
@Layout("com.sorenkai.web.components.layouts.PageLayout")
@Composable
fun ToSPage() {
    val breakpoint = LocalBreakpoint.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TermsEnContent(breakpoint)
    }
}
