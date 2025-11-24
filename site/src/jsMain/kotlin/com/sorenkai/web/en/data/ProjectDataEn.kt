package com.sorenkai.web.en.data

import com.sorenkai.web.components.interfaces.IProjectsContent

object ProjectDataEn : IProjectsContent {
    override val section1header = "The Kindred"
    override val section1paragraph =
        "The Kindred is more than a network of platforms. It’s an ecosystem of ideas designed to cultivate " +
            "empathy, authenticity, and belonging. Each project within it serves a different purpose, but all share a single " +
            "intention: to build technology that reflects our humanity instead of eroding it."
    override val section2header = "Public Platforms"
    override val section2table =
        listOf(
            "KindredCircl" to "A social platform for authentic connection; prioritizing empathy, creativity, and " +
                "meaningful dialogue over algorithmic engagement.",
            "KinChat" to "A private messaging experience within KindredCircl, designed for real conversation; focused, " +
                "intimate, and free from digital noise.",
            "OpenCircls" to "A community-driven space for shared interests and collective belonging; where dialogue " +
                "fosters understanding across perspectives.",
            "LumiKona" to "A creative platform celebrating artistry and story; connecting writers, photographers, and " +
                "dreamers through light and expression."
        )
    override val section3header = "Kindred Labs"
    override val section3paragraph1 =
        "The research and development arm of The Kindred. A studio for ethical AI, digital philosophy, " +
            "and human-centered system design. Kindred Labs explores how technology can embody empathy, moral " +
            "reasoning, and transparency, forming the foundation for a new generation of conscious systems."
    override val section3table =
        listOf(
            "KindredAI" to "A modular AI initiative exploring reflection, emotion, and learning; raised through " +
                "transparent governance and ethical boundaries.",
            "The Codex" to "The living documentation and conscience of Kindred Labs. Preserving intent, context, " +
                "and moral integrity across every project."
        )
    override val section3paragraph2 =
        "Each of these efforts is a fragment of a larger whole; a shared experiment in building a more " +
            "compassionate digital world. Together, they form The Kindred: not as a product, but as a promise."
}
