package com.sorenkai.web.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import com.sorenkai.web.SideMenuSlideInAnim
import com.sorenkai.web.components.data.ui.RevealState
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.onAnimationEnd
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.framework.annotations.DelicateApi
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.overlay.OverlayVars
import com.varabyte.kobweb.silk.style.animation.toAnimation
import org.jetbrains.compose.web.css.AnimationDirection
import org.jetbrains.compose.web.css.AnimationFillMode
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.ms

@OptIn(DelicateApi::class)
@Composable
fun SideMenuDrawer(
    revealState: RevealState,
    onAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Do not mount anything when fully hidden
    if (revealState == RevealState.HIDDEN) return

    Overlay(
        Modifier
            .setVariable(
                OverlayVars.BackgroundColor,
                Colors.Black.copyf(alpha = 0.5f)
            )
            .zIndex(1000)
    ) {
        /*
         * IMPORTANT:
         * We key on revealState to force a fresh CSS animation instance.
         * Without this, reverse animations may not reliably trigger
         * onAnimationEnd, causing the drawer/overlay to remain mounted.
         */
        key(revealState) {
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .align(Alignment.TopEnd)
                    .animation(
                        SideMenuSlideInAnim.toAnimation(
                            duration = 200.ms,
                            timingFunction =
                                if (revealState == RevealState.VISIBLE) {
                                    AnimationTimingFunction.EaseOut
                                } else {
                                    AnimationTimingFunction.EaseIn
                                },
                            direction =
                                if (revealState == RevealState.VISIBLE) {
                                    AnimationDirection.Normal
                                } else {
                                    AnimationDirection.Reverse
                                },
                            fillMode = AnimationFillMode.Forwards
                        )
                    )
                    .onClick { it.stopPropagation() }
                    .onAnimationEnd { onAnimationEnd() },
                horizontalAlignment = Alignment.End
            ) {
                content()
            }
        }
    }
}
