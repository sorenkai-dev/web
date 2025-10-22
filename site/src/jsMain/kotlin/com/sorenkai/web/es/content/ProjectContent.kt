package com.sorenkai.web.es.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.ProjectsTable
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun ProjectsContentEs(breakpoint: Breakpoint) {

    SectionHeader("The Kindred", breakpoint)

    SectionParagraph(breakpoint) {
        Text("The Kindred es más que una red de plataformas. Es un ecosistema de ideas diseñado para cultivar " +
            "la empatía, la autenticidad y el sentido de pertenencia.")
    }

    SectionParagraph(breakpoint) {
        Text("Cada proyecto dentro de él cumple un propósito diferente, pero todos comparten una misma " +
            "intención: construir tecnología que refleje nuestra humanidad en lugar de erosionarla.")
    }

    SectionHeader("Plataformas Públicas", breakpoint)

    ProjectsTable(
        projects = listOf(
            "KindredCircl" to "Una plataforma social para la conexión auténtica; que prioriza la empatía, la " +
                "creatividad y el diálogo significativo sobre el compromiso algorítmico.",
            "KinChat" to "Una experiencia de mensajería privada dentro de KindredCircl, diseñada para conversaciones " +
                "reales; enfocada, íntima y libre de ruido digital.",
            "OpenCircls" to "Un espacio impulsado por la comunidad para intereses compartidos y pertenencia colectiva; " +
                "donde el diálogo fomenta la comprensión entre diferentes perspectivas.",
            "LumiKona" to "Una plataforma creativa que celebra el arte y la historia; conectando escritores, fotógrafos " +
                "y soñadores a través de la luz y la expresión."
        )
    )

    SectionHeader("Kindred Labs", breakpoint)

    SectionParagraph(breakpoint) {
        Text("El brazo de investigación y desarrollo de The Kindred. Un estudio dedicado a la IA ética, la " +
            "filosofía digital y el diseño de sistemas centrados en las personas. Kindred Labs explora cómo la tecnología " +
            "puede incorporar empatía, razonamiento moral y transparencia, formando la base de una nueva generación de " +
            "sistemas conscientes.")
    }

    ProjectsTable(
        projects = listOf(
            "KindredAI" to "Una iniciativa modular de IA que explora la reflexión, la emoción y el aprendizaje; " +
                "desarrollada mediante gobernanza transparente y límites éticos.",
            "The Codex" to "La documentación viva y la conciencia de Kindred Labs. Preserva la intención, el contexto " +
                "y la integridad moral en cada proyecto."
        ),
        modifier = Modifier.margin(top = 2.cssRem)
    )

    SectionParagraph(
        breakpoint,
        modifier = Modifier.margin(topBottom = 4.cssRem)
    ) {
        Text("Cada uno de estos esfuerzos es un fragmento de un todo mayor; un experimento compartido para " +
            "construir un mundo digital más compasivo. Juntos, forman The Kindred: no como un producto, sino como una " +
            "promesa.")
    }
}
