package com.sorenkai.web.components.widgets.footers

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import com.sorenkai.web.components.data.model.writing.WritingsType
import com.sorenkai.web.components.util.formatInstantToHumanReadable
import com.sorenkai.web.components.widgets.buttons.WritingActionButton
import com.sorenkai.web.components.widgets.icons.LikeIcon
import com.sorenkai.web.components.widgets.icons.ShareIcon
import com.sorenkai.web.ui.text.WCFooterText
import com.sorenkai.web.ui.text.WritingActionButtonText
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCardFooter(
    lang: String,
    breakpoint: Breakpoint,
    liked: Boolean,
    onLikeClick: (writingId: String) -> Unit,
    onShareClick: (writingId: String) -> Unit,
    onView: (writingId: String) -> Unit,
    onSalesClick: (writingId: String) -> Unit,
    writing: WritingEntry,
) {
    val text = WCFooterText[lang] ?: error("Language not supported: $lang")
    val buttonText = WritingActionButtonText[lang] ?: error("Language not supported: $lang")
    val enabled =
        when (writing.writingsType) {
            WritingsType.ARTICLE -> writing.writingsStatus == WritingsStatus.PUBLISHED
            WritingsType.BOOK -> writing.salesLink != null
        }
    val label =
        when (writing.writingsType) {
            WritingsType.ARTICLE -> if (enabled) {
                buttonText.getValue("readArticle")
            } else {
                buttonText.getValue("comingSoon")
            }

            WritingsType.BOOK -> if (enabled) {
                buttonText.getValue("buyBook")
            } else {
                buttonText.getValue("comingSoon")
            }
        }

    if (breakpoint < Breakpoint.SM) {
        Column(
            Modifier.fillMaxWidth()
                .gap(0.75.cssRem)
                .fontSize(0.8.cssRem)
                .margin(top = 0.5.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(bottom = 0.5.cssRem),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LikeIcon(
                    liked = liked,
                    onClick = { onLikeClick(writing.id) },
                )
                ShareIcon(
                    onShareClick = { onShareClick(writing.id) }
                )
            }
            DateLines(writing, lang, text)
            WritingActionButton(
                enabled = enabled,
                label = label,
                onClick = {
                    when (writing.writingsType) {
                        WritingsType.ARTICLE -> onView(writing.id)
                        WritingsType.BOOK -> onSalesClick(writing.id)
                    }
                }
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth()
                .margin(top = 1.cssRem)
                .fontSize(1.cssRem),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(leftRight = 2.cssRem)
                    .gap(0.5.cssRem),
                verticalArrangement = Arrangement.Center,
            ) {
                LikeIcon(
                    liked = liked,
                    onClick = { onLikeClick(writing.id) },
                )
                ShareIcon(
                    onShareClick = { onShareClick(writing.id) }
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                DateLines(writing, lang, text)
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WritingActionButton(
                    enabled = enabled,
                    label = label,
                    onClick = {
                        when (writing.writingsType) {
                            WritingsType.ARTICLE -> onView(writing.id)
                            WritingsType.BOOK -> onSalesClick(writing.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DateLines(
    writing: WritingEntry,
    lang: String,
    text: Map<String, String> = WCFooterText[lang] ?: error("Language not supported: $lang")
) {
    val createdAt = formatInstantToHumanReadable(writing.createdAt)
    val updatedAt = writing.updatedAt?.let { formatInstantToHumanReadable(it) }
    val publishedAt = writing.publishedAt?.let { formatInstantToHumanReadable(it) }

    Row {
        if (updatedAt != null && updatedAt != createdAt) {
            Text("${text.getValue("lastUpdated")} $updatedAt")
        }
    }
    Row {
        if (publishedAt != null) {
            Text("${text.getValue("published")}: $publishedAt")
        } else if (writing.writingsStatus == WritingsStatus.PUBLISHED) {
            Text("${text.getValue("published")}: $createdAt")
        } else {
            Text(text.getValue("notPublished"))
        }
    }
    Row {
        if (publishedAt != null && publishedAt == createdAt) {
            return@Row
        } else {
            Text("${text.getValue("createdAt")}: $createdAt")
        }
    }
}
