package com.sorenkai.web.components.interfaces

interface IAboutContent {
    val aboutQuote: String
    val aboutQuoteAuthor: String
    val aboutLeadParagraph: String
    val aboutSection1Title: String
    val aboutSection1Paragraph: String
    val aboutSection2: IAboutSection2
    val aboutSection3Title: String
    val aboutSection3Quote: String
    val aboutSection3Paragraph: String
    val aboutSection4Title: String
    val aboutSection4Paragraph: String
    val aboutSection5Title: String
    val aboutSection5Quote: String
    val aboutSection5QuoteAuthor: String
    val exploreLink: String
    val projectsLink: String
}

interface IAboutSection2 {
    val aboutSection2Title: String
    val column1Title: String
    val column1Paragraph: String
    val column2Title: String
    val column2Paragraph: String
}
