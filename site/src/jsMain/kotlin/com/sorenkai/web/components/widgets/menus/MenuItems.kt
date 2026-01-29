package com.sorenkai.web.components.widgets.menus

import androidx.compose.runtime.Composable

@Composable
fun MenuItems(
    isMobile: Boolean = false,
    lang: String,
    onItemClicked: (() -> Unit)? = null,
) {
    val text = navTexts[lang]!!

    NavLink("/$lang", text["home"]!!, onClick = onItemClicked)
    NavLink("/$lang/writings", text["writings"]!!, onClick = onItemClicked)
    NavLink("/$lang/projects", text["projects"]!!, onClick = onItemClicked)
    NavLink("/$lang/community", text["community"]!!, onClick = onItemClicked)
    NavLink("/$lang/about", text["about"]!!, onClick = onItemClicked)
    NavLink("/$lang/contact", text["contact"]!!, onClick = onItemClicked)
}

private val navTexts = mapOf(
    "en" to mapOf(
        "home" to "Home",
        "writings" to "Writings",
        "projects" to "Projects",
        "community" to "Community",
        "privacy" to "Privacy Policy",
        "terms" to "Terms of Service",
        "guidelines" to "Community Guidelines",
        "policies" to "Policies",
        "about" to "About",
        "contact" to "Contact",
        "settings" to "Settings"
    ),
    "es" to mapOf(
        "home" to "Inicio",
        "writings" to "Escritos",
        "projects" to "Proyectos",
        "community" to "Comunidad",
        "privacy" to "Política de Privacidad",
        "terms" to "Términos de Servicio",
        "guidelines" to "Directrices de la Comunidad",
        "policies" to "Políticas",
        "about" to "Acerca de",
        "contact" to "Contacto",
        "settings" to "Configuración"
    )
)
