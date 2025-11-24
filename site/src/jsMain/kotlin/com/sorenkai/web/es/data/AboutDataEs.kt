package com.sorenkai.web.es.data

import com.sorenkai.web.components.interfaces.IAboutContent
import com.sorenkai.web.components.interfaces.IAboutSection2

object AboutDataEs : IAboutContent {
    override val aboutQuote =
        "Entre el código y la conciencia hay una pregunta; no sobre lo que podemos construir, " +
            "sino sobre quiénes nos convertimos cuando lo hacemos."
    override val aboutQuoteAuthor = "— Soren Kai"
    override val aboutLeadParagraph =
        "Escribo y programo desde el mismo impulso: entender qué significa ser humano en una era " +
            "definida por algoritmos. Cada línea de código, cada frase, es un intento de alinear lo abstracto " +
            "con lo emocional; de reconciliar la lógica con la empatía, la estructura con el alma."
    override val aboutSection1Title = "Por Qué Escribo, Por Qué Construyo"
    override val aboutSection1Paragraph =
        "He pasado mi vida en la intersección de dos mundos: las arquitecturas precisas de la tecnología y la ambigüedad " +
            "ilimitada del lenguaje. Ambos, a su manera, son sistemas para dar significado. Ambos son actos de creación. " +
            "La escritura se convirtió en mi forma de sentir el pulso de las ideas. La ingeniería, en mi forma de darles " +
            "forma. Juntas, forman una sola búsqueda: construir sistemas que honren las historias humanas que contienen. " +
            "Fue esta búsqueda la que llevó a Kindred, un ecosistema de plataformas e ideas basadas en la " +
            "empatía, la autenticidad y el sentido de pertenencia; una respuesta a la toxicidad que con demasiada " +
            "frecuencia define nuestras vidas digitales."
    override val aboutSection2 = object : IAboutSection2 {
        override val aboutSection2Title = "Lenguaje y Lógica"
        override val column1Title = "La Escritura como Reflexión"
        override val column1Paragraph =
            "A través de ensayos como Canary in the Data Mine y The Mirror and the Machine, exploro " +
                "cómo la tecnología redefine la identidad; cómo el reconocimiento, el poder y la pertenencia son " +
                "reescritos por el código. Escribir me permite dar un paso atrás y preguntarme no cómo piensan las " +
                "máquinas, sino por qué queremos que lo hagan."
        override val column2Title = "La Tecnología como Expresión"
        override val column2Paragraph =
            "Como ingeniero, construyo plataformas y sistemas que reflejan estas preguntas en la " +
                "práctica: KindredCircl para la conexión auténtica. Kindred Labs para la investigación de IA ética. " +
                "Cada proyecto es un experimento en alinear la arquitectura con la empatía. Una prueba de que la " +
                "tecnología puede ser rigurosa y, al mismo tiempo, humana."
    }
    override val aboutSection3Title = "Misión — La Humanidad al Centro"
    override val aboutSection3Quote =
        "Diseñar sistemas —lingüísticos, sociales y técnicos— que fortalezcan la conexión auténtica " +
            "y cultiven el sentido de pertenencia."
    override val aboutSection3Paragraph =
        "La tecnología no debe erosionar nuestra humanidad; debe amplificarla. Mi misión es construir " +
            "herramientas que sirvan al espíritu humano; que nos recuerden que pertenecemos no por los algoritmos, " +
            "sino por las historias que compartimos."
    override val aboutSection4Title = "La Filosofía de Kindred"
    override val aboutSection4Paragraph =
        "Kindred es más que una red de plataformas; es una filosofía. Una creencia de que la empatía es infraestructura " +
            "y que el código puede escribirse con conciencia. Desde la red social basada en parentesco de KindredCircl " +
            "hasta el marco ético de aprendizaje de KindredAI, cada proyecto es un acto de resistencia: contra la soledad, " +
            " el cinismo y la indiferencia digital. Juntos, forman un experimento vivo sobre lo que significa construir " +
            "tecnología para y con la humanidad."
    override val aboutSection5Title = "Un Llamado a Pertenecer"
    override val aboutSection5Quote =
        "Si la historia de la humanidad y las máquinas aún se está escribiendo, que la " +
            "escribamos con empatía. Recordemos que todo algoritmo comienza con una intención; y que esa intención " +
            "comienza con nosotros."
    override val aboutSection5QuoteAuthor = "— Soren Kai"
    override val exploreLink = "Explorar Escritos"
    override val projectsLink = "Ver Proyectos"
}
