package com.sorenkai.web.components.widgets.buttons

import androidx.compose.runtime.Composable
import com.sorenkai.web.ButtonStyle
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingActionButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier =
            ButtonStyle.toModifier()
                .color(Colors.White)
                .then(modifier),
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Text(label)
    }
}
