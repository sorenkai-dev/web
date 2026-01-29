package com.sorenkai.web.en.pages.writing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.components.layouts.PageLayoutData
import com.sorenkai.web.state.writings.WritingState
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@InitRoute
fun initWritingEnIndexPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Writings",
            description = "Portfolio of essays, reflections, and technical contributions exploring technology, culture, " +
                "and belonging."
        )
    )
}

@Page(routeOverride = "/en/writing/")
@Composable
fun WritingEnIndexPage(ctx: PageContext) {

    val path = window.location.pathname
    val id = path.substringAfterLast("/")
    LaunchedEffect(Unit) {
        WritingState.pendingId = id
        console.log("id: $id")
        console.log("path: $path")
//        ctx.router.navigateTo("/en/writings")
    }
    H1() {
        Text("You have arrived at the writing index page with id $id which is assigned to WritingState: ${WritingState.pendingId}")
    }
}
