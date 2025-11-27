package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.sorenkai.web.WritingCardStyle
import com.sorenkai.web.components.data.model.WritingEntry
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCard(
    breakpoint: Breakpoint,
    writing: WritingEntry,
    onReadArticle: (title: String, slug: String) -> Unit,
    onTagClick: (String) -> Unit,
    onShareClick: (WritingEntry) -> Unit,
    onViewToggle: (WritingEntry) -> Unit,
    onSalesClick: (WritingEntry) -> Unit,
    lang: String
) {
    val text = writingText[lang] ?: error("Language not supported: $lang")
    val title = writing.title
    val synopsis = writing.synopsis
    val likes = remember { mutableStateOf(writing.stats.likeCount) }
    val views = remember { mutableStateOf(writing.stats.viewCount) }
    val comments = writing.stats.commentCount
    val shares = remember { mutableStateOf(writing.stats.shareCount) }
    val likeKey = "liked_${writing.slug}"
    val liked = remember {  mutableStateOf(localStorage.getItem(likeKey) == "true") }
    val scope = rememberCoroutineScope()

    Box(
        WritingCardStyle.toModifier()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.px)
        ) {
            WritingCardHeader(
                breakpoint = breakpoint,
                featured = writing.featured == true,
                featuredText = text["featured"]!!,
                title = title,
            )
            StatsRow(
                breakpoint = breakpoint,
                likes = likes,
                likesText = text["likes"]!!,
                views = views,
                viewsText = text["views"]!!,
                comments = comments,
                commentsText = text["comments"]!!,
                shares = shares,
                shareText = text["shares"]!!,
            )

            P(
                attrs = Modifier.fontSize( if (breakpoint <= Breakpoint.SM) 0.8.cssRem else 1.cssRem).toAttrs()
            ){ Text(synopsis) }
            if (writing.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .flexWrap(FlexWrap.Wrap),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    writing.tags.take(6).forEach { tag ->
                        TagChip(
                            breakpoint = breakpoint,
                            tag = tag,
                            onClick = { onTagClick(tag) },
                        )
                    }
                }
            }
            Spacer()
            WritingCardFooter(
                lang = lang,
                breakpoint = breakpoint,
                liked = liked,
                scope = scope,
                likes = likes,
                likeKey = likeKey,
                shares = shares,
                onShareClick = onShareClick,
                onReadArticle = onReadArticle,
                onViewToggle = onViewToggle,
                onSalesClick = onSalesClick,
                views = views,
                writing = writing
            )
        }
    }
}

private val writingText = mapOf(
    "en" to mapOf(
        "featured" to "Featured",
        "views" to "Views",
        "comments" to "Comments",
        "likes" to "Likes",
        "notPublished" to "Not published yet",
        "shares" to "Shares"
    ),
    "es" to mapOf(
        "featured" to "Destacado",
        "views" to "Vistas",
        "comments" to "Comentarios",
        "likes" to "Me gusta",
        "shares" to "Compartidos"
    )
)

