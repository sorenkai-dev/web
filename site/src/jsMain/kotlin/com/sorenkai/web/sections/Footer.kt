package com.sorenkai.web.sections

import androidx.compose.runtime.Composable
import com.sorenkai.web.CopyrightStyle
import com.sorenkai.web.FooterStyle
import com.sorenkai.web.components.widgets.socialBar
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCopyright
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLScriptElement
import kotlin.js.Date


@Composable
fun Footer(modifier: Modifier = Modifier, breakpoint: Breakpoint, lang: String) {
    Row(FooterStyle.toModifier().then(modifier)) {
        Box(
            modifier = Modifier
                .classNames("footer")
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.padding(top = 10.px),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    socialBar()
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    copyright(breakpoint, lang)
                }
            }
        }
    }
    Box (
        ref = ref {
            val firebaseScriptId = "firebase-init-script"
            if (document.getElementById(firebaseScriptId) == null) {
                val script = document.createElement("script") as HTMLScriptElement
                script.type = "module"
                script.asDynamic().src = "/firebase-init.js"
                it.appendChild(script)
            }
        }
    )
}

@Composable
private fun copyright(breakpoint: Breakpoint, lang: String = "en") {
    val size = if (breakpoint <= Breakpoint.SM) FontSize.XSmall
        else  FontSize.Medium
    P(
        attrs = CopyrightStyle.toModifier()
            .fontSize(size)
            .toAttrs()
    ) {
        FaCopyright(
            modifier = Modifier.padding(right = 0.625.cssRem)
        )
        Text("${Date().getFullYear()} SorenKai. ${footerTexts[lang]} ")
    }
}

private val footerTexts = mapOf(
    "en" to "All rights reserved.",
    "es" to "Todos los derechos reservados."
)
