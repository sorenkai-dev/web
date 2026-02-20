package com.sorenkai.web.components.widgets.header


import androidx.compose.runtime.Composable
import com.sorenkai.web.components.util.formatInstantToHumanReadable
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlin.time.Instant
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Text

@Composable
fun DiscussionItemHeader(
    discussionId: String,
    highlightedId: String?,
    username: String,
    createdAt: Instant
) {
    val isHighlighted = highlightedId == discussionId

    Row(
        modifier = Modifier
            .width(100.percent)
            .padding(leftRight = 1.5.cssRem, )
            .backgroundColor(
                if (isHighlighted) {
                    ColorMode.current.toSitePalette().brand.accent
                } else {
                    ColorMode.current.toSitePalette().nearBackground
                }
            )
            .transition(
                Transition.of(
                    "background-color",
                    300.ms,
                    TransitionTimingFunction.EaseInOut
                )
            )
            .borderRadius(0.5.cssRem),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(username)
        Spacer()
        Text(formatInstantToHumanReadable(createdAt))
    }
}
