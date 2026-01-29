package com.sorenkai.web.components.widgets.menus

import androidx.compose.runtime.Composable
import com.sorenkai.web.SideMenuStyle
import com.sorenkai.web.components.widgets.buttons.CloseButton
import com.sorenkai.web.components.widgets.buttons.SettingsButton
import com.sorenkai.web.components.widgets.header.HeaderTitle
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem

@Composable
fun SideNavMenu(
    breakpoint: Breakpoint,
    mode: SideNavMode,
    lang: String,
    onClose: () -> Unit,
    onSettingsClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val labelsForLang = labels[lang] ?: error("Language not supported: $lang")

    Column(
        SideMenuStyle.toModifier(),
        horizontalAlignment = Alignment.End
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (mode) {
                SideNavMode.Settings -> {
                    HeaderTitle(
                        text = labelsForLang["settings"]!!
                    )
                }

                SideNavMode.Admin -> {
                    HeaderTitle(
                        text = labelsForLang["admin"] ?: "Admin"
                    )
                }

                SideNavMode.Moderator -> {
                    HeaderTitle(
                        text = labelsForLang["moderator"] ?: "Moderator"
                    )
                }

                SideNavMode.Navigation -> {
                    SettingsButton(
                        breakpoint = breakpoint,
                        lang = lang,
                        onClick = onSettingsClick
                    )
                }
            }

            CloseButton(onClick = onClose, lang = lang)
        }

        // Body
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


private val labels = mapOf(
    "en" to mapOf(
        "settings" to "Settings",
        "admin" to "Admin",
        "moderator" to "Moderator"
    ),
    "es" to mapOf(
        "settings" to "Configuración",
        "admin" to "Administración",
        "moderator" to "Moderación"
    )
)
