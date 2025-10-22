package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Em
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Text

@Composable
fun HomeHeroEnQuote(breakpoint: Breakpoint) {
    val size = if (breakpoint <= Breakpoint.SM) 1.25.cssRem
            else  if (breakpoint <= Breakpoint.MD) 1.5.cssRem
            else 1.75.cssRem
    Div (
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            {
                Text("“Progress should not be measured only by what machines can do, but by how much more " +
                    "fully we remain human in their presence.")
            },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size),
            citation = "-- Soren Kai"
        )

    }
}

@Composable
fun HomeContentEn(breakpoint: Breakpoint) {

    LeadParagraph(breakpoint) {
        Text("Soren Kai is a writer and technologist exploring how technology shapes human connection, " +
            "identity, and belonging. With one foot in code and the other in prose, he examines not just artificial " +
            "intelligence but the broader arc of tools that transform how we live and relate to one another. His work " +
            "blends technical fluency with cultural reflection, always asking what it means to remain fully human in a " +
            "world remade by machines.")
    }

    SectionHeader("Culture & Belonging", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Technology doesn’t just change what we do; it reshapes how we see ourselves and one another. I " +
            "explore how digital life influences identity, presence, and connection. I think about how design choices " +
            "in the systems we build can either strengthen belonging or fracture it.")
    }

    SectionHeader("A.I. & Technology", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Artificial intelligence and mobile platforms are no longer accessories; they are woven into daily " +
            "life. I write about the cultural impact of these systems, and I build and study them directly, from " +
            "backend architectures to AI-driven services. That dual perspective helps me ask not just")
        I { Text("what can we build") }
        Text(", but ")
        Em { Text("what should we build and how can it help my community and society at large.") }
    }

    SectionHeader("LGBTQ+ Voices", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Belonging is also personal. As a member of the LGBTQ community, I write about our stories, " +
            "struggles, and resilience. In my technical work, I bring the same values, ensuring the platforms and tools " +
            "I help create don’t just function, but make space for authenticity and visibility.")
    }
}
