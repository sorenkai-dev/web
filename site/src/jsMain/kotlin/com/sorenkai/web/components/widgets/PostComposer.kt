package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.ButtonStyle
import com.sorenkai.web.components.data.model.community.discussions.ComposerMode
import com.varabyte.kobweb.compose.css.disabled
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun PostComposer(
    modifier: Modifier = Modifier,
    lang: String,
    placeholder: String,
    enabled: Boolean = true,
    initialText: String = "",
    mode: ComposerMode = ComposerMode.CREATE,
    onSubmit: (String) -> Unit,
    onCancel: (() -> Unit)? = null
) {
    val submit = when {
        mode == ComposerMode.EDIT && lang == "es" -> "Guardar"
        mode == ComposerMode.EDIT -> "Save"
        lang == "es" -> "Publicar"
        else -> "Post"
    }
    val cancel = when (lang) {
        "es" -> "Cancelar"
        else -> "Cancel"
    }
    val clear = when (lang) {
        "es" -> "Borrar"
        else -> "Clear"
    }
    var text by remember(initialText) { mutableStateOf(initialText) }

    Column(
        Modifier.gap(1.cssRem)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextArea(
            value = text,
            attrs = Modifier
                .fillMaxWidth()
                .width(75.percent)
                .height(5.cssRem)
                .then(modifier)
                .toAttrs {
                    if (!enabled) disabled()
                    attr("placeholder", placeholder)
                    onInput { evt ->
                        // Compose HTML input events expose value via target.value
                        text = evt.target.value
                    }

                    onKeyDown { evt ->
                        val isSubmitCombo =
                            (evt.ctrlKey || evt.metaKey) && evt.key == "Enter"

                        val isEscape = evt.key == "Escape"

                        if (isSubmitCombo && enabled && text.isNotBlank()) {
                            evt.preventDefault()
                            onSubmit(text)
                            if (mode == ComposerMode.CREATE) {
                                text = ""
                            }
                        }

                        if (
                            isEscape &&
                            (mode == ComposerMode.EDIT || mode == ComposerMode.COMMENT) &&
                            onCancel != null
                        ) {
                            evt.preventDefault()
                            onCancel()
                        }
                    }
                }
        )

        Row(
            modifier = Modifier.fillMaxWidth(70.percent),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                attrs = ButtonStyle.toModifier()
                    .id("clear-button")
                    .toAttrs {
                        if (text.isBlank()) disabled()
                        onClick { text = "" }
                    }
            ) {
                Text(clear)
            }

            if ((mode == ComposerMode.EDIT || mode == ComposerMode.COMMENT) && onCancel != null) {
                Button(
                    attrs = ButtonStyle.toModifier()
                        .id("cancel-button")
                        .toAttrs {
                            onClick { onCancel() }
                        }
                ) {
                    Text(cancel)
                }
            }

            Button(
                attrs = ButtonStyle.toModifier()
                    .id("submit-button")
                    .toAttrs {
                        if (!enabled || text.isBlank()) disabled()
                        onClick {
                            onSubmit(text)
                            if (mode == ComposerMode.CREATE) {
                                text = ""
                            }
                        }
                    }
            ) {
                Text(submit)
            }
        }
    }
}
