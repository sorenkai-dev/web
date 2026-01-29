package com.sorenkai.web.components.data.ui

// Note: When the user closes the side menu, we don't immediately stop rendering it (at which point it would disappear
// abruptly). Instead, we start animating it out and only stop rendering it when the animation is complete.
enum class RevealState {
    HIDDEN,
    VISIBLE,
    HIDING;

    fun close() = when (this) {
        HIDDEN -> HIDDEN
        VISIBLE -> HIDING
        HIDING -> HIDING
    }
}
