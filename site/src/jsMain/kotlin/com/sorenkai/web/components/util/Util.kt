package com.sorenkai.web.components.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Text
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJSDate

// Utility function to safely format the date string
@OptIn(ExperimentalTime::class)
fun formatIsoDateToHumanReadable(isoDateString: String): String {
    // 1. Parse the ISO String to a Kotlinx-datetime Instant
    val options = js(
        """
        { 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric'
        }
        """
    )

    val instant = try {
        Instant.parse(isoDateString)
    } catch (e: Exception) {
        return "Invalid Date"
    }

    val jsDate = instant.toJSDate()

    // 2. CRITICAL FIX: Use 'a' for jsDate and 'b' for options in the implicit function call.
    // The expression inside js(...) is treated as the function body.
    // The arguments passed in the outer (jsDate, options) are mapped to 'a' and 'b'.
    return js(
        """
        (function(jsDate, options) {
            // Check if jsDate is valid before calling format
            if (jsDate instanceof Date) {
                 return new Intl.DateTimeFormat(undefined, options).format(jsDate);
            }
            return 'Invalid Date Object';
        })
        """
    )(jsDate, options) as String
}

@Composable
fun DateText(isoDateString: String) {
    val formattedDate = formatIsoDateToHumanReadable(isoDateString)
    Text("Last Published: $formattedDate") // Example usage in your Text Composable
}

