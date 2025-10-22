package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.ariaLabel
import com.varabyte.kobweb.silk.components.icons.CloseIcon
import com.varabyte.kobweb.silk.components.icons.HamburgerIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
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
fun ColorModeButton(lang: String) {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite },) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, uiText[lang]?.get("toggleColor")!!, placement = PopupPlacement.BottomRight)
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
