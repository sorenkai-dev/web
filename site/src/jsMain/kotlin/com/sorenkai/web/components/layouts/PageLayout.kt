package com.sorenkai.web.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.PageContentStyle
import com.sorenkai.web.components.sections.Footer
import com.sorenkai.web.components.sections.NavHeader
import com.sorenkai.web.components.widgets.buttons.backToTopButton
import com.varabyte.kobweb.compose.css.dvh
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gridRow
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.Document
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.EventListener

private fun Document.setPageMetadata(title: String, description: String, robotsContent: String? = null) {
    this.title = "SorenKai – $title"

    val head = this.head!!
    // Update or create <meta name="description">
    (head.querySelector("meta[name='description']")
        ?: this.createElement("meta").apply {
            setAttribute("name", "description")
            head.appendChild(this)
        }
    ).setAttribute("content", description)

    // Open Graph tags (minimal, no X/Twitter)
    (head.querySelector("meta[property='og:title']")
        ?: this.createElement("meta").apply {
            setAttribute("property", "og:title")
            head.appendChild(this)
        }
    ).setAttribute("content", "SorenKai – $title")

    (head.querySelector("meta[property='og:description']")
        ?: this.createElement("meta").apply {
            setAttribute("property", "og:description")
            head.appendChild(this)
        }
    ).setAttribute("content", description)

    (head.querySelector("meta[property='og:type']")
        ?: this.createElement("meta").apply {
            setAttribute("property", "og:type")
            head.appendChild(this)
        }
    ).setAttribute("content", "website")

    robotsContent?.let {
        (head.querySelector("meta[name='robots']")
            ?: this.createElement("meta").apply {
                setAttribute("name", "robots")
                head.appendChild(this)
            }
        ).setAttribute("content", it)
    }
}

val LocalBreakpoint = compositionLocalOf { Breakpoint.ZERO }

class PageLayoutData(
    val title: String,
    val description: String = title,
    val robotsContent: String? = null,
    val lang: String = "en"
)

