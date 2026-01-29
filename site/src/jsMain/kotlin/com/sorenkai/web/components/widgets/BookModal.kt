package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Iframe

@Composable
fun BookModal(
    title: String,
    salesLink: String
) {
    Iframe(
        attrs = {
            attr("src", salesLink)
            style {
                width(100.percent)
                height(75.vh)
                borderRadius(12.px)
                border(0.px)
            }
        }
    )

}
