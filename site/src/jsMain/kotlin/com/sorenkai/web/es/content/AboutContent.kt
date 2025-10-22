package com.sorenkai.web.es.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.LinkStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun AboutHeroEsQuote(breakpoint: Breakpoint) {
    val size = if (breakpoint <= Breakpoint.SM) 1.25.cssRem
    else if (breakpoint <= Breakpoint.MD) 1.5.cssRem
    else 1.75.cssRem

    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            content = {
                Text("Entre el código y la conciencia hay una pregunta; no sobre lo que podemos construir, " +
                    "sino sobre quiénes nos convertimos cuando lo hacemos.")
            },
            citation = "— Soren Kai",
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
}

@Composable
fun AboutContentEs(breakpoint: Breakpoint) {
    LeadParagraph(breakpoint) {
        Text("Escribo y programo desde el mismo impulso: entender qué significa ser humano en una era " +
            "definida por algoritmos. Cada línea de código, cada frase, es un intento de alinear lo abstracto " +
            "con lo emocional; de reconciliar la lógica con la empatía, la estructura con el alma.")
    }

    SectionHeader("Por Qué Escribo, Por Qué Construyo", breakpoint)

    SectionParagraph(
        breakpoint,
        content = { Text("He pasado mi vida en la intersección de dos mundos: las arquitecturas precisas " +
            "de la tecnología y la ambigüedad ilimitada del lenguaje. Ambos, a su manera, son sistemas para dar " +
            "significado. Ambos son actos de creación.") }
    )

    SectionParagraph(breakpoint) {
        Text("La escritura se convirtió en mi forma de sentir el pulso de las ideas. La ingeniería, en mi " +
            "forma de darles forma. Juntas, forman una sola búsqueda: construir sistemas que honren las historias " +
            "humanas que contienen.")
    }

    SectionParagraph(breakpoint) {
        Text("Fue esta búsqueda la que llevó a Kindred, un ecosistema de plataformas e ideas basadas en la " +
            "empatía, la autenticidad y el sentido de pertenencia; una respuesta a la toxicidad que con demasiada " +
            "frecuencia define nuestras vidas digitales.")

    }

    SectionHeader("Lenguaje y Lógica", breakpoint)

    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(leftRight = 2.cssRem)
        ) {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) {
                Span(
                    attrs = Modifier.fontWeight(700).toAttrs()
                ) { Text("La Escritura como Reflexión") }
            }
            Text("A través de ensayos como Canary in the Data Mine y The Mirror and the Machine, exploro " +
                "cómo la tecnología redefine la identidad; cómo el reconocimiento, el poder y la pertenencia son " +
                "reescritos por el código. Escribir me permite dar un paso atrás y preguntarme no cómo piensan las " +
                "máquinas, sino por qué queremos que lo hagan.")
        }
        Column(
            modifier = Modifier.padding(leftRight = 2.cssRem)
        ) {
            Div(
                attrs = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .toAttrs()
            ) {
                Span(
                    attrs = Modifier.fontWeight(700).toAttrs()
                ) { Text("La Tecnología como Expresión") }
            }
            Text("Como ingeniero, construyo plataformas y sistemas que reflejan estas preguntas en la " +
                "práctica: KindredCircl para la conexión auténtica. Kindred Labs para la investigación de IA ética. " +
                "Cada proyecto es un experimento en alinear la arquitectura con la empatía. Una prueba de que la " +
                "tecnología puede ser rigurosa y, al mismo tiempo, humana.")
        }
    }

    SectionHeader("Misión — La Humanidad al Centro", breakpoint)

    SectionParagraph(breakpoint) {
        BlockQuote({
            Text("Diseñar sistemas —lingüísticos, sociales y técnicos— que fortalezcan la conexión auténtica " +
                "y cultiven el sentido de pertenencia.")
        })
        Text("La tecnología no debe erosionar nuestra humanidad; debe amplificarla. Mi misión es construir " +
            "herramientas que sirvan al espíritu humano; que nos recuerden que pertenecemos no por los algoritmos, " +
            "sino por las historias que compartimos.")
    }

    SectionHeader("La Filosofía de Kindred", breakpoint)

    SectionParagraph(breakpoint) {
        Text("Kindred es más que una red de plataformas; es una filosofía. Una creencia de que la empatía es " +
            "infraestructura y que el código puede escribirse con conciencia.")
    }

    SectionParagraph(breakpoint) {
        Text("Desde la red social basada en parentesco de KindredCircl hasta el marco ético de aprendizaje de " +
            "KindredAI, cada proyecto es un acto de resistencia: contra la soledad, el cinismo y la indiferencia " +
            "digital. Juntos, forman un experimento vivo sobre lo que significa construir tecnología para y con la " +
            "humanidad.")
    }

    SectionHeader("Un Llamado a Pertenecer", breakpoint)

    SectionParagraph(breakpoint) {
        BlockQuote(
            { Text("Si la historia de la humanidad y las máquinas aún se está escribiendo, que la " +
                "escribamos con empatía. Recordemos que todo algoritmo comienza con una intención; y que esa intención " +
                "comienza con nosotros.") },
            citation = "— Soren Kai"
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Link(
                path = "/writings",
                text = "Explorar Escritos",
                modifier = LinkStyle.toModifier()
            )
            Link(
                path = "/projects",
                text = "Ver Proyectos",
                modifier = LinkStyle.toModifier()
            )
        }
    }
}
