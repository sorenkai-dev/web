package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.Animation
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun HorizontalProgressBar() {
    val colorMode = ColorMode.current
    val palette = colorMode.toSitePalette()
    val barColor = palette.brand.primary // Use your site's primary color

    // Outer Box: The track (background)
    Box(
        Modifier
            .fillMaxWidth(80.percent) // Set a width for the bar to sit under the text
            .height(6.px)
            .backgroundColor(palette.nearBackground)
            .borderRadius(3.px)
    ) {
        // Inner Box: The actual moving bar
        Box(
            Modifier
                .fillMaxHeight()
                .width(40.percent) // The bar is only 40% of the track width
                .backgroundColor(barColor)
                .borderRadius(3.px)
                .styleModifier {
                    // Apply a continuous translation animation to simulate progress
                    Animation.of("indeterminateAnimation 1.5s ease-in-out infinite")
                }
        )
    }
}
