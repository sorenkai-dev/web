package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sorenkai.web.CategoryStyle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem

@Composable
fun CategoryFilterBar(
    breakpoint: Breakpoint,
    activeCategory: String?,
    onSelect: (String?) -> Unit,
    lang: String,
) {
    val categories = listOf("all", "theme", "topic", "series", "concept", "project", "type")
    val labels = categoryLabels[lang] ?: error("Language not supported: en")
    val windowWidth = rememberWindowWidth()
    val splitLayout = windowWidth in 402..453
    val otherLabels = categories.drop(1)

    if (splitLayout) {
        Row(
            CategoryStyle.toModifier()
                .margin(bottom = 0.cssRem),
            horizontalArrangement = Arrangement.Center
        ) {
            CategoryChip(
                breakpoint = breakpoint,
                category = "all",
                categoryLabel = labels["all"]!!
            ) {
                onSelect(if (activeCategory == "all") null else "all")
            }
        }
        Row(
        CategoryStyle.toModifier()
            .padding(top = 0.5.cssRem, bottom = 2.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            otherLabels.forEach { category ->
                CategoryChip(
                    breakpoint = breakpoint,
                    category = category,
                    categoryLabel = labels[category]!!
                ) {
                    onSelect(if (category == activeCategory) null else category)
                }
            }
        }
    } else {
        Row(
            CategoryStyle.toModifier()
            .padding(top = 1.cssRem, bottom = 2.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categories.forEach { category ->
                CategoryChip(
                    breakpoint = breakpoint,
                    category = category,
                    categoryLabel = labels[category]!!
                ) {
                    onSelect(if (category == activeCategory) null else category)
                }
            }
        }
    }
}

@Composable
fun rememberWindowWidth(): Int {
    val widthState = remember { mutableStateOf(window.innerWidth) }

    DisposableEffect(Unit) {
        val listener: (org.w3c.dom.events.Event) -> Unit = {
            widthState.value = window.innerWidth
        }

        window.addEventListener("resize", listener)

        onDispose {
            window.removeEventListener("resize", listener)
        }
    }

    return widthState.value
}


private val categoryLabels: Map<String, Map<String, String>> = mapOf(
    "en" to mapOf(
        "all" to "#all",
        "theme" to "#themes",
        "topic" to "#topics",
        "concept" to "#concepts",
        "series" to "#series",
        "project" to "#projects",
        "type" to "#books",
    ),
    "es" to mapOf(
        "all" to "#todo",
        "theme" to "#temas",
        "topic" to "#tópicos",
        "concept" to "#conceptos",
        "series" to "#la serie",
        "project" to "#proyectos",
        "type" to "#libros"
    )
)
