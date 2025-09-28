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
fun HamburgerButton(onClick: () -> Unit) {
    IconButton(onClick) {
        HamburgerIcon(modifier = Modifier.ariaLabel("Open menu"))
    }
}

@Composable
fun CloseButton(onClick: () -> Unit) {
    IconButton(onClick) {
        CloseIcon(modifier = Modifier.ariaLabel("Close menu"))
    }
}

@Composable
fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(onClick = { colorMode = colorMode.opposite },) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, "Toggle color mode", placement = PopupPlacement.BottomRight)
}
