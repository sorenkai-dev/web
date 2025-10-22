package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.components.widgets.socialLinks
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontStyle
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun ContactContent(breakpoint: Breakpoint) {
    SectionHeader("Get In Touch", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Whether you’re a reader moved by an essay, a technologist exploring ethics in AI, " +
            "or simply curious about the work, I’d love to hear from you.")
    }

    SectionParagraph(breakpoint) {
        Text("Let's build something thoughtful together.")
    }

    SectionHeader("Email", breakpoint)

    Link(
        path = "mailto:soren@sorenkai.com",
        text = "Email me",
        LinkStyle.toModifier()
    )

    SectionHeader("Connect", breakpoint, modifier = Modifier.padding(top = 2.cssRem))

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 2.cssRem, bottom = 4.cssRem, leftRight = 10.cssRem),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        socialLinks(

        )
    }

    SectionParagraph(breakpoint, modifier = Modifier.fontStyle(FontStyle.Italic).opacity(0.85)) {
        Text("The most meaningful ideas begin with a simple conversation.")
    }
}
