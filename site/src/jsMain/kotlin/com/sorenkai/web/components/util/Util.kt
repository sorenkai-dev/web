package com.sorenkai.web.components.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Text
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Utility function to safely format the date string
@OptIn(ExperimentalTime::class)
fun formatInstantToHumanReadable(instant: Instant): String {
    val options = js(
        """
        {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        }
        """
    )

    val millis = instant.toEpochMilliseconds().toDouble()
    val jsDate = js("new Date(millis)")

    return js(
        """
        (function(jsDate, options) {
            if (jsDate instanceof Date && !isNaN(jsDate.getTime())) {
                 return new Intl.DateTimeFormat(undefined, options).format(jsDate);
            }
            return 'Invalid Date';
        })
        """
    )(jsDate, options) as String
}

@OptIn(ExperimentalTime::class)
fun formatIsoDateToHumanReadable(isoDateString: String): String {
    val instant = try {
        Instant.parse(isoDateString)
    } catch (e: Exception) {
        return "Invalid Date"
    }
    return formatInstantToHumanReadable(instant)
}

@OptIn(ExperimentalTime::class)
fun formatInstantToHumanReadableSafe(instant: Instant?): String {
    return instant?.let { formatInstantToHumanReadable(it) } ?: "N/A"
}

@Composable
fun DateText(isoDateString: String) {
    val formattedDate = formatIsoDateToHumanReadable(isoDateString)
    Text("Last Published: $formattedDate") // Example usage in your Text Composable
}
