package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun AboutHeroEnQuote(breakpoint: Breakpoint) {
    val size = if (breakpoint <= Breakpoint.SM) 1.25.cssRem
    else if (breakpoint <= Breakpoint.MD) 1.5.cssRem
    else 1.75.cssRem

    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            content = {
                Text("Between code and consciousness lies a question; not of what we can build, but who we " +
                    "become when we build it." )
            },
            citation = "— Soren Kai",
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
}

@Composable
fun AboutContentEn(breakpoint: Breakpoint) {
    LeadParagraph(breakpoint) {
        Text("I write and engineer from the same impulse: to understand what it means to be human in an age " +
            "defined by algorithms. Each line of code, each sentence, is an attempt to bring the abstract into " +
            "alignment with the emotional; to reconcile logic and empathy, structure and soul." )
    }

    SectionHeader("Why I Write, Why I Build", breakpoint)

    SectionParagraph(
        breakpoint,
        content = { Text("I’ve spent my life at the intersection of two worlds; the precise architectures " +
            "of technology and the boundless ambiguity of language. Both, in their own way, are systems for making " +
            "meaning. Both are acts of creation.") }
    )

    SectionParagraph(breakpoint) {
        Text("Writing became my way to feel the pulse of ideas. Engineering became my way to give them form. " +
            "Together, they form a single pursuit: building systems that honor the human stories inside them.")
    }

    SectionParagraph(breakpoint) {
        Text("It was this pursuit that led to Kindred, an ecosystem of platforms and ideas rooted in empathy, " +
            "authenticity, and belonging; a response to the toxicity that too often defines our digital lives.")
    }

    SectionHeader("Language & Logic", breakpoint)

    Row( modifier = Modifier.fillMaxWidth()) {
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
                ) { Text("Writing as Reflection") }
            }
            Text("Through essays like Canary in the Data Mine and The Mirror and the Machine, I explore how " +
                "technology reshapes identity; how recognition, power, and belonging are rewritten by code. Writing " +
                "allows me to step back and ask not how machines think, but why we want them to.")
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
                ) { Text("Technology as Expression") }
            }
            Text("As an engineer, I build platforms and systems that mirror these questions in practice: " +
                "KindredCircl for authentic connection. Kindred Labs for ethical AI research. Each project is an experiment in aligning architecture with empathy. Proof that technology can be both rigorous and humane.")
        }

    }


    SectionHeader("Mission — Humanity at the Center", breakpoint)


    SectionParagraph(breakpoint) {
        BlockQuote(
            { Text("To design systems, linguistic, social, and technical, that empower authentic " +
                "connection and cultivate belonging.") }
        )
        Text("Technology should not erode our humanity; it should amplify it. My mission is to build tools " +
            "that serve the human spirit; that remind us we belong not because of algorithms, but because of the " +
            "stories we share.")
    }

    SectionHeader("The Kindred Philosophy", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Kindred is more than a network of platforms; it’s a philosophy. It’s a belief that empathy is " +
            "infrastructure, and that code can be written with conscience.")
    }

    SectionParagraph(breakpoint) {
        Text("From KindredCircl’s kinship-driven social fabric to KindredAI’s ethical learning framework, " +
            "every project is an act of resistance; against isolation, cynicism, and digital indifference. Together, " +
            "they form a living experiment in what it means to build technology for and with humanity.")
    }

    SectionHeader("A Call to Belong", breakpoint)

    SectionParagraph(breakpoint) {
        BlockQuote(
            {Text("If the story of humanity and machine is still being written, may we write it with " +
                "empathy. May we remember that every algorithm begins with intention; and that intention begins with us.")},
            citation = "— Soren Kai"
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Link(
                path = "/writings",
                text = "Explore Writings",
                modifier = LinkStyle.toModifier()
            )
            Link(
                path = "/projects",
                text = "View Projects",
                modifier = LinkStyle.toModifier()
            )
        }
    }
}

