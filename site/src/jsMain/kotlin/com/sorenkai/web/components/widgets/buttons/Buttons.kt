package com.sorenkai.web.components.widgets.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.sorenkai.web.toSitePalette
import com.sorenkai.web.ui.text.MenuActionText
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
        HamburgerIcon(modifier = Modifier.ariaLabel(MenuActionText[lang]?.getValue("openMenu") ?: error("Language not supported: $lang")))
    }
}

@Composable
fun CloseButton(onClick: () -> Unit, lang: String) {
    IconButton(onClick) {
        CloseIcon(modifier = Modifier.ariaLabel(MenuActionText[lang]?.get("closeMenu") ?: error("Language not supported: $lang")))
    }
}

@Composable
fun SettingsButton(
    breakpoint: Breakpoint,
    lang: String,
    onClick: () -> Unit
) {
    FaGear(
        size = IconSize.LG,
        modifier = Modifier
            .color(color = ColorMode.current.toSitePalette().brand.accent)
            .cursor(Cursor.Pointer)
            .onClick { onClick() }
    )
}

@Composable
fun ColorModeButton(lang: String) {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite }) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(
        ElementTarget.PreviousSibling,
        MenuActionText[lang]?.get("toggleColor") ?: error("Language not supported: $lang"),
        placement = PopupPlacement.BottomRight
    )
}
