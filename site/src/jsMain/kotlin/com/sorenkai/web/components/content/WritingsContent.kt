package com.sorenkai.web.components.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.WritingItem
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingsHeroQuote(breakpoint: Breakpoint) {
    val size = if (breakpoint <= Breakpoint.SM) 1.25.cssRem
    else if (breakpoint <= Breakpoint.MD) 1.5.cssRem
    else 1.75.cssRem
    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            content = {
                Text("“The fact of storytelling hints at a fundamental human unease, hints at human imperfection. Where there is perfection there is no story to tell.”")
            },
            citation = "— Ben Okri",
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
}

@Composable
fun WritingContent(breakpoint: Breakpoint) {
    LeadParagraph(breakpoint) {
        Text(
            "This page is a portfolio of my published and ongoing work. It brings together essays, reflections, and contributions that explore the intersections of technology, culture, and belonging. Some pieces are deeply technical, others more reflective, but all are united by the same question: what does it mean to remain fully human in a world shaped by machines? Each entry includes a short synopsis and a link to read or purchase the full work."
            )
    }

    val writings = listOf(
        WritingEntry(
            title = "Out-Belonging the Machines",
            publication = "AI: Thriving in a World with Smart Machines and Robots",
            date = "January 2026",
            synopsis = "An exploration of the post-phone era, where AI wearables blur the line between connection and control — and how belonging becomes a survival skill. This chapter is a cornerstone of the philosophy I explore in my work.",
            purchase = true
        ),
        WritingEntry(
            title = "Unlocking Asynchronous Power: A Journey into Kotlin Coroutines",
            publication = "Medium",
            date = "August 29, 2023",
            synopsis = "A practical guide to Kotlin coroutines, demonstrating how elegant code can simplify complex concurrency and free developers to focus on building features that genuinely serve people.",
            link = "https://medium.com/@sorenkai/unlocking-asynchronous-power-a-journey-into-kotlin-coroutines-7d402c93df30?source=friends_link&sk=5db7f96bc8a9129b95981ddf203f7ba1"
        ),
        WritingEntry(
            title = "Exploring Kobweb: A New Framework for Compose Web Development",
            publication = "Medium",
            date = "September 23, 2023",
            synopsis = "A look at a new framework for building cross-platform apps. I dive into how the right developer tools can unlock creativity and enable us to create seamless applications that adapt to people’s lives rather than the other way around.",
            link = "https://medium.com/@sorenkai/exploring-kobweb-4d54f9288ce?source=friends_link&sk=6291a4ee4e4afbdb10bad77b7d77aa41"
        ),
        WritingEntry(
            title = "I’m Tired of Toxic Social Media, So I’m Building My Own",
            publication = "Medium",
            date = "March 30, 2025",
            synopsis = "A personal and technical reflection on why today’s social media is broken. I outline how to build new platforms that prioritize authenticity, respect, and genuine human connection.",
            link = "https://medium.com/@sorenkai/im-tired-of-toxic-social-media-so-i-m-building-my-own-56ab59a6f038?source=friends_link&sk=ea41c710e0b3196409e9b178fe70fc77"
        )
    )

    writings.forEach { entry ->
        WritingItem(entry, breakpoint)
    }

}