package com.sorenkai.web.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun StatsRow(
    breakpoint: Breakpoint,
    likesText: String,
    likes: MutableState<Int>,
    viewsText: String,
    views: MutableState<Int>,
    commentsText: String,
    comments: Int,
    shareText: String,
    shares: MutableState<Int>,
) {
    if (breakpoint < Breakpoint.SM) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fontSize(0.75.cssRem)
                .padding(leftRight = 2.cssRem),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column (
                horizontalAlignment = Alignment.Start
            ) { Text("$likesText: ${likes.value}") }
            Column (
                horizontalAlignment = Alignment.End
            ) { Text("$shareText: ${shares.value}") }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fontSize(0.8.cssRem)
                .padding(leftRight = 2.cssRem),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column (
                horizontalAlignment = Alignment.Start
            ) { Text("$viewsText: ${views.value}") }
            Column (
                horizontalAlignment = Alignment.End
            ) { Text("$commentsText: $comments") }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(leftRight = 2.cssRem),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column { Text("$likesText: ${likes.value}") }
            Column { Text("$viewsText: ${views.value}") }
            Column { Text("$commentsText: $comments") }
            Column { Text("$shareText: ${shares.value}") }
        }
    }

}
