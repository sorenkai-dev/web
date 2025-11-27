package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.components.data.model.WritingEntry
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.icons.fa.FaHeart
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem

@Composable
fun LikeIcon(
    liked: MutableState<Boolean>,
    scope: CoroutineScope,
    likes: MutableState<Int>,
    likeKey: String,
    writing: WritingEntry
) {
    FaHeart(
        size = IconSize.LG,
        modifier = Modifier
            .margin(1.cssRem)
            .color(color = Colors.Red)
            .cursor(Cursor.Pointer)
            .onClick {
                val newLiked = !liked.value
                liked.value = newLiked
                localStorage.setItem(likeKey, newLiked.toString())

                // Optimistic UI update
                if (newLiked) {
                    likes.value += 1
                    scope.launch { ApiClient.like(writing.slug) }
                } else {
                    likes.value = maxOf(0, likes.value - 1)
                    scope.launch { ApiClient.unlike(writing.slug) }
                }
            },
        style = if (liked.value) IconStyle.FILLED else IconStyle.OUTLINE
    )

}
