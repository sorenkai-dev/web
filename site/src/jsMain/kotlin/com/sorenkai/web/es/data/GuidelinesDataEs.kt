package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.IGuidelines
import com.sorenkai.web.components.interfaces.IProhibitedContent

object GuidelinesDataEs : IGuidelines {
    override val title = "Normas de la Comunidad"
    override val openingParagraph =
    "Welcome to SorenKai.com, an independent publication and community space focused on " +
        "technology, writing, and the human side of AI. These Community Guidelines exist to " +
        "set clear expectations for participation and to protect the quality of discussion " +
        "on this site. By participating in community discussions, you agree to follow these " +
        "guidelines. Violations may result in moderation actions as described below."
    override val section1heading = "Propósito"
    override val section1paragraph =
        "Esta comunidad existe para fomentar conversaciones reflexivas y respetuosas. El " +
            "objetivo es el diálogo, no la confrontación, y la participación asume buena fe, " +
            "curiosidad y respeto mutuo. Al publicar aquí, aceptas cumplir estas normas."
    override val section2heading = "Conducta Esperada"
    override val section2paragraph1 = "Esperamos que las personas participantes:"
    override val section2ul =
        listOf(
            "Participen con respeto, incluso cuando haya desacuerdos",
            "Cuestionen ideas, no ataquen a personas",
            "Asuman buena intención salvo que se demuestre claramente lo contrario",
            "Mantengan las conversaciones relevantes al tema tratado",
            "Contribuyan de manera constructiva y no disruptiva"
        )
    override val section2paragraph2 = "El desacuerdo es bienvenido. La falta de respeto no."
    override val section3heading = "Contenido y Comportamiento Prohibidos"
    override val section3paragraph = "Lo siguiente no está permitido:"
    override val prohibitedContent = object : IProhibitedContent {
        override val section1heading = "Acoso y Abuso"
        override val uList1 =
            listOf(
                "Ataques personales, insultos o intimidación",
                "Acoso dirigido o participación reiterada de mala fe",
                "Amenazas de violencia o daño"
            )
        override val section2heading = "Odio, Deshumanización y Desprecio"
        override val section2paragraph1 =
            "Esta comunidad no permite contenido que sea denigrante, deshumanizante o " +
                "despectivo hacia personas o grupos. Esto incluye:"
        override val section2Ulist =
            listOf(
                "Insultos, agravios o etiquetas peyorativas dirigidas a grupos de personas",
                "Lenguaje deshumanizante, como reducir a personas a animales, demonios, plagas o caricaturas",
                "Descalificaciones políticas ofensivas (por ejemplo, apodos burlones u obscenos para grupos ideológicos o votantes)",
                "Generalizaciones amplias que retraten a grupos de personas como inherentemente estúpidas, malvadas o subhumanas"
            )
        override val section2paragraph2 =
            "Este estándar se aplica independientemente de la afiliación política, ideología o " +
                "creencias. Puedes criticar ideas, políticas, movimientos y figuras públicas. " +
                "No puedes atacar a las personas como categoría ni usar el desprecio como " +
                "sustituto de un argumento. Si tu punto no puede expresarse sin insultos, no " +
                "pertenece a este espacio."
        override val section3heading = "Violencia y Actividad Ilegal"
        override val uList3 =
            listOf(
                "Amenazas o glorificación de la violencia",
                "Contenido que promueva o facilite actividades ilegales"
            )
        override val section4heading = "Contenido Sexual"
        override val section4paragraph1 =
            "Esta comunidad no es un espacio para contenido sexual. No está permitido:"
        override val section4UL1 =
            listOf(
                "Material sexualizado o explícito",
                "Descripciones gráficas de actos sexuales",
                "Contenido destinado a provocar excitación o impacto"
            )
        override val section4paragraph2 =
            "Dicho esto, se permite la discusión seria sobre violencia sexual, agresión o abuso cuando sea:"
        override val section4UL2 =
            listOf(
                "Factual",
                "Contextual",
                "Relevante para un hecho noticioso, caso legal, análisis histórico o tema social más amplio"
            )
        override val section4paragraph3 =
            "Las discusiones deben centrarse en los hechos, las consecuencias y las " +
                "implicaciones, no en la descripción explícita de los actos. No exigimos " +
                "eufemismos ni lenguaje codificado. Los temas maduros pueden discutirse de " +
                "manera madura."
        override val section5heading = "Spam y Manipulación"
        override val section5UL =
            listOf(
                "Publicidad no solicitada",
                "Publicaciones repetitivas o disruptivas",
                "Intentos coordinados de desviar o dominar la conversación",
                "Desinformación deliberada con intención de engañar"
            )
        override val section6heading = "Suplantación de Identidad"
        override val section6UL =
            listOf(
                "Hacerse pasar por otra persona o entidad de manera engañosa o perjudicial"
            )
    }
    override val section4heading = "Moderación"
    override val section4paragraph =
        "La moderación existe para proteger la salud de la conversación. Esta es una " +
            "comunidad de gestión privada. La participación es un privilegio, no un derecho. " +
            "Las decisiones de moderación se guían por la calidad del diálogo, no por " +
            "reclamos de neutralidad o de “libertad de expresión”. El contenido puede ser " +
            "eliminado si socava una discusión reflexiva, incluso si no es ilegal. Si tu " +
            "participación reduce de forma constante la calidad del intercambio, puede ser " +
            "limitada o eliminada."
    override val section5heading = "Temas de Alto Conflicto y Bloqueo de Hilos"
    override val section5paragraph1 =
        "Algunos temas tienen un patrón bien establecido de degenerar en posturas de " +
            "guerra cultural, ataques personales o argumentación de mala fe, " +
            "independientemente de la intención de la publicación original. Cuando esto ocurre, " +
            "los moderadores pueden:"
    override val section5UL1 =
        listOf(
            "Bloquear el hilo para evitar que continúe la discusión",
            "Eliminar la publicación y solicitar a la persona autora que la edite para evitar confusión adicional"
        )
    override val section5paragraph2 =
        "Esto puede suceder incluso si los comentarios individuales aún no han violado " +
            "reglas específicas. Los temas que con frecuencia entran en esta categoría " +
            "incluyen, entre otros:"
    override val section5UL2 =
        listOf(
            "Debates sobre identidad de género y temas trans planteados como guerra cultural",
            "Disputas políticas altamente polarizadas basadas en identidades",
            "Publicaciones cuyo objetivo principal es provocar, incitar o anotar puntos ideológicos"
        )
    override val section5paragraph3 =
        "Bloquear un hilo no es un juicio sobre la legitimidad del tema en sí. Es una " +
            "evaluación de si la conversación, tal como se está desarrollando, puede " +
            "mantenerse productiva y respetuosa en este espacio. Las acciones se toman en " +
            "función del comportamiento y el contexto, no de la ideología u opinión. Las " +
            "decisiones de moderación son definitivas."
    override val section6heading = "Reportes"
    override val section6paragraph =
        "Si encuentras contenido que viola estas normas, repórtalo utilizando las " +
            "herramientas disponibles o contacta al administrador del sitio. Los reportes " +
            "deben hacerse de buena fe. El abuso de los mecanismos de reporte puede dar lugar " +
            "a medidas disciplinarias."
    override val section7heading = "Consecuencias"
    override val section7paragraph1 = "Las infracciones pueden dar lugar a:"
    override val section7UL =
        listOf(
            "Eliminación de contenido",
            "Suspensión temporal",
            "Expulsión permanente de la comunidad"
        )
    override val section7paragraph2 =
        "Se tendrá en cuenta la gravedad y la frecuencia de las infracciones."
    override val closingHeading = "Notas Finales"
    override val closingParagraph1 =
        "Este espacio está curado de forma intencional. Existe para personas que desean " +
            "pensar, dialogar y discrepar sin recurrir al desprecio, la burla o la " +
            "deshumanización. Si eso resulta restrictivo, esta comunidad puede no ser la " +
            "adecuada, y eso está bien."
    override val closingParagraph2 =
        "Sé curioso. Sé respetuoso. Discrepa con criterio."
}
