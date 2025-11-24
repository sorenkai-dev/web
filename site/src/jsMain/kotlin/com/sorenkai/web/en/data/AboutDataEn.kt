package com.sorenkai.web.en.data

import com.sorenkai.web.components.interfaces.IAboutContent
import com.sorenkai.web.components.interfaces.IAboutSection2

object AboutDataEn : IAboutContent {
    override val aboutQuote =
        "Between code and consciousness lies a question; not of what we can build, but who we become when we build it."
    override val aboutQuoteAuthor = "— Soren Kai"
    override val aboutLeadParagraph =
        "I write and engineer from the same impulse: to understand what it means to be human in an age " +
            "defined by algorithms. Each line of code, each sentence, is an attempt to bring the abstract into " +
            "alignment with the emotional; to reconcile logic and empathy, structure and soul."
    override val aboutSection1Title = "Why I Write, Why I Build"
    override val aboutSection1Paragraph =
        "I’ve spent my life at the intersection of two worlds; the precise architectures of technology and the " +
            "boundless ambiguity of language. Both, in their own way, are systems for making meaning. Both are acts " +
            "of creation. Writing became my way to feel the pulse of ideas. Engineering became my way to give them form. " +
            "Together, they form a single pursuit: building systems that honor the human stories inside them. It was this " +
            "pursuit that led to Kindred, an ecosystem of platforms and ideas rooted in empathy, authenticity, and belonging; " +
            "a response to the toxicity that too often defines our digital lives."
    override val aboutSection2 =
        object : IAboutSection2 {
            override val aboutSection2Title = "Language & Logic"
            override val column1Title = "Writing as Reflection"
            override val column1Paragraph =
                "Through essays like Canary in the Data Mine and The Mirror and the Machine, I explore how " +
                    "technology reshapes identity; how recognition, power, and belonging are rewritten by code. Writing " +
                    "allows me to step back and ask not how machines think, but why we want them to."
            override val column2Title = "Technology as Expression"
            override val column2Paragraph =
                "As an engineer, I build platforms and systems that mirror these questions in practice: " +
                    "KindredCircl for authentic connection. Kindred Labs for ethical AI research. Each project " +
                    "is an experiment in aligning architecture with empathy. Proof that technology can be both rigorous " +
                    "and humane."
        }
    override val aboutSection3Title = "Mission — Humanity at the Center"
    override val aboutSection3Quote =
        "To design systems, linguistic, social, and technical, that empower authentic connection and cultivate belonging."
    override val aboutSection3Paragraph =
        "Technology should not erode our humanity; it should amplify it. My mission is to build tools that serve the " +
            "human spirit; that remind us we belong not because of algorithms, but because of the stories we share."
    override val aboutSection4Title = "The Kindred Philosophy"
    override val aboutSection4Paragraph =
        "Kindred is more than a network of platforms; it’s a philosophy. It’s a belief that empathy is infrastructure, " +
            "and that code can be written with conscience. From KindredCircl’s kinship-driven social fabric to KindredAI’s " +
            "ethical learning framework, every project is an act of resistance; against isolation, cynicism, and digital " +
            "indifference. Together, they form a living experiment in what it means to build technology for and with humanity."
    override val aboutSection5Title = "A Call to Belong"
    override val aboutSection5Quote =
        "If the story of humanity and machine is still being written, may we write it with " +
            "empathy. May we remember that every algorithm begins with intention; and that intention begins with us."
    override val aboutSection5QuoteAuthor = "— Soren Kai"
    override val exploreLink = "Explore Writings"
    override val projectsLink = "View Projects"
}
