package com.sorenkai.web.components.widgets.modals


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sorenkai.web.SpinnerStyle
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.footers.WritingModalFooter
import com.sorenkai.web.ui.text.ArticleModalText
import com.sorenkai.web.ui.viewmodels.WritingsViewModel
import com.sorenkai.web.util.renderMarkdown
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.koin.compose.koinInject

@Composable
fun ArticleModal(
    id: String,
    lang: String,
    liked: Boolean,
    onLikeToggle: () -> Unit,
    onShareClick: () -> Unit,
    onCommentToggle: () -> Unit,
    onSubmit: () -> Unit,
    breakpoint: Breakpoint,
) {
    val writingsViewModel = koinInject<WritingsViewModel>()
    val text = ArticleModalText[lang] ?: error("Language not supported: $lang")

    val writingDetail by writingsViewModel.selectedWritingDetail.collectAsState()
    val isLoading by writingsViewModel.loading.collectAsState()
    val error by writingsViewModel.error.collectAsState()

    LaunchedEffect(id) {
        writingsViewModel.loadWritingDetail(id)
    }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 1.cssRem, bottom = 2.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) { Text(text.getValue("loading")) }
            Image(
                src = Res.Img.LOGO,
                modifier = SpinnerStyle.toModifier()
            )
        }
    } else {
        val content = when {
            error != null -> error ?: ""
            writingDetail != null -> writingDetail?.content ?: ""
            else -> ""
        }
        Div(
            attrs = Modifier
                .onClick { event ->
                    event.stopPropagation()
                }
                .toAttrs()
        ) {
            Column {
                console.log("Selected writing detail title inside the ArticleModal: ${writingDetail?.title}")
                console.log("Selected writing id inside the ArticleModal: $id")
                console.log("Content inside the ArticleModal: ${writingDetail}")
                renderMarkdown(content)
                WritingModalFooter(
                    lang = lang,
                    breakpoint = breakpoint,
                    liked = liked,
                    onLikeToggle = { onLikeToggle() },
                    onShareClick = { onShareClick() },
                    onCommentToggle = { onCommentToggle() },
                    onSubmit = { onSubmit() },
                    writingId = writingDetail?.id ?: "Not Found"
                )
            }
        }
    }
}

