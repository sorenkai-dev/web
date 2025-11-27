package com.sorenkai.web.es.pages

import androidx.compose.runtime.Composable
import com.sorenkai.web.api.content.WritingContent
import com.sorenkai.web.components.layouts.LocalBreakpoint
import com.sorenkai.web.components.layouts.PageLayoutData
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.rememberPageContext

@InitRoute
fun initArticleDeepLinkEn(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Artículo | Escritos", // Título ligeramente diferente
            description = "Explora un ensayo, reflexión o contribución técnica específica."
        )
    )
}

@Page("/es/writings/{slug}")
@Composable
fun ArticleDeepLinkPage() {val ctx = rememberPageContext()

    val slug = ctx.route.params.getValue("slug")
    val breakpoint = LocalBreakpoint.current

    WritingContent(
        breakpoint = breakpoint,
        lang = "es",
        openSlugFromUrl = slug
    )
}
