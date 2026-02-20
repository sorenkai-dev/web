package com.sorenkai.web.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sorenkai.web.NavHeaderStyle
import com.sorenkai.web.components.data.ui.RevealState
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.buttons.HamburgerButton
import com.sorenkai.web.components.widgets.buttons.SettingsButton
import com.sorenkai.web.components.widgets.menus.MenuItems
import com.sorenkai.web.components.widgets.menus.SettingsMenu
import com.sorenkai.web.components.widgets.menus.SideNavMenu
import com.sorenkai.web.components.widgets.menus.SideNavMode
import com.sorenkai.web.ui.navigation.SideMenuDrawer
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

@Composable
fun NavHeader(
    breakpoint: Breakpoint,
    lang: String
) {
    // Single authoritative menu state
    var activeMenu by remember { mutableStateOf<SideNavMode?>(null) }
    var pendingMenu by remember { mutableStateOf<SideNavMode?>(null) }
    var revealState by remember { mutableStateOf(RevealState.HIDDEN) }

    // ---------- Menu control ----------

    fun openMenu(mode: SideNavMode) {
        if (activeMenu == null) {
            // Nothing open, open immediately
            activeMenu = mode
            revealState = RevealState.VISIBLE
        } else {
            // Something is open — close first, then reopen
            pendingMenu = mode
            revealState = RevealState.HIDING
        }
    }

    fun closeMenu() {
        if (activeMenu != null) {
            pendingMenu = null
            revealState = RevealState.HIDING
        }
    }

    fun onDrawerAnimationEnd() {
        if (revealState == RevealState.HIDING) {
            revealState = RevealState.HIDDEN
            activeMenu = null

            // If we were transitioning, open next menu
            pendingMenu?.let { next ->
                pendingMenu = null
                activeMenu = next
                revealState = RevealState.VISIBLE
            }
        }
    }

    // ---------- Header ----------

    Row(
        NavHeaderStyle.toModifier()
            .id("nav-header")
            .position(Position.Sticky)
            .top(0.px)
            .zIndex(5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Link("/") {
            Image(
                Res.Img.LOGO,
                "SorenKai Logo",
                Modifier.height(2.cssRem)
            )
        }

        Spacer()

        // ---------- Desktop ----------

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
                onClick = { openMenu(SideNavMode.Settings) }
            )
        }

        // ---------- Mobile ----------

        Row(
            Modifier
                .fontSize(1.5.cssRem)
                .gap(1.cssRem)
                .displayUntil(Breakpoint.MD),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HamburgerButton(
                onClick = { openMenu(SideNavMode.Navigation) },
                lang = lang
            )
        }
    }

    // ---------- Side Menu Drawer ----------

    SideMenuDrawer(
        revealState = revealState,
        onAnimationEnd = { onDrawerAnimationEnd() }
    ) {
        when (val menu = activeMenu) {
            SideNavMode.Navigation -> {
                SideNavMenu(
                    breakpoint = breakpoint,
                    mode = SideNavMode.Navigation,
                    lang = lang,
                    onClose = { closeMenu() },
                    onSettingsClick = {
                        openMenu(SideNavMode.Settings)
                    }
                ) {
                    MenuItems(
                        isMobile = true,
                        lang = lang,
                        onItemClicked = { closeMenu() }
                    )
                }
            }

            SideNavMode.Settings -> {
                SideNavMenu(
                    breakpoint = breakpoint,
                    mode = SideNavMode.Settings,
                    lang = lang,
                    onClose = { closeMenu() },
                    onSettingsClick = {}
                ) {
                    SettingsMenu(lang)
                }
            }

            SideNavMode.Admin -> {
                TODO()
            }

            SideNavMode.Moderator -> {
                TODO()
            }

            null -> {
                // No menu rendered
            }
        }
    }
}
