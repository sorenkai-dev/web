package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.en.data.GuidelinesDataEn
import com.sorenkai.web.es.data.GuidelinesDataEs
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.jetbrains.skia.FontWeight

@Composable
fun GuidelinesContent(
    lang: String,
    breakpoint: Breakpoint,
) {
    val data =
        when (lang) {
            "es" -> GuidelinesDataEs
            else -> GuidelinesDataEn
        }
    val prohibited = data.prohibitedContent
    val version =
        when (lang){
            "es" -> "Versión 1.0.0"
            else -> "Version: 1.0.0" }
    val date =
        when (lang) {
            "es" -> "Establecido: 31 de enero de 2025"
            else -> "Established: January 31, 2025"
        }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        H1(
            attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
        ) {
            Text(data.title)
        }

        LeadParagraph(breakpoint) {
            Text(data.openingParagraph)
        }

        SectionHeader(data.section1heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section1paragraph) }

        SectionHeader(data.section2heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section2paragraph1) }
        Ul {
            data.section2ul.forEach { li -> Li { Text(li) } }
        }
        SectionParagraph(breakpoint) { Text(data.section2paragraph2) }

        SectionHeader(data.section3heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section3paragraph) }

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(leftRight = 2.cssRem)
        )
        {
            SectionHeader(
                prohibited.section1heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            Ul {
                prohibited.uList1.forEach { li -> Li { Text(li) } }
            }

            SectionHeader(prohibited.section2heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            SectionParagraph(breakpoint) { Text(prohibited.section2paragraph1) }
            Ul {
                prohibited.section2Ulist.forEach { li -> Li { Text(li) } }
            }
            SectionParagraph(breakpoint) { Text(prohibited.section2paragraph2) }

            SectionHeader(prohibited.section3heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            Ul {
                prohibited.uList3.forEach { li -> Li { Text(li) } }
            }

            SectionHeader(prohibited.section4heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            SectionParagraph(breakpoint) { Text(prohibited.section4paragraph1) }
            Ul {
                prohibited.section4UL1.forEach { li -> Li { Text(li) } }
            }
            SectionParagraph(breakpoint) { Text(prohibited.section4paragraph2) }
            Ul {
                prohibited.section4UL2.forEach { li -> Li { Text(li) } }
            }
            SectionParagraph(breakpoint) { Text(prohibited.section4paragraph3) }

            SectionHeader(prohibited.section5heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            Ul {
                prohibited.section5UL.forEach { li -> Li { Text(li) } }
            }

            SectionHeader(prohibited.section6heading,
                breakpoint,
                modifier = Modifier.color(Colors.Red)
            )
            Ul {
                prohibited.section6UL.forEach { li -> Li { Text(li) } }
            }
        }

        SectionHeader(data.section4heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section4paragraph) }

        SectionHeader(data.section5heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section5paragraph1) }
        Ul {
            data.section5UL1.forEach { li -> Li { Text(li) } }
        }
        SectionParagraph(breakpoint) { Text(data.section5paragraph2) }
        Ul {
            data.section5UL2.forEach { li -> Li { Text(li) } }
        }
        SectionParagraph(breakpoint) { Text(data.section5paragraph3) }

        SectionHeader(data.section6heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section6paragraph) }

        SectionHeader(data.section7heading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.section7paragraph1) }
        Ul {
            data.section7UL.forEach { li -> Li { Text(li) } }
        }
        SectionParagraph(breakpoint) { Text(data.section7paragraph2) }

        SectionHeader(data.closingHeading, breakpoint)
        SectionParagraph(breakpoint) { Text(data.closingParagraph1) }
        SectionParagraph(
            breakpoint,
            modifier = Modifier
                .fontWeight(FontWeight.SEMI_BOLD)
                .fontStyle(FontStyle.Italic)
        ) { Text(data.closingParagraph2) }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(topBottom = 2.cssRem)
                .fontSize(0.9.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SpanText(version)
            SpanText(date)
        }
    }
}
