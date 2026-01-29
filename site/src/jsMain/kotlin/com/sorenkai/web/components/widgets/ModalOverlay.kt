package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.sorenkai.web.ModalExternalCardStyle
import com.sorenkai.web.ModalInternalCardStyle
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLBodyElement

@Composable
fun ModalOverlay(
    title: String? = null,
    onClose: () -> Unit,
    bottomBar: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    LockBodyScroll()
    Box(
        ModalExternalCardStyle.toModifier()
            .onClick { onClose() },
        Alignment.Center

    ) {
        Box(
            ModalInternalCardStyle.toModifier()
                .onClick { /* stop click propagation */ }
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .justifyContent(JustifyContent.SpaceBetween)
                        .alignItems(AlignItems.Center)
                        .margin(bottom = 16.px)
                ) {
                    title?.let { Text(it) }
                    FaXmark(Modifier.cursor(Cursor.Pointer).onClick { onClose() })
                }
                Column(
                    Modifier.weight(1f)
                        .fillMaxWidth()
                        .overflow(
                            overflowX = Overflow.Clip,
                            overflowY = Overflow.Auto
                        )
                ) {
                    content()
                }
                Box(
                    Modifier.fillMaxWidth()
                        .backgroundColor(ColorMode.current.toSitePalette().nearBackground) // Ensure it hides text behind it
                        .zIndex(20)
                        .onClick { event -> event.stopPropagation() }
                ) {
                    bottomBar()
                }
            }
        }
    }
}

@Composable
fun LockBodyScroll() {
    DisposableEffect(Unit) { val body = document.body as HTMLBodyElement?

        // --- FIX HERE: Use the explicit scope for the DOM function ---
        (body?.style as? Any)?.asDynamic()?.overflow = "hidden"

        onDispose {
            // --- FIX HERE: Use the explicit scope for the DOM function ---
            (body?.style as? Any)?.asDynamic()?.overflow = "auto"
        }
    }
}
