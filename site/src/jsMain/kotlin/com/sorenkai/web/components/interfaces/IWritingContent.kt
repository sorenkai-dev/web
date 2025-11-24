package com.sorenkai.web.components.interfaces

interface IWritingContent {
    val writingQuote: String
    val writingQuoteAuthor: String
    val leadParagraph: String
    val loadingText: String
    val tagLabel: String
    val httpErrorText: String
    val networkErrorText: String
    val unknownErrorText: String
}
