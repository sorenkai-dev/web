package com.sorenkai.web.es.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.*

@Composable
fun PrivacyEsContent(breakpoint: Breakpoint) {
    val update = "21 de octubre de 2025"
    val version = "1.0"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        H1 (
            attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
            content = { Text("Política de Privacidad") }
        )

        LeadParagraph(breakpoint) {
            Text("Esta página contiene la Política de Privacidad, vigente a partir del $update, para sorenkai.com y sus subdominios relacionados (colectivamente, el \"Sitio\"). Soren Kai, o su representante designado (\"nosotros\", \"nos\" o \"nuestro\"), es el único responsable del Sitio y de su contenido.")
        }

        SectionHeader("1. Información que Recopilamos", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Recopilamos únicamente la información necesaria para operar el Sitio y sus funciones comunitarias limitadas:")
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos de Autenticación: ") }
                    )
                    Text("Dirección de correo electrónico o credenciales básicas de inicio de sesión de su proveedor elegido (Google, Microsoft, etc.) utilizadas exclusivamente a través de Zitadel para prevenir el spam y el abuso automatizado. Estas cuentas no crean perfiles públicos ni funciones sociales.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos de Contenido: ") }
                    )
                    Text("Comentarios o publicaciones en discusiones que usted decida enviar.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos Operativos: ") }
                    )
                    Text("Marcas de tiempo, identificadores internos y metadatos técnicos mínimos (por ejemplo, dirección IP, registros de errores) necesarios para la seguridad, limitación de tasas y diagnóstico.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Comunicaciones: ") }
                    )
                    Text("Correos electrónicos que nos envíe (soporte, apelaciones de moderación o solicitudes de privacidad).")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos de Boletín: ") }
                    )
                    Text("Si se suscribe a actualizaciones, recopilamos su dirección de correo electrónico y sus preferencias.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Cookies y Análisis: ") }
                    )
                    Text("Podemos utilizar herramientas básicas de análisis (por ejemplo, Plausible Analytics, Google Analytics) para comprender los patrones de tráfico y mejorar el Sitio. Estos datos se agregan o anonimizan y no se utilizan para crear perfiles ni con fines publicitarios. Cuando la ley lo requiera, solicitaremos su consentimiento antes de establecer cookies no esenciales.")
                }
            }
        }

        SectionHeader("2. Cómo Usamos la Información", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Utilizamos la información recopilada para:")
            Ul {
                Li { Text("Autenticar el acceso y prevenir el spam o el abuso automatizado.") }
                Li { Text("Mostrar y moderar el contenido de la comunidad.") }
                Li { Text("Operar boletines informativos y correos opcionales (con opción de cancelación).") }
                Li { Text("Mantener el rendimiento, la fiabilidad y la seguridad del Sitio.") }
                Li { Text("Comunicarnos con usted cuando se ponga en contacto con nosotros.") }
                Li { Text("Analizar el tráfico agregado para mejorar el Sitio.") }
            }
            Text("No vendemos ni monetizamos datos personales.")
        }

        SectionHeader("3. Bases Legales para el Procesamiento", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Para los visitantes del EEE, Reino Unido o Suiza, los datos se procesan según una o más de las siguientes bases legales:")
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Contrato: ") }
                    )
                    Text("Para proporcionar y mantener el Sitio y sus funciones.") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Consentimiento: ") }
                    )
                    Text("Para boletines, comunicaciones opcionales y cookies no esenciales.") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Intereses Legítimos: ") }
                    )
                    Text("Para la moderación, prevención del abuso y mejora del Sitio, equilibrado con sus derechos.") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Obligación Legal: ") }
                    )
                    Text("Cuando sea necesario para cumplir con las leyes aplicables.")
                }
            }
        }

        SectionHeader("4. Sus Derechos", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Si se encuentra en el EEE, Reino Unido o Suiza, puede tener los siguientes derechos:")
            Ul {
                Li { Text("Acceder a los datos que mantenemos sobre usted.") }
                Li { Text("Corregir inexactitudes.") }
                Li { Text("Solicitar la eliminación de sus datos (“derecho al olvido”).") }
                Li { Text("Restringir u oponerse a ciertos tipos de procesamiento.") }
                Li { Text("Solicitar una copia de sus datos (portabilidad).") }
                Li { Text("Presentar una queja ante su autoridad local de protección de datos.") }
            }
            Text("Para ejercer cualquiera de estos derechos, comuníquese con ")
            Link("mailto:privacy@sorenkai.com?subject=Rights", "privacy@sorenkai.com")
        }

        SectionHeader("5. Compartir", breakpoint)

        SectionParagraph(breakpoint) {
            Ol {
                Li { Text("No vendemos ni alquilamos datos personales.") }
                Li {
                    Text("Cierta información limitada puede ser procesada en nuestro nombre por proveedores de servicios de confianza que nos ayudan a operar el Sitio, como:")
                    Ul {
                        Li { Text("Zitadel para autenticación y análisis básicos.") }
                        Li { Text("MongoDB Atlas para almacenamiento de base de datos.") }
                        Li { Text("Proveedores de alojamiento/CDN (para entrega y almacenamiento en caché del sitio).") }
                        Li { Text("Proveedores de boletines (si está suscrito).") }
                    }
                }
                Li { Text("Todos estos proveedores actúan bajo acuerdos contractuales y solo pueden procesar datos en nuestro nombre.") }
                Li { Text("Podemos divulgar datos cuando la ley lo exija o sea necesario para proteger los derechos, la seguridad o la integridad del sistema.") }
            }
        }

        SectionHeader("6. Retención", breakpoint)

        SectionParagraph(breakpoint) {
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos de Autenticación: ") }
                    )
                    Text("Se conservan mientras mantenga un inicio de sesión activo.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Contenido: ") }
                    )
                    Text("Se conserva hasta que se elimine o anonimice; el contenido eliminado puede permanecer temporalmente en copias de seguridad.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Datos de Boletín: ") }
                    )
                    Text("Se conservan hasta que se dé de baja.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Registros Operativos: ") }
                    )
                    Text("Normalmente se conservan entre 90 y 180 días, salvo que sea necesario por más tiempo por motivos de seguridad o cumplimiento.")
                }
            }
            Text("Conservamos los datos personales solo durante el tiempo necesario para estos fines o según lo exija la ley; después se eliminan o anonimizan.")
        }

        SectionHeader("7. Menores de Edad", breakpoint)

        SectionParagraph(breakpoint) {
            Text("El Sitio no está dirigido a menores de 13 años en los Estados Unidos ni a menores de la edad legal mínima en su jurisdicción (hasta 16 en algunas partes de la UE). No recopilamos deliberadamente datos personales de menores. Si descubrimos que lo hemos hecho, los eliminaremos de inmediato.")
        }

        SectionHeader("8. Seguridad", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Utilizamos medidas de seguridad de nivel industrial, que incluyen:")
            Ul {
                Li { Text("Cifrado HTTPS para todos los datos en tránsito.") }
                Li { Text("Autenticación y controles de acceso mediante Zitadel.") }
                Li { Text("Permisos basados en roles y principio de menor privilegio en los sistemas backend.") }
            }
            Text("Aunque ningún sistema puede garantizar una seguridad total, tomamos todas las medidas razonables para proteger su información.")
        }

        SectionHeader("9. Transferencias Internacionales", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Los datos pueden procesarse en los Estados Unidos u otros lugares donde operen nuestros proveedores de servicios. Para los usuarios del EEE, Reino Unido o Suiza, utilizamos Cláusulas Contractuales Tipo (SCC) u otros mecanismos aprobados para garantizar una protección adecuada de los datos transferidos fuera de su región.")
        }

        SectionHeader("10. Enlaces Externos", breakpoint)

        SectionParagraph(breakpoint) {
            Text("El Sitio puede contener enlaces a plataformas de terceros, incluidas, entre otras, Substack, Patreon, GitHub o TikTok. No somos responsables de las prácticas de privacidad de esos servicios externos. El uso de dichas plataformas es bajo su propio criterio y sujeto a sus respectivas políticas.")
        }

        SectionHeader("11. Actualizaciones de Esta Política", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Podemos actualizar esta Política de Privacidad periódicamente. Las actualizaciones importantes se anunciarán en el Sitio y la fecha de 'última actualización' se modificará en consecuencia. Su uso continuo del Sitio después de la entrada en vigor de los cambios constituye la aceptación de la Política actualizada.")
        }

        SectionHeader("12. Contacto", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Para preguntas sobre privacidad o solicitudes de datos, comuníquese con: ")
            Link("mailto:privacy@sorenkai.com", "privacy@sorenkai.com")
        }

        SectionParagraph(breakpoint, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row { Text("Para fines del RGPD, el responsable del tratamiento de los datos es:") }
                Row { Text("Soren Kai") }
                Row { Text("Estados Unidos") }
            }
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
