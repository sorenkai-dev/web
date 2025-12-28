package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.RadioGroup
import org.jetbrains.compose.web.dom.RadioInput
import org.jetbrains.compose.web.dom.Text

@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun LanguageMenu() {
    val initialLang = localStorage.getItem("lang") ?: "en"
    val selectedLang = remember { mutableStateOf(initialLang) }
    val text = languages[initialLang] ?: error("Language not supported: $initialLang")

    fun switchTo(lang: String) {
        val current = window.location.pathname

        // Normalize path change
        val newPath = when {
            current.startsWith("/en/") -> current.replaceFirst("/en/", "/$lang/")
            current.startsWith("/es/") -> current.replaceFirst("/es/", "/$lang/")
            current == "/en" -> "/$lang"
            current == "/es" -> "/$lang"
            else -> "/$lang$current"  // fallback for root or unexpected patterns
        }

        localStorage.setItem("lang", lang)
        window.location.href = newPath
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gap(0.75.cssRem)
    ) {
        Text(text["language"]!!)
        RadioGroup(
            checkedValue = selectedLang.value,
            name = "language-selector",
            content = {
                Row(
                    modifier = Modifier.cursor(Cursor.Pointer).gap(1.cssRem),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioInput(
                        value = "en",
                        id = "lang_en",
                        attrs = Modifier
                            .cursor(Cursor.Pointer)
                            .onClick {
                                selectedLang.value = "en"
                                switchTo("en")
                            }
                            .toAttrs()
                    )
                    Text("English")
                }
                Row(
                    modifier = Modifier.cursor(Cursor.Pointer).gap(1.cssRem),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioInput(
                        value = "es",
                        id = "lang_es",
                        attrs = Modifier
                            .cursor(Cursor.Pointer)
                            .onClick {
                                selectedLang.value = "es"
                                switchTo("es")
                            }
                            .toAttrs()
                    )
                    Text("Español")
                }
            }
        )
    }
}

private val languages =
    mapOf(
        "en" to mapOf(
            "language" to "Language",
        ),
        "es" to mapOf(
            "language" to "Idioma",
        )
    )
