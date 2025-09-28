package com.sorenkai.web.components.sections

import androidx.compose.runtime.*
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.NavHeaderStyle
import com.sorenkai.web.SideMenuStyle
import com.sorenkai.web.components.data.ui.SideMenuState
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.CloseButton
import com.sorenkai.web.components.widgets.ColorModeButton
import com.sorenkai.web.components.widgets.HamburgerButton
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
private fun NavLink(
    path: String,
    text: String,
    isTrigger: Boolean = false,
    modifier: Modifier = LinkStyle.toModifier(),
    onClick: (() -> Unit)? = null,
    expanded: Boolean? = null,
) {
    if (isTrigger) {
        val triggerModifier = modifier
            .attr("role", "button")
            .attr("tabindex", "0")
            .attr("aria-haspopup", "true")
            .let { if (expanded != null) it.attr("aria-expanded", expanded.toString()) else it }
            .onClick { onClick?.invoke() }
            .onKeyDown {
                when (it.key) {
                    "Enter", " " -> { it.preventDefault(); onClick?.invoke() }
                    "Escape" -> { it.preventDefault(); onClick?.invoke() }
                }
            }
        Span(attrs = triggerModifier.toAttrs()) {
            Text(text)
        }
    } else {
        Link(path, text, modifier = LinkStyle.toModifier())
    }
}

@Composable
private fun MenuItems(isMobile: Boolean = false) {
    NavLink("/", "Home")
    NavLink("/writings", "Writings")
    NavLink("/projects", "Projects")
    NavLink("/community", "Community")

    if (isMobile) {
        NavLink("/policies/privacy", "Privacy Policy")
        NavLink("/policies/terms", "Terms of Service")
        NavLink("/policies/community", "Community Guidelines")
    } else {
        PoliciesDropdown()
    }
    NavLink("/about", "About")
    NavLink("/contact", "Contact")
}

val SideMenuSlideInAnim = Keyframes {
    from {
        Modifier.translateX(100.percent)
    }

    to {
        Modifier
    }
}


@Composable
fun NavHeader() {
    Row(
        NavHeaderStyle.toModifier()
            .position(Position.Sticky)
            .top(0.px)
            .zIndex(5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Link("/") {
            // Block display overrides inline display of the <img> tag, so it calculates centering better
            Image(Res.Img.LOGO, "SorenKai Logo", Modifier.height(2.cssRem).display(DisplayStyle.Block))
        }

        Spacer()

        Row(Modifier.gap(1.5.cssRem).displayIfAtLeast(Breakpoint.MD), verticalAlignment = Alignment.CenterVertically) {
            MenuItems()
            ColorModeButton()
        }

        Row(
            Modifier
                .fontSize(1.5.cssRem)
                .gap(1.cssRem)
                .displayUntil(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

            ColorModeButton()
            HamburgerButton(onClick =  { menuState = SideMenuState.OPEN })

            if (menuState != SideMenuState.CLOSED) {
                SideMenu(
                    menuState,
                    close = { menuState = menuState.close() },
                    onAnimationEnd = { if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED }
                )
            }
        }
    }
}


@OptIn(DelicateApi::class)
@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit) {
    val breakpoint = rememberBreakpoint()
    Overlay(
        Modifier
            .setVariable(OverlayVars.BackgroundColor, Colors.Black.copyf(alpha = 0.5f))
            // Ensure overlay stacks above all page content and sticky header
            .zIndex(1000)
            .onClick { close() }
    ) {
        key(menuState) { // Force recompute animation parameters when close button is clicked
            Column(
                SideMenuStyle.toModifier()
                    .align(Alignment.CenterEnd)
                    .animation(
                        SideMenuSlideInAnim.toAnimation(
                            duration = 200.ms,
                            timingFunction = if (menuState == SideMenuState.OPEN) AnimationTimingFunction.EaseOut else AnimationTimingFunction.EaseIn,
                            direction = if (menuState == SideMenuState.OPEN) AnimationDirection.Normal else AnimationDirection.Reverse,
                            fillMode = AnimationFillMode.Forwards
                        )
                    )
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.End
            ) {
                CloseButton(onClick = { close() })
                Column(Modifier.padding(right = 0.75.cssRem).gap(1.5.cssRem).fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.Start) {
                    MenuItems(isMobile = breakpoint < Breakpoint.MD)
                }
            }
        }
    }
}


@Composable
private fun PoliciesDropdown() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .position(Position.Relative)
    ) {
        // The trigger link
        NavLink(
            text = "Policies", isTrigger = true, path = "", modifier = Modifier, expanded = expanded,
            onClick = { expanded = !expanded }
        )

        if (expanded) {
            Column(
                modifier = Modifier
                    .position(Position.Absolute)
                    .top(100.percent) // right below the "Policies" link
                    .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                    .boxShadow(
                        offsetX = 0.px,
                        offsetY = 2.px,
                        blurRadius = 6.px,
                        color = Colors.Black.copyf(alpha = 0.15f)
                    )
                    .borderRadius(0.5.cssRem)
                    .padding(0.5.cssRem)
                    .gap(0.5.cssRem)
                    .zIndex(10)
            ) {
                NavLink("/policies/privacy", "Privacy Policy")
                NavLink("/policies/terms", "Terms of Service")
                NavLink("/policies/community", "Community Guidelines")
            }
        }
    }
}