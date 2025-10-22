package com.sorenkai.web.api

import com.sorenkai.web.components.util.Constants.BASE_URL
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

object ApiClient {

    private suspend fun waitForAppCheck(tries: Int = 3, delayMs: Long = 300): dynamic {
        var attempt = 0
        while (attempt < tries) {
            val appCheck = window.asDynamic().appCheck
            val getTokenFn = window.asDynamic().firebase?.appCheck?.getToken
            if ((appCheck != undefined && appCheck != null) && (getTokenFn != undefined && getTokenFn != null)) return appCheck
            attempt++
            delay(delayMs)
        }
        return null
    }

    private suspend fun getAppCheckToken(tries: Int = 3, delayMs: Long = 300): String? {
        var attempt = 0
        var lastError: dynamic = null
        while (attempt < tries) {
            try {
                val appCheck = waitForAppCheck(tries = 1, delayMs = delayMs) ?: throw IllegalStateException("App Check not initialized yet")
                val getToken = window.asDynamic().firebase?.appCheck?.getToken
                if (getToken == undefined || getToken == null) throw IllegalStateException("Firebase App Check getToken not available")
                val tokenPromise = getToken(appCheck, false) as Promise<dynamic>
                val tokenObj = tokenPromise.await()
                val token = tokenObj.token as String
                if (token.isNotBlank()) return token
            } catch (t: dynamic) {
                lastError = t
                // fall through to retry after delay
            }
            attempt++
            delay(delayMs)
        }
        console.warn("[ApiClient] Failed to acquire App Check token after $tries attempts", lastError)
        return null
    }

    private suspend fun apiRequest(path: String, method: String = "GET", body: dynamic = undefined): dynamic {
        val url = if (path.startsWith("http")) path else BASE_URL + path

        // Prepare headers, including App Check token if available
        val headers = Headers()
        headers.append("Accept", "application/json")
        headers.append("Content-Type", "application/json")

        val token = getAppCheckToken()
        if (token != null) {
            headers.append("X-Firebase-AppCheck", token)
        } else {
            console.warn("[ApiClient] Proceeding without App Check token; request may be rejected by server: $url")
        }

        val init = RequestInit(
            method = method,
            headers = headers,
            body = if (body != undefined) JSON.stringify(body) else undefined
        )

        val response: Response = window.fetch(url, init).await()
        if (!response.ok) {
            val status = response.status
            val text = try { response.text().await() } catch (e: dynamic) { "" }
            throw RuntimeException("HTTP $status for $url. Body: $text")
        }
        // Try parse as JSON, fallback to text
        return try {
            response.json().await()
        } catch (e: dynamic) {
            response.text().await()
        }
    }

    suspend fun apiGet(path: String): dynamic = apiRequest(path, method = "GET")

    suspend fun <T> safeApiGet(path: String, parse: (String) -> T): ApiResponse<T> {
        return try {
            val url = if (path.startsWith("http")) path else BASE_URL + path

            val headers = Headers()
            headers.append("Accept", "application/json")
            headers.append("Content-Type", "application/json")

            val init = RequestInit(
                method = "GET",
                headers = headers
            )

            val response: Response = window.fetch(url, init).await()
            val responseText = response.text().await()

            if (!response.ok) {
                val status = response.status
                return ApiResponse.HttpError(status.toInt(), if (responseText.isNotBlank()) responseText else "HTTP $status")
            }

            val data = parse(responseText)
            ApiResponse.Success(data)
        } catch (t: dynamic) {
            classifyError(t)
        }
    }

    private fun <T> classifyError(t: dynamic): ApiResponse<T> {
        console.error("Caught exception in API client:", t)
        val message = try { (t?.message as? String) ?: t?.toString() ?: "" } catch (_: dynamic) { "" }
        val name = try { t?.name as? String } catch (_: dynamic) { null }
        val httpRegex = Regex("HTTP \\d+")
        val match = message.let { httpRegex.find(it) }
        if (match != null) {
            val code = match.value.substringAfter(" ").toIntOrNull() ?: 500
            @Suppress("UNCHECKED_CAST")
            return ApiResponse.HttpError(code, message) as ApiResponse<T>
        }
        if (name == "TypeError" || message.contains("NetworkError", ignoreCase = true)) {
            @Suppress("UNCHECKED_CAST")
            return ApiResponse.NetworkError(message.ifBlank { "Network error" }) as ApiResponse<T>
        }
        @Suppress("UNCHECKED_CAST")
        return ApiResponse.UnknownError(message.ifBlank { "Unknown error" }) as ApiResponse<T>
    }
}
