package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.SocialLinkStyle
import com.sorenkai.web.components.util.Constants.BLUESKY_URL
import com.sorenkai.web.components.util.Constants.GITHUB_URL
import com.sorenkai.web.components.util.Constants.LINKEDIN_URL
import com.sorenkai.web.components.util.Constants.SUBSTACK_URL
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.icons.fa.FaBluesky
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaLinkedin
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

@Composable
fun socialBar() {
    Row(
        modifier = Modifier
            .margin(top = 1.25.cssRem)
            .padding(leftRight = 1.5625.cssRem)
            .minHeight(2.5.cssRem)
            .borderRadius(r = 1.25.cssRem)
            .backgroundColor(Colors.Transparent)
            .gap(0.75.cssRem)
    ) {
        socialLinks(SocialLinkStyle.toModifier())
    }
}

@OptIn(DelicateApi::class)
@Composable
fun socialLinks(
    modifier: Modifier = Modifier
) {
    val breakpoint = rememberBreakpoint()

    Link(
        path = SUBSTACK_URL,
        openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB,
    ) {
        SubstackIcon(
            modifier = modifier.size(
                when {
                    breakpoint < Breakpoint.SM -> 16.px
                    breakpoint < Breakpoint.MD -> 24.px
                    else -> 32.px
                }
            )
        )
    }

    Link(
        path = LINKEDIN_URL,
        openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB
    ) {
        FaLinkedin(
            modifier = modifier,
            size = when {
                breakpoint < Breakpoint.SM -> IconSize.SM
                breakpoint < Breakpoint.MD -> IconSize.LG
                else -> IconSize.XXL
            }
        )
    }
    
    Link(
        path = GITHUB_URL,
        openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB
    ) {
        FaGithub(
            modifier = modifier,
            size = when {
                breakpoint < Breakpoint.SM -> IconSize.SM
                breakpoint < Breakpoint.MD -> IconSize.LG
                else -> IconSize.XXL
            }
        )
    }
    
    Link(
        path = BLUESKY_URL,
        openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB
    ) {
        FaBluesky(
            modifier = modifier,
            size = when {
                breakpoint < Breakpoint.SM -> IconSize.SM
                breakpoint < Breakpoint.MD -> IconSize.LG
                else -> IconSize.XXL
            }
        )
    }
}