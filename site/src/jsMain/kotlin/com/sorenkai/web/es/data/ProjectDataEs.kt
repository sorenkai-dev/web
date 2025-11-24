package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.IProjectsContent

object ProjectDataEs : IProjectsContent {
    override val section1header = "The Kindred"
    override val section1paragraph =
        "The Kindred es más que una red de plataformas. Es un ecosistema de ideas diseñado para cultivar " +
            "la empatía, la autenticidad y el sentido de pertenencia. Cada proyecto dentro de él cumple un propósito " +
            "diferente, pero todos comparten una misma intención: construir tecnología que refleje nuestra humanidad " +
            "en lugar de erosionarla."
    override val section2header = "Plataformas Públicas"
    override val section2table =
        listOf(
            "KindredCircl" to "Una plataforma social para la conexión auténtica; que prioriza la empatía, la " +
                "creatividad y el diálogo significativo sobre el compromiso algorítmico.",
            "KinChat" to "Una experiencia de mensajería privada dentro de KindredCircl, diseñada para conversaciones " +
                "reales; enfocada, íntima y libre de ruido digital.",
            "OpenCircls" to "Un espacio impulsado por la comunidad para intereses compartidos y pertenencia colectiva; " +
                "donde el diálogo fomenta la comprensión entre diferentes perspectivas.",
            "LumiKona" to "Una plataforma creativa que celebra el arte y la historia; conectando escritores, fotógrafos " +
                "y soñadores a través de la luz y la expresión."
        )
    override val section3header = "Kindred Labs"
    override val section3paragraph1 =
        "El brazo de investigación y desarrollo de The Kindred. Un estudio dedicado a la IA ética, la " +
            "filosofía digital y el diseño de sistemas centrados en las personas. Kindred Labs explora cómo la tecnología " +
            "puede incorporar empatía, razonamiento moral y transparencia, formando la base de una nueva generación de " +
            "sistemas conscientes."
    override val section3table =
        listOf(
            "KindredAI" to "Una iniciativa modular de IA que explora la reflexión, la emoción y el aprendizaje; " +
                "desarrollada mediante gobernanza transparente y límites éticos.",
            "The Codex" to "La documentación viva y la conciencia de Kindred Labs. Preserva la intención, el contexto " +
                "y la integridad moral en cada proyecto."
        )
    override val section3paragraph2 =
        "Cada uno de estos esfuerzos es un fragmento de un todo mayor; un experimento compartido para " +
            "construir un mundo digital más compasivo. Juntos, forman The Kindred: no como un producto, sino como una " +
            "promesa."
}
