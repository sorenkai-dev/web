package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.en.data.AboutDataEn
import com.sorenkai.web.es.data.AboutDataEs
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun AboutContent(
    lang: String,
    breakpoint: Breakpoint
) {
    val data =
        when (lang) {
            "es" -> AboutDataEs
            else -> AboutDataEn
        }
    val size = if (breakpoint <= Breakpoint.SM) {
        1.25.cssRem
    } else if (breakpoint <= Breakpoint.MD) {
        1.5.cssRem
    } else {
        1.75.cssRem
    }

    Row(
        modifier = BlockquoteCardStyle.toModifier()
            .fillMaxWidth(),
    ) {
        BlockQuote(
            { Text(data.aboutQuote) },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size),
            citation = data.aboutQuoteAuthor,
        )
    }

    LeadParagraph(breakpoint) { Text(data.aboutLeadParagraph) }

    SectionHeader(data.aboutSection1Title, breakpoint)
    SectionParagraph(breakpoint) { Text(data.aboutSection1Paragraph) }

    SectionHeader(data.aboutSection2.aboutSection2Title, breakpoint)
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(leftRight = 2.cssRem)
        ) {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) {
                Span(
                    attrs = Modifier
                        .fontWeight(700)
                        .toAttrs()
                ) {
                    Text(data.aboutSection2.column1Title)
                }
            }
            SectionParagraph(breakpoint) { Text(data.aboutSection2.column1Paragraph) }
        }
        Column(
            modifier = Modifier.padding(leftRight = 2.cssRem)
        ) {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) {
                Span(
                    attrs = Modifier
                        .fontWeight(700)
                        .toAttrs()
                ) {
                    Text(data.aboutSection2.column2Title)
                }
            }
            SectionParagraph(breakpoint) { Text(data.aboutSection2.column2Paragraph) }
        }
    }

    SectionHeader(data.aboutSection3Title, breakpoint)
    SectionParagraph(breakpoint) {
        BlockQuote({ Text(data.aboutSection3Quote) })
        Text(data.aboutSection3Paragraph)
    }

    SectionHeader(data.aboutSection4Title, breakpoint)
    SectionParagraph(breakpoint) { Text(data.aboutSection4Paragraph) }

    SectionHeader(data.aboutSection5Title, breakpoint)
    SectionParagraph(breakpoint) {
        BlockQuote({ Text(data.aboutSection5Quote) })
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Link(
                path = "writings",
                text = data.exploreLink,
                LinkStyle.toModifier()
            )
            Link(
                path = "projects",
                text = data.projectsLink,
                LinkStyle.toModifier()
            )
        }
    }
}
