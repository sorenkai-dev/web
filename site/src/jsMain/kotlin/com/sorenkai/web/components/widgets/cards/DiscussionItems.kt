package com.sorenkai.web.components.widgets.cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.api.dto.discussions.DiscussionReadDto
import com.sorenkai.web.components.data.model.community.discussions.ComposerMode
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.widgets.AuthNotice
import com.sorenkai.web.components.widgets.PostComposer
import com.sorenkai.web.components.widgets.footers.DiscussionItemFooter
import com.sorenkai.web.components.widgets.header.DiscussionItemHeader
import com.sorenkai.web.toSitePalette
import com.sorenkai.web.ui.text.NoticeText
import com.sorenkai.web.ui.viewmodels.DiscussionsViewModel
import com.sorenkai.web.util.renderMarkdown
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.silk.components.icons.fa.FaCaretRight
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlin.math.min
import kotlinx.browser.document
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun DiscussionItems(
    lang: String,
    discussion: DiscussionReadDto,
    allDiscussions: List<DiscussionReadDto>,
    isAuthenticated: Boolean,
    viewModel: DiscussionsViewModel,
    userId: String? = null,
    depth: Int = 0,
    onAuthRequired: () -> Unit
) {
    val noticeText = NoticeText[lang] ?: error("Language not supported: $lang")

    var showLikeAuthNotice by remember { mutableStateOf(false) }
    var showCommentAuthNotice by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var showCommentComposer by remember { mutableStateOf(false) }

    val expandedIds by viewModel.expandedIds.collectAsState()
    val loadingParentIds by viewModel.loadingParentIds.collectAsState()
    val counts by viewModel.childCount.collectAsState()
    val liveChildCount = counts[discussion.id] ?: discussion.childCount
    val hasChildren = liveChildCount > 0

    val isExpanded = expandedIds.contains(discussion.id)
    val isLoading = loadingParentIds.contains(discussion.id)

    if (showLikeAuthNotice) AuthNotice(
        message = noticeText.getValue("like"),
        actionLabel = noticeText.getValue("login"),
        onAction = onAuthRequired,
        onDismiss = { showLikeAuthNotice = false }
    )
    if (showCommentAuthNotice) AuthNotice(
        message = noticeText.getValue("comment"),
        actionLabel = noticeText.getValue("login"),
        onAction = onAuthRequired,
        onDismiss = { showCommentAuthNotice = false }
    )

    val effectiveDepth = min(depth, 4)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .id(discussion.id)
            .maxWidth(100.vw)
            .borderBottom(
                width = 1.px,
                style = if (discussion.kind == Kind.POST ) LineStyle.Solid else LineStyle.None,
                color = ColorMode.current.toSitePalette().brand.accent
            )
            .padding(
                top = if (discussion.kind == Kind.POST ) 0.5.cssRem else 0.cssRem,
                bottom = if (discussion.kind == Kind.POST ) 0.5.cssRem else 0.cssRem
            )
            .then(
                if (depth <= 4) {
                    Modifier.padding(
                        left = (effectiveDepth * 1.5).cssRem,
                    )
                }
                else Modifier
        )
    ){
        val parent = remember(discussion.parentId, allDiscussions) {
            allDiscussions.firstOrNull { it.id == discussion.parentId }
        }
        val hasSiblings = remember(discussion.parentId, allDiscussions) {
            allDiscussions.count { it.parentId == discussion.parentId } > 1
        }
        val highlightedId by viewModel.highlightedId.collectAsState()

        DiscussionItemHeader(
            discussion.id,
            highlightedId,
            discussion.author?.username ?: if (lang == "es") "Anónimo" else "Anonymous",
            discussion.createdAt
        )
        if (depth > 4 && parent != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(0.9.cssRem)
                    .color(ColorMode.current.toSitePalette().brand.accentText)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        document.getElementById(parent.id)?.scrollIntoView()
                        viewModel.highlightComment(parent.id)
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text("In reply to: ${parent.author?.username ?: if (lang == "es") "Anónimo" else "Anonymous"}")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alignItems(AlignItems.Stretch),
        ) {
            Column(
                modifier = Modifier
                    .height(Height.Inherit)
                    .padding(right = 0.5.cssRem),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FaCaretRight(
                    size = IconSize.SM,
                    modifier = Modifier
                        .visibility(
                            if (discussion.childCount > 1 ||
                                discussion.childCount == 1 && hasSiblings ||
                                hasChildren && discussion.kind==Kind.POST
                                ) Visibility.Visible
                            else Visibility.Hidden
                        )
                        .color(Colors.Blue)
                        .cursor(Cursor.Pointer)
                        .rotate(if (isExpanded) 90.deg else 0.deg)
                        .onClick {
                            viewModel.toggleExpanded(discussion.id)
                        }
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(left = 0.5.cssRem),
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (isEditing) {
                        PostComposer(
                            lang = lang,
                            placeholder = "",
                            initialText = discussion.body,
                            mode = ComposerMode.EDIT,
                            onSubmit = { updated ->
                                viewModel.editDiscussion(
                                    id = discussion.id,
                                    body = updated
                                )
                                isEditing = false
                            },
                            onCancel = {
                                isEditing = false
                            }
                        )
                    } else {
                        renderMarkdown(discussion.body)
                    }
                }
            }
        }
        DiscussionItemFooter(
            lang = lang,
            discussion = discussion,
            isAuthenticated = isAuthenticated,
            userId = userId,
            viewModel = viewModel,
            showCommentComposer = showCommentComposer,
            onCommentToggle = {
                if (isAuthenticated) showCommentComposer = !showCommentComposer
                else showCommentAuthNotice = true
            },
            onLikeClicked = {if (isAuthenticated) {
                if (discussion.isLikedByMe) viewModel.unlikeDiscussion(discussion.id)
                    else viewModel.likeDiscussion(discussion.id)
                } else {
                    showLikeAuthNotice = true
                }
            },
            onEditClicked = { isEditing = true },
            onDeleteClicked = { viewModel.deleteDiscussion(discussion.id) },
            onRestoreClicked = { viewModel.restoreDiscussion(discussion.id) },
            onReportClicked = { viewModel.reportDiscussion(discussion.id) }
        )
        if (expandedIds.contains(discussion.id)) {
            val children = allDiscussions.filter { it.parentId == discussion.id }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                children.forEach { child ->
                    DiscussionItems(
                        lang = lang,
                        discussion = child,
                        allDiscussions = allDiscussions,
                        isAuthenticated = isAuthenticated,
                        viewModel = viewModel,
                        userId = userId,
                        depth = depth + 1,
                        onAuthRequired = onAuthRequired
                    )
                }
            }
        }
    }
}
