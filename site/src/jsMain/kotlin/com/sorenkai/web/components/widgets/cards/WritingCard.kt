package com.sorenkai.web.components.widgets.cards

import androidx.compose.runtime.Composable
import com.sorenkai.web.WritingCardStyle
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.widgets.StatsRow
import com.sorenkai.web.components.widgets.TagChip
import com.sorenkai.web.components.widgets.footers.WritingCardFooter
import com.sorenkai.web.components.widgets.header.WritingCardHeader
import com.sorenkai.web.ui.text.WCBodyText
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
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCard(
    breakpoint: Breakpoint,
    writing: WritingEntry,
    onReadArticle: (title: String, id: String) -> Unit,
    onTagClick: (String) -> Unit,
    onShareClick: (WritingEntry) -> Unit,
    onViewToggle: (WritingEntry) -> Unit,
    onSalesClick: (WritingEntry) -> Unit,
    onLikeToggle: (WritingEntry) -> Unit,
    lang: String
) {
    val text = WCBodyText[lang] ?: error("Language not supported: $lang")

    Box(
        WritingCardStyle.toModifier()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.px)
        ) {
            WritingCardHeader(
                breakpoint = breakpoint,
                featured = writing.featured == true,
                featuredText = text.getValue("featured"),
                title = writing.title,
            )
            StatsRow(
                breakpoint = breakpoint,
                likes = writing.stats.likesCount,
                likesText = text.getValue("likes"),
                views = writing.stats.viewCount,
                viewsText = text.getValue("views"),
                comments = writing.stats.commentCount,
                commentsText = text.getValue("comments"),
                shares = writing.stats.shareCount,
                shareText = text.getValue("shares"),
            )

            P(
                attrs = Modifier.fontSize(if (breakpoint <= Breakpoint.SM) 0.8.cssRem else 1.cssRem).toAttrs()
            ) { Text(writing.synopsis ?: "") }
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
                liked = writing.stats.isLikedByMe,
                onLikeClick = {
                    onLikeToggle(writing)
                },
                onShareClick = {
                    onShareClick(writing)
                },
                onView = {
                    onViewToggle(writing)
                    onReadArticle(writing.title, writing.id)
                },
                onSalesClick = {
                    onSalesClick(writing)
                },
                writing = writing
            )
        }
    }
}
