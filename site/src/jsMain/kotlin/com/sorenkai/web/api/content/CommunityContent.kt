package com.sorenkai.web.api.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.en.data.CommunityDataEn
import com.sorenkai.web.es.data.CommunityDataEs
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun CommunityContent(
    lang: String,
    breakpoint: Breakpoint,
) {
    val data =
        when (lang) {
            "es" -> CommunityDataEs
            else -> CommunityDataEn
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
            { Text(data.communityQuote) },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size),
            citation = data.communityQuoteAuthor,
        )
    }

    LeadParagraph(breakpoint) { Text(data.communityLeadParagraph) }

    SectionHeader(data.ctaHeading, breakpoint)
    SectionParagraph(breakpoint) {
        Text(data.ctaParagraph)
        Row(
            modifier = Modifier
                .padding(top = 2.cssRem)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Link(
                path = "mailto:notify@sorenkai.com?subject=Community Opening Notification&body=Notify me!",
                text = data.ctaLink
            )
        }

    }

    SectionHeader(data.policyHeading, breakpoint)
    Row(
        modifier = Modifier
            .padding(top = 2.cssRem)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Link(
            path = "policies/community",
            text = data.guidelinesLink
        )
        Link(
            path = "policies/terms",
            text = data.tosLink
        )
        Link(
            path = "policies/privacy",
            text = data.privacyLink
        )
    }
}
