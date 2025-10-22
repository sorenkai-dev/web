package com.sorenkai.web.es.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Em
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Text

@Composable
fun HomeHeroEsQuote(breakpoint: Breakpoint) {
    val size = if (breakpoint <= Breakpoint.SM) 1.25.cssRem
            else if (breakpoint <= Breakpoint.MD) 1.5.cssRem
            else 1.75.cssRem
    Div(
        attrs = BlockquoteCardStyle.toModifier().toAttrs()
    ) {
        BlockQuote(
            {
                Text("“El progreso no debe medirse solo por lo que las máquinas pueden hacer, sino por cuánto " +
                    "más plenamente seguimos siendo humanos en su presencia.”")
            },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size),
            citation = "-- Soren Kai"
        )
    }
}

@Composable
fun HomeContentEs(breakpoint: Breakpoint) {

    LeadParagraph(breakpoint) {
        Text("Soren Kai es escritor y tecnólogo que explora cómo la tecnología moldea la conexión humana, la " +
            "identidad y el sentido de pertenencia. Con un pie en el código y el otro en la prosa, examina no solo la " +
            "inteligencia artificial, sino también el arco más amplio de herramientas que transforman cómo vivimos y nos " +
            "relacionamos. Su trabajo combina fluidez técnica con reflexión cultural, siempre preguntándose qué significa " +
            "seguir siendo plenamente humanos en un mundo rehecho por las máquinas.")
    }

    SectionHeader("Cultura y Pertenencia", breakpoint)

    SectionParagraph(breakpoint) {
        Text("La tecnología no solo cambia lo que hacemos; también redefine cómo nos vemos a nosotros mismos y a los " +
            "demás. Exploro cómo la vida digital influye en la identidad, la presencia y la conexión. Pienso en cómo las " +
            "decisiones de diseño en los sistemas que construimos pueden fortalecer o fracturar el sentido de pertenencia.")
    }

    SectionHeader("I.A. y Tecnología", breakpoint)

    SectionParagraph(breakpoint) {
        Text("La inteligencia artificial y las plataformas móviles ya no son accesorios; están entretejidas en la " +
            "vida cotidiana. Escribo sobre el impacto cultural de estos sistemas y también los construyo y estudio " +
            "directamente, desde arquitecturas backend hasta servicios impulsados por IA. Esa doble perspectiva me permite " +
            "preguntar no solo ")
        I { Text("qué podemos construir") }
        Text(", sino ")
        Em { Text("qué deberíamos construir y cómo puede ayudar a mi comunidad y a la sociedad en general.") }
    }

    SectionHeader("Voces LGBTQ+", breakpoint)

    SectionParagraph(breakpoint) {
        Text("La pertenencia también es algo personal. Como miembro de la comunidad LGBTQ, escribo sobre nuestras " +
            "historias, luchas y resiliencia. En mi trabajo técnico, llevo esos mismos valores, asegurando que las " +
            "plataformas y herramientas que ayudo a crear no solo funcionen, sino que también generen espacio para la " +
            "autenticidad y la visibilidad.")
    }
}
