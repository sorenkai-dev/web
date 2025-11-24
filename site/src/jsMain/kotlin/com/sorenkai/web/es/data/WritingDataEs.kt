package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.IWritingContent

object WritingDataEs : IWritingContent {
    override val writingQuote =
        "\"El hecho de contar historias insinúa una inquietud humana fundamental, una señal de " +
            "imperfección humana. Donde hay perfección, no hay historia que contar.\""
    override val writingQuoteAuthor = "— Ben Okri"
    override val leadParagraph =
        "Esta página es un portafolio de mis trabajos publicados y en desarrollo. Reúne ensayos, " +
            "reflexiones y contribuciones que exploran las intersecciones entre tecnología, cultura y " +
            "pertenencia. Algunas piezas son profundamente técnicas, otras más reflexivas, pero todas comparten " +
            "la misma pregunta: ¿qué significa seguir siendo plenamente humanos en un mundo moldeado por las " +
            "máquinas? Cada entrada incluye una breve sinopsis y un enlace para leer o adquirir el trabajo completo."
    override val loadingText = "Cargando escritos..."
    override val tagLabel = "Todos"
    override val httpErrorText = "Error del servidor"
    override val networkErrorText = "Problema de red, por favor intente de nuevo."
    override val unknownErrorText = "Ocurrió un error inesperado."
}
