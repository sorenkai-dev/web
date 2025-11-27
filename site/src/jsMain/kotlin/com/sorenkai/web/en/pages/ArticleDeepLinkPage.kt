package com.sorenkai.web.en.pages

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
    // This is the fallback metadata before the article data is fetched
    ctx.data.add(
        PageLayoutData(
            title = "Article | Writings", // Slightly different title
            description = "Explore a specific essay, reflection, or technical contribution."
        )
    )
}

@Page("/en/writings/{slug}")
@Composable
fun ArticleDeepLinkPage() {val ctx = rememberPageContext()
    // 1. Directly capture the slug from the URL path
    val slug = ctx.route.params.getValue("slug")
    val breakpoint = LocalBreakpoint.current

    // 2. Render the WritingContent component immediately
    // The slug is passed as the 'openSlugFromUrl' parameter
    WritingContent(
        breakpoint = breakpoint,
        lang = "en",
        openSlugFromUrl = slug
    )
}
