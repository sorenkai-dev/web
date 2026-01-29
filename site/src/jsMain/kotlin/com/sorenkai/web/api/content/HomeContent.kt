package com.sorenkai.web.api.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.en.data.HomeDataEn
import com.sorenkai.web.es.data.HomeDataEs
import com.sorenkai.web.util.RichText
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun HomeContent(
    lang: String,
    breakpoint: Breakpoint
) {
    val data =
        when (lang) {
            "es" -> HomeDataEs
            else -> HomeDataEn
        }
    val size = if (breakpoint <= Breakpoint.SM) {
        1.25.cssRem
    } else  if (breakpoint <= Breakpoint.MD) {
        1.5.cssRem
    } else {
        1.75.cssRem
    }

    Row(
        modifier = BlockquoteCardStyle.toModifier()
            .fillMaxWidth(),
    ) {
        BlockQuote(
            { Text(data.heroQuote) },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size),
            citation = data.heroQuoteAuthor,
        )
    }

    LeadParagraph(breakpoint) { Text(data.leadParagraph) }

    SectionHeader(data.section1Title, breakpoint)
    SectionParagraph(breakpoint) { Text(data.section1Paragraph) }

    SectionHeader(data.section2Title, breakpoint)
    SectionParagraph(breakpoint) { RichText(data.section2Paragraph) }

    SectionHeader(data.section3Title, breakpoint)
    SectionParagraph(breakpoint) { Text(data.section3Paragraph) }
}
