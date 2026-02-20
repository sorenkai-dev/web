package com.sorenkai.web.components.widgets.footers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.components.data.model.community.discussions.ComposerMode
import com.sorenkai.web.components.widgets.PostComposer
import com.sorenkai.web.components.widgets.buttons.WritingActionButton
import com.sorenkai.web.components.widgets.icons.LikeIcon
import com.sorenkai.web.components.widgets.icons.ShareIcon
import com.sorenkai.web.ui.text.ArticleModalText
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun WritingModalFooter(
    lang: String,
    breakpoint: Breakpoint,
    liked: Boolean,
    onLikeToggle: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onCommentToggle: () -> Unit,
    onSubmit: () -> Unit,
    writingId: String,
) {
    val text = ArticleModalText[lang] ?: error("Language not supported: $lang")
    var showFooter by remember { mutableStateOf(false) }

    LaunchedEffect(showFooter) {
        if (showFooter) {
            document.getElementById("comment-button")?.scrollIntoView(ScrollBehavior.Smooth)
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
                    .fillMaxWidth(80.percent)
                    .margin(bottom = 0.5.cssRem),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LikeIcon(
                    liked = liked,
                    onClick = { onLikeToggle(writingId) },
                )
                ShareIcon(
                    onShareClick = { onShareClick(writingId) }
                )
            }
            WritingActionButton(
                enabled = true,
                label = text.getValue("comment"),
                onClick = {
                    showFooter = !showFooter
                    console.log("Comment composer toggled")
                    console.log(showFooter)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(80.percent)
            ) {
                if (showFooter) {
                    Box(
                        modifier = Modifier
                            .position(Position.Absolute)   // 🔑 overlay
                            .top(100.percent)              // below footer
                            .left(0.px)
                            .right(0.px)
                            .margin(top = 0.5.cssRem)
                            .zIndex(10)
                    ) {
                        // center + constrain width
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .maxWidth(850.px)
                            ) {
                                PostComposer(
                                    lang = lang,
                                    placeholder = if (lang == "es") "Escribe un comentario…" else "Write a comment…",
                                    mode = ComposerMode.CREATE,
                                    onSubmit = {
                                        onSubmit()
                                        onCommentToggle()
                                    },
                                    onCancel = {
                                        onCommentToggle()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(80.percent)
                    .margin(top = 1.cssRem)
                    .fontSize(1.cssRem),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LikeIcon(
                    liked = liked,
                    onClick = {
                        onLikeToggle(writingId)
                        console.log("Like toggle from Modal Footer")
                    },
                )
                ShareIcon(
                    onShareClick = { onShareClick(writingId) }
                )
                WritingActionButton(
                    modifier = Modifier.id("comment-button"),
                    enabled = true,
                    label = text.getValue("comment"),
                    onClick = {
                        onCommentToggle()
                        showFooter = true
                    }
                )
            }
        }
    }
}
