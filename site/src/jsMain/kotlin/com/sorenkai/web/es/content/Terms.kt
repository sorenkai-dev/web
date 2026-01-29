package com.sorenkai.web.es.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.*

@Composable
fun TermsEsContent(breakpoint: Breakpoint) {
    val update = "21-10-2025"
    val version = "1.0"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        H1(
            attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
            content = { Text("Términos de Servicio") }
        )

        SectionParagraph(
            breakpoint,
            content = { Text("Bienvenido a SorenKai.com, una publicación y comunidad independiente que explora la tecnología, la escritura y el lado humano de la IA. Al acceder o utilizar este sitio web (el “Sitio”), usted acepta estos Términos de Servicio (“Términos”) y nuestras Directrices de la Comunidad y Política de Privacidad.") }
        )
        SectionParagraph(
            breakpoint,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fontStyle(FontStyle.Italic)
                .fontWeight(FontWeight.SemiBold)
                .color(Colors.Red),
            content = { Text("Si no está de acuerdo, por favor no utilice el Sitio.") }
        )

        SectionHeader("1. Propósito y Alcance", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("SorenKai.com (“nosotros” o “nuestro”) es un espacio de publicación y discusión operado por Soren Kai. Estos Términos regulan su acceso y uso de todo el contenido y las funciones del Sitio, incluyendo escritos, ensayos, discusiones comunitarias y áreas de comentarios (colectivamente, los “Servicios”).") }
        )

        SectionHeader("2. Elegibilidad", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("Este Sitio está abierto al público en general. Sin embargo, la participación en las discusiones comunitarias está destinada a usuarios de 13 años o más (o la edad mínima legal en su jurisdicción). Si usted es menor de esa edad, por favor no envíe comentarios ni otro contenido interactivo.") }
        )

        SectionHeader("3. Participación en la Comunidad", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("SorenKai.com incluye funciones interactivas limitadas como comentarios y discusiones. Las cuentas son administradas mediante Zitadel únicamente para prevenir el spam y el abuso automatizado. Estas cuentas no crean perfiles públicos, mensajería privada ni páginas de usuario.") }
        )

        SectionParagraph(breakpoint) {
            Text("Al participar, usted acepta:")
            Ul {
                Li { Text("Publicar solo contenido que cumpla con nuestras Directrices de la Comunidad;") }
                Li { Text("Evitar el acoso, el discurso de odio o actividades ilegales;") }
                Li { Text("Asumir la responsabilidad de sus aportes.") }
            }
            Text("Nos reservamos el derecho de eliminar contenido, deshabilitar el acceso o restringir la participación por violaciones a estos Términos o nuestras Directrices de la Comunidad.")
        }

        SectionHeader("4. Contenido del Usuario", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Usted conserva la propiedad del contenido que envíe (como comentarios o publicaciones en discusiones).")
        }

        SectionParagraph(breakpoint) {
            Text("Al enviar contenido al Sitio, usted otorga a Soren Kai una licencia no exclusiva, mundial, libre de regalías y perpetua para:")
            Ol {
                Li { Text("Mostrar, distribuir y archivar su contenido como parte del funcionamiento del Sitio;") }
                Li { Text("Usar, reproducir, citar o adaptar su contenido en trabajos futuros — incluyendo artículos, ensayos, libros o publicaciones multimedia producidas bajo el nombre de Soren Kai, ya sea en formato impreso, digital o de audio.") }
            }
            Text("Cuando sea posible, se acreditará a los colaboradores por nombre o nombre de usuario cuando sus comentarios o contribuciones públicas sean mencionados en dichos trabajos.")
        }

        SectionParagraph(breakpoint) {
            Text("Puede solicitar la eliminación de su contenido contactando a ")
            Link("mailto:support@sorenkai.com?subject=Content Removal", "support@sorenkai.com")
            Text(". Haremos esfuerzos razonables para eliminarlo de la vista pública, aunque el contenido ya utilizado en materiales publicados puede permanecer en esos contextos.")
        }

        SectionHeader("5. Propiedad Intelectual", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Todos los escritos, diseños y materiales originales en este Sitio, incluidos, entre otros, ensayos, artículos, identidad visual e imágenes, son propiedad intelectual de Soren Kai, salvo que se indique lo contrario. No puede copiar, reproducir ni distribuir ninguna parte del Sitio sin el consentimiento previo por escrito.")
        }

        SectionParagraph(breakpoint) {
            Text("Las contribuciones de invitados siguen siendo propiedad de sus respectivos autores, utilizadas con permiso.")
        }

        SectionHeader("6. Aplicación y Moderación", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Revisamos los reportes de buena fe y podemos tomar acciones como:")
            Ul {
                Li { Text("Eliminar o editar contenido;") }
                Li { Text("Emitir advertencias;") }
                Li { Text("Deshabilitar temporal o permanentemente los privilegios de participación en discusiones.") }
            }
            Text("Si cree que una decisión de moderación fue un error, puede apelar enviando un correo electrónico a ")
            Link("mailto:appeals@sorenkai.com?subject=Moderation Appeal", "appeals@sorenkai.com")
            Text(". Todas las decisiones sobre apelaciones son finales.")
        }

        SectionHeader("7. Descargos de Responsabilidad", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Este Sitio y su contenido se proporcionan “tal cual” y “según disponibilidad”. No ofrecemos garantías, expresas o implícitas, sobre la exactitud, fiabilidad o disponibilidad. No garantizamos acceso ininterrumpido ni ausencia de problemas técnicos. Usted entiende que la participación en las discusiones es bajo su propio criterio y riesgo.")
        }

        SectionHeader("8. Limitación de Responsabilidad", breakpoint)

        SectionParagraph(breakpoint) {
            Text("En la máxima medida permitida por la ley, SorenKai.com, su propietario y afiliados no serán responsables por daños indirectos, incidentales o consecuentes que surjan de su uso del Sitio o su contenido. Nuestra responsabilidad total no excederá los $100 USD o el monto que usted nos haya pagado (si corresponde), lo que sea mayor.")
        }

        SectionHeader("9. Ley Aplicable", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Estos Términos se rigen por las leyes del Estado de Nueva Jersey, sin tener en cuenta los principios sobre conflictos de leyes. Cualquier disputa derivada de estos Términos se resolverá en los tribunales estatales o federales ubicados en el Condado de Camden, Nueva Jersey, salvo que la ley exija lo contrario.")
        }

        SectionHeader("10. Actualizaciones de Estos Términos", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Podemos actualizar estos Términos periódicamente. Si realizamos cambios importantes, publicaremos la nueva versión en esta página con una fecha de vigencia actualizada. Su uso continuo del Sitio después de dichas actualizaciones constituye la aceptación de los Términos revisados.")
        }

        SectionHeader("11. Contacto", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Para preguntas sobre estos Términos, comuníquese con: ")
            Link("mailto:contact@sorenkai.com?subject=ToS Questions", "contact@sorenkai.com")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 4.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SectionParagraph(breakpoint) { Text("Última actualización: $update") }
            SectionParagraph(breakpoint) { Text("Versión $version") }
        }
    }
}
