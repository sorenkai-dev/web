package com.sorenkai.web.components.widgets.footers

import androidx.compose.runtime.Composable
import com.sorenkai.web.api.dto.discussions.DiscussionReadDto
import com.sorenkai.web.components.data.model.community.discussions.ComposerMode
import com.sorenkai.web.components.data.model.community.discussions.DiscussionsStatus
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.widgets.PostComposer
import com.sorenkai.web.toSitePalette
import com.sorenkai.web.ui.viewmodels.DiscussionsViewModel
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.icons.fa.FaCommentDots
import com.varabyte.kobweb.silk.components.icons.fa.FaFlag
import com.varabyte.kobweb.silk.components.icons.fa.FaHeart
import com.varabyte.kobweb.silk.components.icons.fa.FaPen
import com.varabyte.kobweb.silk.components.icons.fa.FaTrashArrowUp
import com.varabyte.kobweb.silk.components.icons.fa.FaTrashCan
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun DiscussionItemFooter(
    lang: String,
    discussion: DiscussionReadDto,
    isAuthenticated: Boolean,
    userId: String?,
    viewModel: DiscussionsViewModel,
    showCommentComposer: Boolean, // State raised to parent
    onCommentToggle: () -> Unit,   // Triggered by footer button
    onLikeClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onRestoreClicked: () -> Unit,
    onReportClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .position(Position.Relative)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(leftRight = 1.5.cssRem)
                    .gap(0.5.cssRem)
                    .alignItems(AlignItems.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaHeart(
                    size = IconSize.SM,
                    style = if (discussion.isLikedByMe) IconStyle.FILLED else IconStyle.OUTLINE,
                    modifier = Modifier
                        .color(Colors.Red)
                        .cursor(if (isAuthenticated) Cursor.Pointer else Cursor.Default)
                        .onClick { onLikeClicked() }
                )
                Text("${discussion.likes}")

                Row(
                    modifier = Modifier.fillMaxWidth().gap(1.5.cssRem),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (isAuthenticated) {
                        if (userId == discussion.author?.userId) {
                            if (discussion.status == DiscussionsStatus.VISIBLE) {
                                FaPen(
                                    size = IconSize.SM,
                                    modifier = Modifier
                                        .cursor(Cursor.Pointer)
                                        .onClick { onEditClicked() }
                                )
                                FaTrashCan(
                                    size = IconSize.SM,
                                    modifier = Modifier
                                        .cursor(Cursor.Pointer)
                                        .onClick { onDeleteClicked() }
                                )
                            } else {
                                FaTrashArrowUp(
                                    size = IconSize.SM,
                                    modifier = Modifier
                                        .cursor(Cursor.Pointer)
                                        .onClick { onRestoreClicked() }
                                )
                            }
                        } else {
                            FaFlag(
                                size = IconSize.SM,
                                modifier = Modifier
                                    .cursor(Cursor.Pointer)
                                    .color(ColorMode.current.toSitePalette().brand.visited)
                                    .onClick { onReportClicked() }
                            )
                        }
                    }
                    FaCommentDots(
                        size = IconSize.SM,
                        style = IconStyle.OUTLINE,
                        modifier = Modifier
                            .cursor(Cursor.Pointer)
                            .color(Colors.Green)
                            .onClick { onCommentToggle() }
                    )
                }
            }
        }

        if (showCommentComposer) {
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
                            onSubmit = { comment ->
                                viewModel.createDiscussion(
                                    lang = lang,
                                    kind = Kind.COMMENT,
                                    parentId = discussion.id,
                                    body = comment
                                )
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