@OptIn(DelicateApi::class)
@Composable
@Layout
fun PageLayout(ctx: PageContext, content: @Composable ColumnScope.() -> Unit) {
    // Be resilient to missing PageLayoutData so the page still renders (header, footer, content)
    val pageTitle = runCatching { ctx.data.getValue<PageLayoutData>().title }.getOrElse { "Home" }
    val description = runCatching { ctx.data.getValue<PageLayoutData>().description }.getOrElse { "Soren Kai — writer and technologist exploring culture, AI, and belonging." }
    val robotsContent = runCatching { ctx.data.getValue<PageLayoutData>().robotsContent }.getOrNull()
    val lang = runCatching { ctx.data.getValue<PageLayoutData>().lang }.getOrElse { "en" }

    LaunchedEffect(Unit) {
        window.requestAnimationFrame {
            val header = document.getElementById("nav-header")
            if (header != null) {
                val height = header.getBoundingClientRect().height.toInt()
                document.documentElement.asDynamic().style.setProperty(
                    "--nav-header-height",
                    "${height}px"
                )
            }
        }
    }

    val breakpoint = rememberBreakpoint()
    LaunchedEffect(pageTitle, description) {
        document.setPageMetadata("SorenKai - $pageTitle", description, robotsContent)
    }

    // 🔹 Track scroll position robustly (some browsers / setups report scroll on different elements)
    var showBackToTop by remember { mutableStateOf(false) }

    fun readScrollY(): Double {
        // Consider all common scroll containers and cast Numbers safely to Double
        val winY = (window.asDynamic().scrollY as? Number)?.toDouble() ?: 0.0
        val pageYOffset = (window.asDynamic().pageYOffset as? Number)?.toDouble() ?: 0.0
        val scrollingElY = (document.asDynamic().scrollingElement?.scrollTop as? Number)?.toDouble() ?: 0.0
        val docElY = (document.documentElement?.asDynamic().scrollTop as? Number)?.toDouble() ?: 0.0
        val bodyY = (document.body?.asDynamic().scrollTop as? Number)?.toDouble() ?: 0.0
        return maxOf(winY, pageYOffset, scrollingElY, docElY, bodyY)
    }

    DisposableEffect(Unit) {
        // Initialize state immediately
        showBackToTop = readScrollY() > 300.0

        val listener = EventListener {
            showBackToTop = readScrollY() > 300.0 // threshold in px
        }
        // Listen on multiple potential scroll containers to cover various layouts
        window.addEventListener("scroll", listener)
        document.addEventListener("scroll", listener)
        // Capture phase to detect scrolls on inner scrolling containers
        document.addEventListener("scroll", listener, true)
        document.documentElement?.addEventListener("scroll", listener)
        document.body?.addEventListener("scroll", listener)
        (document.asDynamic().scrollingElement as? org.w3c.dom.events.EventTarget)?.addEventListener("scroll", listener)
        // Also listen on our layout root if it becomes the scroll container
        (document.getElementById("top") as? org.w3c.dom.events.EventTarget)?.addEventListener("scroll", listener)

        // Fallback: some environments may not dispatch scroll events as expected. Poll periodically.
        val intervalId = window.setInterval({
            showBackToTop = readScrollY() > 300.0
        }, 200)

        onDispose {
            window.removeEventListener("scroll", listener)
            document.removeEventListener("scroll", listener)
            document.removeEventListener("scroll", listener, true)
            document.documentElement?.removeEventListener("scroll", listener)
            document.body?.removeEventListener("scroll", listener)
            (document.asDynamic().scrollingElement as? org.w3c.dom.events.EventTarget)?.removeEventListener("scroll", listener)
            (document.getElementById("top") as? org.w3c.dom.events.EventTarget)?.removeEventListener("scroll", listener)
            window.clearInterval(intervalId)
        }
    }
    Box(
        Modifier
            .fillMaxWidth()
            .minHeight(100.dvh)
            .backgroundColor(ColorMode.current.toPalette().background)
            .id("top")
            // Create a box with two rows: the main content (fills as much space as it can) and the footer (which reserves
            // space at the bottom). "min-content" means the use the height of the row, which we use for the footer.
            // Since this box is set to *at least* 100%, the footer will always appear at least on the bottom but can be
            // pushed further down if the first row grows beyond the page.
            // Grids are powerful but have a bit of a learning curve. For more info, see:
            // https://css-tricks.com/snippets/css/complete-guide-grid/
            .gridTemplateRows {
                size(1.fr)
                size(minContent)
            }
    ) {
        CompositionLocalProvider(LocalBreakpoint provides breakpoint) {
            Column(
                // Ensure main content is above the decorative SVG
                Modifier.fillMaxWidth().gridRow(1).padding(bottom = 1.cssRem),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NavHeader(breakpoint, lang)
                Div(PageContentStyle.toAttrs()) {
                    content()
                }
            }
        }
        // Associate the footer with the row that will get pushed off the bottom of the page if it can't fit.
        Footer(Modifier.fillMaxWidth().gridRow(2), breakpoint, lang)
        backToTopButton(anchorId = "top", visible = showBackToTop)
    }
    Box(
        ref = ref<HTMLElement> { element ->
            val script = document.createElement("script")
            script.asDynamic().src = "https://plausible.io/js/pa-YfdNM71HTeSfoPy9iyEIN.js"
            script.asDynamic().async = true
            element.appendChild(script)

            val initScript = document.createElement("script")
            initScript.textContent =
                """
                              window.plausible=window.plausible||function(){(plausible.q=plausible.q||[]).push(arguments)},plausible.init=plausible.init||function(i){plausible.o=i||{}};
                plausible.init()
                """.trimIndent()
            element.appendChild(initScript)
        }
    )
}
