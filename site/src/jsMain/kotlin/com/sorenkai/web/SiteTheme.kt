package com.sorenkai.web

import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

/**
 * @property nearBackground A useful color to apply to a container that should differentiate itself from the background
 *   but just a little.
 */
class SitePalette(
    val nearBackground: Color,
    val cobweb: Color,
    val brand: Brand,
) {
    class Brand(
        val primary: Color = Color.rgb(0x006D77), // Deep Teal
        val accent: Color = Color.rgb(0x66A9AD),  // Lighter Teal
        val accentText: Color = Color.rgb(0xFFFFFF),
        val gradient: LinearGradient = linearGradient(
            dir = LinearGradient.Direction.ToBottomRight,
            from = primary, to = accent
        ),
        val visited: Color = Color.rgb(0xC99700),
        val shadow: Color = Color.argb(0x26000000),
        val codeback: Color = Color.rgb(0xFAFAFA),
        val codetext: Color = Color.rgb(0x333333),
    )
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(0xF5F3EB), // cream off-white
        cobweb = Colors.LightGray,
        brand = SitePalette.Brand(
            primary = Color.rgb(0x006D77), // Deep Teal
            accent = Color.rgb(0x66A9AD),  // Lighter Teal
            accentText = Color.rgb(0x000000),
            gradient = linearGradient(
                dir = LinearGradient.Direction.ToBottomRight,
                from = Color.rgb(0x006D77),
                to = Color.rgb(0x66A9AD)
            ),
            visited = Color.rgb(0xC99700),
            shadow = Color.argb(0x26000000),
            codeback = Color.rgb(0xFAFAFA),
            codetext = Color.rgb(0x333333)
        )
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(0x1A1A1A), // deep near-black
        cobweb = Colors.LightGray.inverted(),
        brand = SitePalette.Brand(
            primary = Color.rgb(0x66A9AD), // lighter teal pops better in dark mode
            accent = Color.rgb(0x006D77), // deep teal as secondary in dark mode
            accentText = Color.rgb(0xFFFFFF),
            gradient = linearGradient(
                dir = LinearGradient.Direction.ToBottomRight,
                from = Color.rgb(0x66A9AD),
                to = Color.rgb(0x006D77)
            ),
            visited = Color.rgb(0xFFD369),
            shadow = Color.argb(0x19FFFFFF),
            codeback = Color.rgb(0x2D2D2D),
            codetext = Color.rgb(0xE0E0E0)
        )
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    // Light Mode
    ctx.theme.palettes.light.background = Color.rgb(0xFAF9F6) // cream background
    ctx.theme.palettes.light.color = Color.rgb(0x333333) // charcoal text

    // Dark Mode
    ctx.theme.palettes.dark.background = Color.rgb(0x06080B) // very dark blue-black
    ctx.theme.palettes.dark.color = Colors.White // clean white text
}
