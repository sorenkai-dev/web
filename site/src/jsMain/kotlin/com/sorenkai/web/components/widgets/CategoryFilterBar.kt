package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import org.jetbrains.compose.web.css.cssRem

@Composable
fun CategoryFilterBar(
    activeCategory: String?,
    onSelect: (String?) -> Unit,
    lang: String,
) {
    val categories = listOf("all", "theme", "topic", "series", "concept", "project", "type")
    val labels = categoryLabels[lang] ?: error("Language not supported: en")

    Row(
        Modifier
            .fillMaxWidth()
//            .gap(0.75.cssRem)
            .padding(bottom = 2.cssRem)
            .margin(bottom = 2.cssRem),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        categories.forEach { category ->
            CategoryChip(
                category = category,
                categoryLabel = labels[category]!!
            ) {
                onSelect(if (category == activeCategory) null else category)
            }
        }
    }
}

private val categoryLabels: Map<String, Map<String, String>> = mapOf(
    "en" to mapOf(
        "all" to "All",
        "theme" to "Themes",
        "topic" to "Topics",
        "concept" to "Concepts",
        "series" to "Series",
        "project" to "Projects",
        "type" to "Books",
    ),
    "es" to mapOf(
        "all" to "Todo",
        "theme" to "Temas",
        "topic" to "Tópicos",
        "concept" to "Conceptos",
        "series" to "Series",
        "project" to "Proyectos",
        "type" to "Libros"
    )
)
