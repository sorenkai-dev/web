package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.ICommunityContent

object CommunityDataEs : ICommunityContent {
    override val communityQuote =
        "Pertenecer significa más que ser visto. Significa sentirte lo bastante seguro como para ser tú mismo."
    override val communityQuoteAuthor = "-- Soren Kai"
    override val communityLeadParagraph =
        "La página de Comunidad se convertirá en un lugar de encuentro; un pequeño rincón de internet donde " +
            "las personas lectoras puedan conocerse entre ensayos. Aquí, las conversaciones no perseguirán la " +
            "indignación ni la velocidad, sino que crecerán desde la curiosidad, la reflexión y el respeto. " +
            "Cuando se abra, este será el espacio para responder, conectar e imaginar juntas."
    override val policyHeading = "Políticas de la Comunidad"
    override val privacyLink = "Política de Privacidad"
    override val guidelinesLink = "Guía de la Comunidad"
    override val tosLink = "Términos del Servicio"
    override val ctaHeading = "¿Quieres un lugar en la mesa?"
    override val ctaParagraph =
        "Este será un espacio para lectores, creadores y mentes afines. Déjanos tu correo electrónico y te " +
            "invitaremos en cuanto se abra la comunidad."
    override val ctaLink = "Avísame cuando se abra"
}
