package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.ProjectsTable
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun ProjectsContentEn(breakpoint: Breakpoint) {

    SectionHeader("The Kindred", breakpoint)

    SectionParagraph(breakpoint) {
        Text("The Kindred is more than a network of platforms. It’s an ecosystem of ideas designed to cultivate " +
            "empathy, authenticity, and belonging.")
    }

    SectionParagraph(breakpoint) {
        Text("Each project within it serves a different purpose, but all share a single intention: to build " +
            "technology that reflects our humanity instead of eroding it.")
    }

    SectionHeader("Public Platforms", breakpoint)

    ProjectsTable(
        projects = listOf(
            "KindredCircl" to "A social platform for authentic connection; prioritizing empathy, creativity, and " +
                "meaningful dialogue over algorithmic engagement.",
            "KinChat" to "A private messaging experience within KindredCircl, designed for real conversation; focused, " +
                "intimate, and free from digital noise.",
            "OpenCircls" to "A community-driven space for shared interests and collective belonging; where dialogue " +
                "fosters understanding across perspectives.",
            "LumiKona" to "A creative platform celebrating artistry and story; connecting writers, photographers, and " +
                "dreamers through light and expression."
        )
    )

    SectionHeader("Kindred Labs", breakpoint)

    SectionParagraph(breakpoint) {
            Text(
                "The research and development arm of The Kindred. A studio for ethical AI, digital philosophy, " +
                    "and human-centered system design. Kindred Labs explores how technology can embody empathy, moral " +
                    "reasoning, and transparency, forming the foundation for a new generation of conscious systems."
            )
    }

    ProjectsTable(
        projects = listOf(
            "KindredAI" to "A modular AI initiative exploring reflection, emotion, and learning; raised through " +
                "transparent governance and ethical boundaries.",
            "The Codex" to "The living documentation and conscience of Kindred Labs. Preserving intent, context, " +
                "and moral integrity across every project."
        ),
        modifier = Modifier.margin(top = 2.cssRem)
    )

    SectionParagraph(
        breakpoint,
        modifier = Modifier.margin(topBottom = 4.cssRem)
    ) {
        Text("Each of these efforts is a fragment of a larger whole; a shared experiment in building a more " +
            "compassionate digital world. " +
            "Together, they form The Kindred: not as a product, but as a promise.")
    }

}

