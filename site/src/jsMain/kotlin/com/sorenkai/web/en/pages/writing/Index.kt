package com.sorenkai.web.en.pages.writing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sorenkai.web.state.writings.WritingState
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@InitKobweb
fun initWritingEnIndexPage(ctx: InitKobwebContext) {
    ctx.router.register("/en/writing/{id}") { WritingEnIndexPage(it) }
}

@Page(routeOverride = "/en/writing/[id]")
@Composable
fun WritingEnIndexPage(ctx: PageContext) {
    val id = ctx.route.dynamicParams["id"] ?: ""
    LaunchedEffect(Unit) {
        WritingState.pendingId = id
        console.log("id: $id")
//        ctx.router.navigateTo("/en/writings")
    }
    H1() {
        Text("Dynamic Link detected for ID: $id")
    }
}
