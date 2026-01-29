package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.web.events.SyntheticMouseEvent
import com.sorenkai.web.AuthNoticeStyle
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun AuthNotice(
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(10000)
        onDismiss()
    }
    Box(
        modifier = AuthNoticeStyle.toModifier(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(message)
            Spacer()
            SpanText(
                actionLabel,
                modifier = Modifier
                    .color(Colors.Teal)
                    .fontWeight(FontWeight.SemiBold)
                    .fontStyle(FontStyle.Italic)
                    .cursor(Cursor.Pointer)
                    .onClick { _: SyntheticMouseEvent ->
                        onAction()
                    }
            )
        }
    }
}
