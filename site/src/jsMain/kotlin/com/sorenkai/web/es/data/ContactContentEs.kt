package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.IContactContent

object ContactContentEs : IContactContent {
    override val section1Header = "Ponte en Contacto"
    override val section1Paragraph1 =
        "Ya seas un lector conmovido por un ensayo, un tecnólogo explorando la ética en la IA, " +
            "o simplemente alguien curioso por este trabajo, me encantaría saber de ti."
    override val section1Paragraph2 = "Construyamos algo reflexivo juntos."
    override val section2Header = "Correo Electrónico"
    override val section2email = "Envíame un correo"
    override val section3Header = "Conecta"
    override val section3Paragraph = "Las ideas más significativas comienzan con una simple conversación."
}
