package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.sorenkai.web.components.data.model.writing.WritingsStatus
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.components.util.formatIsoDateToHumanReadable
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
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCardFooter(
    lang: String,
    breakpoint: Breakpoint,
    liked: MutableState<Boolean>,
    scope: CoroutineScope,
    likes: MutableState<Int>,
    likeKey: String,
    shares: MutableState<Int>,
    onShareClick: (WritingEntry) -> Unit,
    onReadArticle: (title: String, slug: String) -> Unit,
    onViewToggle: (WritingEntry) -> Unit,
    onSalesClick: (WritingEntry) -> Unit,
    views: MutableState<Int>,
    writing: WritingEntry,
) {
    val text = texts[lang] ?: error("Language not supported: $lang")

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
                    likes = likes,
                    likeKey = likeKey,
                    writing = writing,
                    scope = scope,
                )
                ShareIcon(
                    shares = shares,
                    onShareClick = onShareClick,
                    writing = writing
                )
            }
            DateLines(writing, lang, text)
            WritingActionButton(
                writing = writing,
                onReadArticle = onReadArticle,
                onViewToggle = onViewToggle,
                onSalesClick = onSalesClick,
                views = views,
                lang = lang
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
                    likes = likes,
                    likeKey = likeKey,
                    writing = writing,
                    scope = scope
                )
                ShareIcon(
                    shares = shares,
                    onShareClick = onShareClick,
                    writing = writing
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
                    writing = writing,
                    onReadArticle = onReadArticle,
                    onViewToggle = onViewToggle,
                    onSalesClick = onSalesClick,
                    views = views,
                    lang = lang
                )
            }
        }
    }
}

@Composable
private fun DateLines(
    writing: WritingEntry,
    lang: String,
    text: Map<String, String> = texts[lang] ?: error("Language not supported: $lang")
) {
    val createdAt = formatIsoDateToHumanReadable(writing.createdAt)
    val updatedAt = writing.updatedAt?.let { formatIsoDateToHumanReadable(it) }
    val publishedAt = writing.publishedAt?.let { formatIsoDateToHumanReadable(it) }

    Row { Text("${text["createdAt"]!!}: $createdAt") }
    Row {
        if (publishedAt != null) {
            Text("${text["published"]}: $publishedAt")
        } else if (writing.writingsStatus == WritingsStatus.PUBLISHED) {
            Text("${text["published"]}: $createdAt")
        } else {
            Text(text["notPublished"] ?: "Not published yet")
        }
    }
    Row {
        if (updatedAt != null && updatedAt != createdAt) {
            Text("${text["lastUpdated"]} $updatedAt")
        }
    }
}

private val texts = mapOf(
    "en" to mapOf(
        "createdAt" to "Created",
        "published" to "Published",
        "lastUpdated" to "Updated",
        "readArticle" to "Read Article",
        "comingSoon" to "Coming Soon",
        "buyBook" to "Buy Book",
        "notPublished" to "Not published yet",
    ),
    "es" to mapOf(
        "createdAt" to "Creado",
        "published" to "Publicado",
        "lastUpdated" to "Actualización",
        "readArticle" to "Leer artículo",
        "comingSoon" to "Próximamente",
        "buyBook" to "Comprar libro",
        "notPublished" to "Not published yet",
    )
)
