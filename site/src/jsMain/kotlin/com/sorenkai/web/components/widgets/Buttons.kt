package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.components.data.ui.SideMenuState
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaGear
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode

@Composable
fun HamburgerButton(onClick: () -> Unit, lang: String) {
    IconButton(onClick) {
        HamburgerIcon(modifier = Modifier.ariaLabel(uiText[lang]?.get("openMenu")!!))
    }
}

@Composable
fun CloseButton(onClick: () -> Unit, lang: String) {
    IconButton(onClick) {
        CloseIcon(modifier = Modifier.ariaLabel(uiText[lang]?.get("closeMenu")!!))
    }
}

@Composable
fun SettingsButton(
    breakpoint: Breakpoint,
    lang: String
) {
    var settingsMenuState by remember { mutableStateOf(SideMenuState.CLOSED) }
    if (settingsMenuState == SideMenuState.CLOSED) {
        FaGear(
            size = IconSize.LG,
            modifier = Modifier
                .color(color = ColorMode.current.toSitePalette().brand.accent)
                .cursor(Cursor.Pointer)
                .onClick { settingsMenuState = SideMenuState.OPEN }
            )
    }
    if (settingsMenuState != SideMenuState.CLOSED) {
        SideMenu(
            breakpoint = breakpoint,
            menuState = settingsMenuState,
            settingsMenu = true,
            close = { settingsMenuState = SideMenuState.CLOSING },
            onAnimationEnd = { if (settingsMenuState == SideMenuState.CLOSING) settingsMenuState = SideMenuState.CLOSED },
            lang = lang,
            content = {
                SettingsMenu(lang)
            }
        )
    }
}

@Composable
fun ColorModeButton(lang: String) {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite },) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(
        ElementTarget.PreviousSibling,
        uiText[lang]?.get("toggleColor")!!,
        placement = PopupPlacement.BottomRight
    )
}


private val uiText = mapOf(
    "en" to mapOf(
        "openMenu" to "Open menu",
        "closeMenu" to "Close menu",
        "toggleColor" to "Toggle color mode"
    ),
    "es" to mapOf(
        "openMenu" to "Abrir menú",
        "closeMenu" to "Cerrar menú",
        "toggleColor" to "Cambiar modo de color"
    )
)
