package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.WritingItemStyle
import com.sorenkai.web.components.data.model.WritingEntry
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun WritingItem(entry: WritingEntry, breakpoint: Breakpoint) {
    Div(
        attrs = WritingItemStyle.toModifier()
            .fillMaxWidth()
            .toAttrs()
    ) {
        H3 {
            Text(entry.title)
        }
        P {
            Text("${entry.publication} (${entry.date})")
        }
        P {
            Text(entry.synopsis)
        }
        entry.link?.let { url ->
            Link(
                path = url,
                openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB,
                modifier = LinkStyle.toModifier()
            ) {
                Text(
                    if (entry.purchase) "Purchase" else "Read Article"
                )
            }
        }
    }
}
