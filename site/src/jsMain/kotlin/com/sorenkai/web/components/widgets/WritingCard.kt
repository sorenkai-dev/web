package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.sorenkai.web.FeaturedStyle
import com.sorenkai.web.WritingCardStyle
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.components.data.model.Status
import com.sorenkai.web.components.data.model.Type
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.components.util.formatIsoDateToHumanReadable
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaHeart
import com.varabyte.kobweb.silk.components.icons.fa.FaShare
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingCard(
    writing: WritingEntry,
    onReadArticle: (title: String, slug: String) -> Unit,
    onTagClick: (String) -> Unit,
    onLikeToggle: (WritingEntry, Boolean) -> Unit,
    onShareClick: (WritingEntry) -> Unit,
    lang: String
) {
    val enabled = when (writing.type) {
        Type.BOOK -> writing.salesLink != null
        Type.ARTICLE -> writing.status == Status.PUBLISHED
    }
    val scope = rememberCoroutineScope()
    val text = writingText[lang] ?: error("Language not supported: $lang")
    val title = writing.title
    val synopsis = writing.synopsis
    val likes = writing.stats.likeCount
    val views = writing.stats.viewCount
    val comments = writing.stats.commentCount
    val shares = writing.stats.shareCount
    val createdAt = formatIsoDateToHumanReadable(writing.createdAt)
    val updatedAt = writing.updatedAt?.let { formatIsoDateToHumanReadable(it) }
    val publishedAt = writing.publishedAt?.let { formatIsoDateToHumanReadable(it) }
    val likeKey = "liked_${writing.slug}"
    val liked = remember {  mutableStateOf(localStorage.getItem(likeKey) == "true") }

    Box(
        WritingCardStyle.toModifier()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.px)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (writing.featured == true) {
                    Row(
                        modifier = FeaturedStyle.toModifier(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(text["featured"]!!)
                    }
                }
                H3 { Text(title) }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(leftRight = 2.cssRem),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column { Text("${text["likes"]}: $likes") }
                Column { Text("${text["views"]}: $views") }
                Column { Text("${text["comments"]}: $comments") }
                Column { Text("${text["shares"]}: $shares") }
            }
            P { Text(synopsis) }

            // Tags row (subtle chips)
            if (writing.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(leftRight = 2.cssRem, bottom = 2.cssRem),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    writing.tags.take(6).forEach { tag ->
                        TagChip(
                            tag = tag,
                            onClick = { onTagClick(tag) }
                        )
                    }
                }
            }

            Spacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(leftRight = 2.cssRem)
                        .gap(0.5.cssRem),
                    verticalArrangement = Arrangement.Center,
                ) {
                    FaHeart(
                        modifier = Modifier
                            .color(color = Colors.Red)
                            .cursor(Cursor.Pointer)
                            .onClick {
                                // Optimistically update local like state & persist
                                val newLiked = !liked.value
                                liked.value = newLiked
                                localStorage.setItem(likeKey, newLiked.toString())
                                // Notify parent to sync with backend
                                onLikeToggle(writing, newLiked)
                            },
                        style = if (liked.value) IconStyle.FILLED else IconStyle.OUTLINE
                    )
                    FaShare(
                        modifier = Modifier
                            .color(color = ColorMode.current.toSitePalette().brand.accent)
                            .cursor(Cursor.Pointer)
                            .onClick {
                                onShareClick(writing)
                            }
                    )
                }
                Column {
                    Row { Text("${text["createdAt"]!!}: $createdAt") }
                    Row {
                        if (publishedAt != null) {
                            Text("${text["published"]}: $publishedAt")
                        } else if (writing.status == Status.PUBLISHED) {
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
                Button(
                    enabled = enabled,
                    onClick = {
                        when (writing.type) {
                            Type.ARTICLE -> {
                                onReadArticle(writing.title, writing.slug)
                            }
                            Type.BOOK -> {
                                writing.salesLink?.let { url ->
                                    window.open(url, "_blank")  // open book sales page
                                }
                            }
                        }
                    }
                ) {
                    Text(
                        if (writing.type == Type.ARTICLE) {
                            if (writing.status == Status.PUBLISHED) {
                                text["readArticle"]!!
                            } else {
                                text["comingSoon"]!!
                            }
                        } else {
                            if (writing.salesLink.isNullOrBlank()) {
                                text["comingSoon"]!!
                            } else {
                                text["buyBook"]!!
                            }
                        }
                    )
                }

            }
        }
    }

    suspend fun toggleLike(writing: WritingEntry) {
        val nowLiked = !liked.value
        liked.value = nowLiked

        // Persist locally
        localStorage.setItem("liked_${writing.slug}", nowLiked.toString())

        // Update backend (fire & forget)
        scope.launch {
            try {
                if (nowLiked) {
                    // Like
                    ApiClient.like(writing.slug)
                } else {
                    // Unlike
                    ApiClient.unlike(writing.slug)
                }
            } catch (e: Exception) {
                console.error("Failed to update like status", e)
            }
        }
    }
}

private val writingText = mapOf(
    "en" to mapOf(
        "createdAt" to "Created",
        "published" to "Published",
        "lastUpdated" to "Updated",
        "readArticle" to "Read Article",
        "comingSoon" to "Coming Soon",
        "buyBook" to "Buy Book",
        "featured" to "Featured",
        "views" to "Views",
        "comments" to "Comments",
        "likes" to "Likes",
        "notPublished" to "Not published yet",
        "shares" to "Shares"
    ),
    "es" to mapOf(
        "createdAt" to "Creado",
        "published" to "Publicado",
        "lastUpdated" to "Actualización",
        "readArticle" to "Leer artículo",
        "comingSoon" to "Próximamente",
        "buyBook" to "Comprar libro",
        "featured" to "Destacado",
        "views" to "Vistas",
        "comments" to "Comentarios",
        "likes" to "Me gusta",
        "notPublished" to "Aún no publicado",
        "shares" to "Compartidos"
    )
)

