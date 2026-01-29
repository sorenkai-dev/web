package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.ICommunityContent

object CommunityDataEs : ICommunityContent {
    override val communityQuote =
        "Pertenecer significa más que ser visto. Significa sentirte lo bastante seguro como para ser tú mismo."
    override val communityQuoteAuthor = "-- Soren Kai"
    override val communityLeadParagraph =
        "La página de la Comunidad es un lugar de encuentro; un pequeño rincón de Internet donde los lectores pueden conocerse " +
            "entre ensayos. Aquí, las conversaciones no persiguen la indignación ni la velocidad; crecerán a partir de la curiosidad, " +
            "la reflexión y el respeto."
    override val policyHeading = "Políticas de la Comunidad"
    override val privacyLink = "Política de Privacidad"
    override val guidelinesLink = "Guía de la Comunidad"
    override val tosLink = "Términos del Servicio"
    override val loginButton = "Iniciar sesión para publicar"
}
