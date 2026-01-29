package com.sorenkai.web.components.widgets.buttons

import androidx.compose.runtime.Composable
import com.sorenkai.web.BackToTopButtonStyle
import com.sorenkai.web.ButtonStyle
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.ArrowUpIcon
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.A

@Composable
fun backToTopButton(anchorId: String = "top", visible: Boolean = true) {
    if (!visible) return
    A(
        href = "#$anchorId",
        attrs = ButtonStyle.toModifier()
            .then(BackToTopButtonStyle.toModifier())
            .toAttrs {
                attr("aria-label", "Back to top")
                attr("title", "Back to top")
            }
    ) {
        ArrowUpIcon()
    }
}
