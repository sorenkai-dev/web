package com.sorenkai.web.en.data

import com.sorenkai.web.components.interfaces.IWritingContent

object WritingDataEn : IWritingContent {
    override val writingQuote =
        "\"The fact of storytelling hints at a fundamental human unease, hints at human imperfection. Where there " +
            "is perfection there is no story to tell.\""
    override val writingQuoteAuthor = "— Ben Okri"
    override val leadParagraph =
        "This page is a portfolio of my published and ongoing work. It brings together essays, reflections, " +
            "and contributions that explore the intersections of technology, culture, and belonging. Some pieces " +
            "are deeply technical, others more reflective, but all are united by the same question: what does it " +
            "mean to remain fully human in a world shaped by machines? Each entry includes a short synopsis and a " +
            "link to read or purchase the full work."
    override val loadingText = "Loading writings..."
    override val tagLabel = "All"
    override val httpErrorText = "Server error"
    override val networkErrorText = "Network issue, please try again."
    override val unknownErrorText = "Unexpected error occurred."
}
