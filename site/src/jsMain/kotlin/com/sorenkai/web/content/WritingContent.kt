package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.SpinnerStyle
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.components.data.model.auth.AuthNoticeType
import com.sorenkai.web.components.data.model.community.discussions.ComposerMode
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.AuthNotice
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.CategoryFilterBar
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.ModalOverlay
import com.sorenkai.web.components.widgets.PostComposer
import com.sorenkai.web.components.widgets.cards.WritingCard
import com.sorenkai.web.components.widgets.modals.ArticleModal
import com.sorenkai.web.en.data.WritingDataEn
import com.sorenkai.web.es.data.WritingDataEs
import com.sorenkai.web.ui.text.NoticeText
import com.sorenkai.web.ui.viewmodels.WritingsViewModel
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.koin.compose.koinInject

@Composable
fun WritingContent(
    breakpoint: Breakpoint,
    lang: String,
    openSlugFromUrl: String? = null,
    idFromUrl: String? = null
) {
    val viewModel = koinInject<WritingsViewModel>()
    var showCommentComposer by remember { mutableStateOf(false) }
    val data =
        when (lang) {
            "es" -> WritingDataEs
            else -> WritingDataEn
        }
    val noticeText = NoticeText[lang] ?: error("Language not supported: $lang")
    var authNotice by remember { mutableStateOf<AuthNoticeType?>(null) }
    val writings by viewModel.writings.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val authState by viewModel.authState.collectAsState()
    val enabled = authState == AuthState.Authenticated
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val resolvedWritingId by viewModel.resolvedWritingId.collectAsState()
    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalId by remember { mutableStateOf("") }
    var selectedWriting by remember { mutableStateOf<WritingEntry?>(null) }
    val selectedWritingDetail by viewModel.selectedWritingDetail.collectAsState()
    val submitContent: (String) -> Unit = { body ->
        selectedWriting?.let { writing ->
            viewModel.createComment(
                body = body,
                lang = lang,
                writingId = writing.id
            )
        }
    }
    /* ---------- Initial Load ---------- */

    LaunchedEffect(lang) {
        viewModel.loadWritings(lang)
    }

    LaunchedEffect(idFromUrl) {
        idFromUrl?.let{ id ->
            modalId = id
            showModal = true
        }
    }

    LaunchedEffect(openSlugFromUrl) {
        openSlugFromUrl?.let(viewModel::resolveSlug)
    }

    LaunchedEffect(resolvedWritingId) {
        resolvedWritingId?.let { id ->
            val writing = writings.firstOrNull { it.id == id }
            if (writing != null) {
                selectedWriting = writing
                modalTitle = writing.title
                modalId = writing.id
                showModal = true
                window.document.title = "${writing.title} | Writings"
            }
        }
    }

    LaunchedEffect(modalId) {
        if (modalId.isNotBlank()) {
            viewModel.loadWritingDetail(modalId)
        }
    }

    val closeModal = {
        showModal = false
        selectedWriting = null
        showCommentComposer = false
        modalTitle = ""
        modalId = ""
        window.history.replaceState(null, "", "/$lang/writings")
    }

    authNotice?.let { type ->
        AuthNotice(
            message = noticeText.getValue(
                if (type == AuthNoticeType.LIKE) "like" else "comment"
            ),
            actionLabel = noticeText.getValue("login"),
            onAction = { viewModel.login() },
            onDismiss = { authNotice = null }
        )
    }

    /* ---------- Header ---------- */

    Row(
        modifier = BlockquoteCardStyle.toModifier().fillMaxWidth()
    ) {
        BlockQuote(
            content = { Text(data.writingQuote) },
            citation = data.writingQuoteAuthor,
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }

    LeadParagraph(breakpoint) { Text(data.leadParagraph) }

    /* ---------- Filters ---------- */

    CategoryFilterBar(
        breakpoint = breakpoint,
        activeCategory = selectedCategory,
        onSelect = viewModel::selectCategory,
        lang = lang
    )

    /* ---------- Content ---------- */

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 24.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(data.loadingText)
                Image(src = Res.Img.LOGO, modifier = SpinnerStyle.toModifier())
            }
        }

        error != null -> {
            P { Text(error!!) }
        }

        else -> {
            Column(
                Modifier.fillMaxWidth().gap(4.cssRem)
            ) {
                writings.forEach { writing ->
                    WritingCard(
                        writing = writing,
                        breakpoint = breakpoint,
                        onReadArticle = { title, id ->
                            selectedWriting = writing
                            modalTitle = title
                            modalId = id
                            showModal = true
                        },
                        onTagClick = viewModel::selectTag,
                        onShareClick = { viewModel.share(writing, lang) },
                        onViewToggle = { viewModel.recordView(it.id) },
                        onSalesClick = { writing ->
                            writing.salesLink?.let { link ->
                                window.open(link, "_blank")
                                viewModel.recordView(writing.id)
                                viewModel.recordSalesClick(writing.id)
                            }
                        },
                        onLikeToggle = { writingToLike ->
                            if (enabled) {
                                viewModel.toggleLike(writingToLike.id)
                            }
                            else {
                                authNotice = AuthNoticeType.LIKE
                            }
                        },
                        lang = lang
                    )
                }
            }
        }
    }

    /* ---------- Modal ---------- */

    if (showModal && modalId.isNotEmpty()) {
        ModalOverlay(
            title = selectedWritingDetail?.title,
            onClose = closeModal,
            bottomBar = {
                if (showCommentComposer) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .margin(top = 1.cssRem)
                            .id("comment-box")
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
                                    placeholder = noticeText.getValue("writingPlaceholder"),
                                    mode = ComposerMode.COMMENT,
                                    onSubmit = { body ->
                                        submitContent(body)
                                        showCommentComposer = false
                                    },
                                    onCancel = {
                                        showCommentComposer = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) {
            ArticleModal(
                id = modalId,
                lang = lang,
                writingDetail = selectedWritingDetail,
                isLoading = isLoading,
                error = error,
                liked = false,
                onLikeToggle = {
                    if (!enabled) authNotice = AuthNoticeType.LIKE
                    else viewModel.toggleLike(modalId)
                },
                onShareClick = {
                    selectedWriting?.let { writing ->
                        viewModel.share(writing, lang)
                    }
                },
                onCommentToggle = {
                    if (!enabled) authNotice = AuthNoticeType.COMMENT
                    else showCommentComposer = !showCommentComposer
                },
                onSubmit = { },
                breakpoint = breakpoint
            )
        }
    }
}
