package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.ProjectsTable
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.en.data.ProjectDataEn
import com.sorenkai.web.es.data.ProjectDataEs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.dom.Text

@Composable
fun ProjectContent(
    lang: String,
    breakpoint: Breakpoint
) {
    val data =
        when (lang) {
            "es" -> ProjectDataEs
            else -> ProjectDataEn
        }

    SectionHeader(data.section1header, breakpoint)
    SectionParagraph(breakpoint) { Text(data.section1paragraph) }

    SectionHeader(data.section2header, breakpoint)
    ProjectsTable(data.section2table)

    SectionHeader(data.section3header, breakpoint)
    SectionParagraph(breakpoint) { Text(data.section3paragraph2) }
    ProjectsTable(data.section3table)
    SectionParagraph(breakpoint) { Text(data.section3paragraph2) }
}
