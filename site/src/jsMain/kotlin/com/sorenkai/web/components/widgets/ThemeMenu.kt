package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.RadioGroup
import org.jetbrains.compose.web.dom.RadioInput
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun ThemeMenu(
    lang: String
) {
    var colorMode by ColorMode.currentState
    val themeOptions = listOf("system", "light", "dark")
    val stored = localStorage.getItem("theme") ?: "system"
    val selectedOption = remember { mutableStateOf(stored) }

    val text = themeLabels[lang] ?: error("Language not supported: $lang")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .color(ColorMode.current.toSitePalette().brand.accent)
            .gap(0.75.cssRem)
    ) {
        Text(text["theme"]!!)
        RadioGroup(
            checkedValue = selectedOption.value,
            name = "theme-selector",
            content = {
                themeOptions.forEach { option ->
                    Row(
                        modifier = Modifier.cursor(Cursor.Pointer).gap(1.cssRem),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioInput(
                            value = option,
                            id = option,
                            attrs = Modifier
                                .cursor(Cursor.Pointer)
                                .onClick {
                                    selectedOption.value = option
                                    colorMode = when (selectedOption.value) {
                                        "dark" -> ColorMode.DARK
                                        "light" -> ColorMode.LIGHT
                                        else -> ColorMode.systemPreference
                                    }
                                    localStorage.setItem("theme", option)
                                }
                                .toAttrs()
                        )
                        Text(text[option]!!)
                    }
                }
            }
        )
    }
}

private val themeLabels = mapOf(
    "es" to mapOf(
        "theme" to "Tema",
        "system" to "Sistema",
        "light" to "Claro",
        "dark" to "Oscuro"
    ),
    "en" to mapOf(
        "theme" to "Theme",
        "system" to "System",
        "light" to "Light",
        "dark" to "Dark"
    )
)
