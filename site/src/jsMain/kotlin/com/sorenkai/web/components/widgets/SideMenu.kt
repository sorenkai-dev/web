package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.sorenkai.web.SideMenuStyle
import com.sorenkai.web.components.data.ui.SideMenuState
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.onAnimationEnd
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.translateX
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.AnimationDirection
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Text

@OptIn(DelicateApi::class)
@Composable
fun SideMenu(
    breakpoint: Breakpoint,
    menuState: SideMenuState,
    settingsMenu: Boolean = false,
    close: () -> Unit,
    onAnimationEnd: () -> Unit,
    lang: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val settingsText = labels[lang] ?: error("Language not supported: $lang")
    Overlay(
        Modifier
            .setVariable(OverlayVars.BackgroundColor, Colors.Black.copyf(alpha = 0.5f))
            .zIndex(1000)
            .onClick { close() }
    ) {
        key(menuState) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (settingsMenu) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .color(ColorMode.current.toSitePalette().brand.accent)
                                .padding(left = 0.75.cssRem)
                                .fontWeight(FontWeight.SemiBold),
                        ) {
                            Text(settingsText["settings"]!!)
                        }
                    } else {
                        SettingsButton(
                            breakpoint = breakpoint,
                            lang = lang
                        )
                    }
                    CloseButton(onClick = { close() }, lang)

                }
                Column(
                    Modifier
                        .padding(right = 0.75.cssRem)
                        .gap(1.5.cssRem)
                        .fontSize(1.4.cssRem),
                    horizontalAlignment = Alignment.Start
                ) {
                    content()
                }
            }
        }
    }
}

val SideMenuSlideInAnim = Keyframes {
    from {
        Modifier.translateX(100.percent)
    }
    to {
        Modifier
    }
}

private val labels = mapOf(
    "en" to mapOf(
        "settings" to "Settings"
    ),
    "es" to mapOf(
        "settings" to "Configuración"
    )
)

