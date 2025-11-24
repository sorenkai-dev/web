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
    val palette = colorMode.toSitePalette()
    val backgroundColor = palette.brand.codeback
    val textColor = palette.brand.codetext
    val flavour = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(content)
    var html = HtmlGenerator(content, parsedTree, flavour).generateHtml()
    var container by remember { mutableStateOf<HTMLElement?>(null) }
    val iframeRegex = Regex("""<iframe\s+.*?</iframe\s*>""",
        setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))

    val iframeStyle = "style=\"" +
        "position: relative;" +
        "width: 100%; " +
        "height: 0; " +
        "max-width: 640px;" +
        "margin: 16px auto;" +
        "padding-bottom: 56.25%; " +
        "\""

    val styleAttr = "style=\"" +
        "max-width: 100%; " +
        "height: auto; " +
        "width: auto; " +
        "display: block; " +
        "margin: 16px auto;" +
        "\""

    val preStyles = "style=\"" +
        "overflow-wrap: break-word; " +
        "word-wrap: break-word; " +
        "white-space: pre-wrap; " +
        "font-family: monospace; " +
        "padding: 15px; " +
        "border-radius: 5px; " +
        "background-color: $backgroundColor; " +
        "color: $textColor;" +
        "width: 90%;" +
        "margin: 0 auto" +
        "\""

    val inlineCodeStyles = "style=\"" +
        "background-color: $backgroundColor; " +
        "color: $textColor; " +
        "padding: 2px 4px; " +
        "border-radius: 3px; " +
        "font-family: monospace;" +
        "\""

    val blockquotestyles = "style=\"" +
        "background-color: ${palette.brand.accent}; " +
        "color: ${palette.brand.accentText}; " +
        "padding: 15px; " +
        "border-radius: 5px;\""

    html = html.replace("<a href=", "<a target=\"_blank\" href=")

    html = html.replace("<pre>", "<pre $preStyles>")

    html = html.replace("<img", "<img $styleAttr")

    html = html.replace("<code>", "<code $inlineCodeStyles>")

    html = html.replace("<blockquote>", "<blockquote $blockquotestyles>")

    html = iframeRegex.replace(html) { matchResult ->
        // Grab the full <iframe> content
        val fullIframeTag = matchResult.value
        console.log("Full iframe tag: $fullIframeTag")
        // Wrap the full tag with your styled <div>
        """<div $iframeStyle>$fullIframeTag</div>"""
    }

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
