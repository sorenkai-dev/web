package com.sorenkai.web.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.NavHeaderStyle
import com.sorenkai.web.components.data.ui.SideMenuState
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.HamburgerButton
import com.sorenkai.web.components.widgets.SettingsButton
import com.sorenkai.web.components.widgets.SideMenu
import com.varabyte.kobweb.compose.dom.svg.Path
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignItems
import com.varabyte.kobweb.compose.ui.modifiers.attr
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AnimationTimingFunction.Companion.EaseInOut
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.Transition
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.em
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



@Composable
fun NavHeader(
    breakpoint: Breakpoint,
    lang: String
) {
    var settingsMenuState by remember { mutableStateOf(SideMenuState.CLOSED) }

    Row(
        NavHeaderStyle.toModifier()
            .position(Position.Sticky)
            .top(0.px)
            .zIndex(5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Link("/") {
            Image(
                Res.Img.LOGO,
                "SorenKai Logo",
                Modifier
                    .height(2.cssRem)
                    .display(DisplayStyle.Block))
        }

        Spacer()

        Row(
            Modifier
                .gap(1.5.cssRem)
                .displayIfAtLeast(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuItems(lang = lang)
            SettingsButton(
                breakpoint = breakpoint,
                lang = lang,
            )
            if (settingsMenuState != SideMenuState.CLOSED) {
                SideMenu(
                    breakpoint = breakpoint,
                    menuState = settingsMenuState,
                    settingsMenu = true,
                    close = { settingsMenuState = SideMenuState.CLOSING },
                    onAnimationEnd = { if (settingsMenuState == SideMenuState.CLOSING) settingsMenuState = SideMenuState.CLOSED },
                    lang = lang,
                    content = {
                        MenuItems(isMobile = breakpoint < Breakpoint.MD, lang = lang)
                    }
                )

            }
        }

        Row(
            Modifier
                .fontSize(1.5.cssRem)
                .gap(1.cssRem)
                .displayUntil(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var menuState by remember { mutableStateOf(SideMenuState.CLOSED) }

            HamburgerButton(onClick =  { menuState = SideMenuState.OPEN }, lang)

            if (menuState != SideMenuState.CLOSED) {
                SideMenu(
                    breakpoint = breakpoint,
                    menuState = menuState,
                    close = { menuState = menuState.close() },
                    onAnimationEnd = { if (menuState == SideMenuState.CLOSING) menuState = SideMenuState.CLOSED },
                    lang = lang,
                    content = {
                        MenuItems(isMobile = breakpoint < Breakpoint.MD, lang = lang)
                    }
                )
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
        "contact" to "Contact",
        "settings" to "Settings"
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
        "contact" to "Contacto",
        "settings" to "Configuración"
    )
)
