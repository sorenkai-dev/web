package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.components.widgets.icons.socialLinks
import com.sorenkai.web.en.data.ContactContentEn
import com.sorenkai.web.es.data.ContactContentEs
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun ContactContent(
    breakpoint: Breakpoint,
    lang: String,
) {
    val data =
        when (lang) {
            "es" -> ContactContentEs
            else -> ContactContentEn
        }

    SectionHeader(data.section1Header, breakpoint)
    SectionParagraph(breakpoint) { Text(data.section1Paragraph1) }
    SectionParagraph(breakpoint) { Text(data.section1Paragraph2) }

    SectionHeader(data.section2Header, breakpoint)
    Link(
        path = "mailto:soren@sorenkai.com",
        text = data.section2email,
        LinkStyle.toModifier()
    )

    SectionHeader(data.section3Header, breakpoint)
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 2.cssRem, bottom = 4.cssRem, leftRight = 10.cssRem),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        socialLinks()
    }
    SectionParagraph(breakpoint) { Text(data.section3Paragraph) }
}
