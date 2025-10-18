package com.sorenkai.web.i18n

sealed interface Strings {
    val readArticle: String
    val purchaseBook: String
    val comingSoon: String
    val notAvailable: String

    companion object {
        var current: Strings = StringsEn // default language
    }
}
