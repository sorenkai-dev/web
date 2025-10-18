package com.sorenkai.web.util

import androidx.compose.runtime.*
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement

/**
 * Utility to render a raw Markdown string into Compose HTML content using JetBrains Markdown.
 *
 * Parses the provided Markdown string and injects the generated HTML into the Compose DOM.
 */
@Composable
fun renderMarkdown(content: String) {

    val colorMode = ColorMode.current
    val palette = colorMode.toSitePalette() // Assuming this function exists in your project
    val styleAttr = """style="max-width: 100%; height: auto; display: block; margin: 16px auto;" """
    // Define colors dynamically based on the current theme
    val backgroundColor = palette.brand.primary

    val textColor = palette.brand.accent

    val flavour = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(content)
    var html = HtmlGenerator(content, parsedTree, flavour).generateHtml()
    html = html.replace("<a href=", "<a target=\"_blank\" href=")
    val preStyles = "style=\"" +
        // Word wrap and formatting rules
        "overflow-wrap: break-word; " +
        "word-wrap: break-word; " +
        "white-space: pre-wrap; " +      // Critical for <pre> wrap
        "font-family: monospace; " +
        "padding: 15px; " +
        "border-radius: 5px; " +
        // Dynamic colors
        "background-color: $backgroundColor; " +
        "color: $textColor;" +
        "width: 90%;" +
//        "display: block" +
        "margin: 0 auto" +
        "\""

    // Replace <pre> with the complete, consolidated style attribute
    html = html.replace("<pre>", "<pre $preStyles>")

//    val imageRegex = Regex("<img\\s[^>]*?>") // Use ">" instead of "/>" for broader compatibility
//
//    html = imageRegex.replace(html) { matchResult ->
//        val originalTag = matchResult.value
//        // Wrap the entire image tag with a centered div
//        """<div $styleAttr>$originalTag</div>"""
//    }

    html = html.replace("<img", "<img $styleAttr")

    html = html.replace("<code>", "<code style=\"font-family: monospace;\">")

    var container by remember { mutableStateOf<HTMLElement?>(null) }
    Div({
        ref {
            container = it
            onDispose { container = null }
        }
    })
    val finalHtml = """<div style="word-break: break-word;">$html</div>"""
    LaunchedEffect(html, container) {
        container?.asDynamic().innerHTML = finalHtml
    }
}
