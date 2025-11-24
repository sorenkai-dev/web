package com.sorenkai.web.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.NavHeaderStyle
import com.sorenkai.web.SideMenuStyle
import com.sorenkai.web.components.data.ui.SideMenuState
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.CloseButton
import com.sorenkai.web.components.widgets.ColorModeButton
import com.sorenkai.web.components.widgets.HamburgerButton
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onAnimationEnd
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
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
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationDirection
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.AnimationTimingFunction.Companion.EaseInOut
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.Transition
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text


@OptIn(ExperimentalComposeWebApi::class)
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
        Span(attrs = triggerModifier
            .display(DisplayStyle.Flex)
            .alignItems(AlignItems.Center)
            .gap(0.35.cssRem)
            .toAttrs()
        ) {
            Text(text)

            // ▼ Animated caret
            Svg(
                attrs = Modifier
                    .size(0.8.em)
                    .transition { Transition( "transform", 0.2.s, EaseInOut) }
                    .transform { rotate(if (expanded == true) 180.deg else 0.deg) }
                    .styleModifier {
                        property("viewBox", "0 0 30 30")
                    }
                    .toAttrs()
            ) {
                Path {
                    attr("d", "M2 7 L6 15 L10 7 Z")
                    attr("stroke", "none")
                    attr("fill", "currentColor")
                    attr("stroke-width", "2")
                    attr("stroke-linecap", "miter")
                    attr("stroke-linejoin", "miter")
                }
            }
        }
    } else {
        Link(path, text, modifier = LinkStyle.toModifier())
    }
}

@Composable
private fun MenuItems(isMobile: Boolean = false, lang: String) {
    val text = navTexts[lang]!!

    NavLink("/$lang", text["home"]!!)
    NavLink("/$lang/writings", text["writings"]!!)
    NavLink("/$lang/projects", text["projects"]!!)
    NavLink("/$lang/community", text["community"]!!)
    NavLink("/$lang/about", text["about"]!!)
    NavLink("/$lang/contact", text["contact"]!!)
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
fun NavHeader(lang: String) {
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
            MenuItems(lang = lang)
            ColorModeButton(lang)
        }

        Row(
            Modifier
                .fontSize(1.5.cssRem)
                .gap(1.cssRem)
                .displayUntil(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

            ColorModeButton(lang)
            HamburgerButton(onClick =  { menuState = SideMenuState.OPEN }, lang)

            if (menuState != SideMenuState.CLOSED) {
                SideMenu(
                    menuState,
                    close = { menuState = menuState.close() },
                    onAnimationEnd = { if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED },
                    lang = lang
                )
            }
        }
    }
}


@OptIn(DelicateApi::class)
@Composable
private fun SideMenu(menuState: SideMenuState, close: () -> Unit, onAnimationEnd: () -> Unit, lang: String) {
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
                CloseButton(onClick = { close() }, lang)
                Column(Modifier.padding(right = 0.75.cssRem).gap(1.5.cssRem).fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.Start) {
                    MenuItems(isMobile = breakpoint < Breakpoint.MD, lang = lang)
                }
            }
        }
    }
}


private val navTexts = mapOf(
    "en" to mapOf(
        "home" to "Home",
        "writings" to "Writings",
        "projects" to "Projects",
        "community" to "Community",
        "privacy" to "Privacy Policy",
        "terms" to "Terms of Service",
        "guidelines" to "Community Guidelines",
        "policies" to "Policies",
        "about" to "About",
        "contact" to "Contact"
    ),
    "es" to mapOf(
        "home" to "Inicio",
        "writings" to "Escritos",
        "projects" to "Proyectos",
        "community" to "Comunidad",
        "privacy" to "Política de Privacidad",
        "terms" to "Términos de Servicio",
        "guidelines" to "Directrices de la Comunidad",
        "policies" to "Políticas",
        "about" to "Acerca de",
        "contact" to "Contacto"
    )
)
