package com.sorenkai.web.components.interfaces

interface IGuidelines {
    val title: String
    val openingParagraph: String
    val section1heading: String
    val section1paragraph: String
    val section2heading: String
    val section2paragraph1: String
    val section2ul: List<String>
    val section2paragraph2: String
    val section3heading: String
    val section3paragraph: String
    val prohibitedContent: IProhibitedContent
    val section4heading: String
    val section4paragraph: String
    val section5heading: String
    val section5paragraph1: String
    val section5UL1: List<String>
    val section5paragraph2: String
    val section5UL2: List<String>
    val section5paragraph3: String
    val section6heading: String
    val section6paragraph: String
    val section7heading: String
    val section7paragraph1: String
    val section7UL: List<String>
    val section7paragraph2: String
    val closingHeading: String
    val closingParagraph1: String
    val closingParagraph2: String
}

interface IProhibitedContent {
    val section1heading: String
    val uList1: List<String>
    val section2heading: String
    val section2paragraph1: String
    val section2Ulist: List<String>
    val section2paragraph2: String
    val section3heading: String
    val uList3: List<String>
    val section4heading: String
    val section4paragraph1: String
    val section4UL1: List<String>
    val section4paragraph2: String
    val section4UL2: List<String>
    val section4paragraph3: String
    val section5heading: String
    val section5UL: List<String>
    val section6heading: String
    val section6UL: List<String>
}
