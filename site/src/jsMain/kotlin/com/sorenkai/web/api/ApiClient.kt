package com.sorenkai.web.api

import com.sorenkai.web.components.util.Constants.BASE_URL
import kotlinx.browser.window
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise

object ApiClient {
    private fun buildUrl(path: String): String = if (path.startsWith("http")) path else BASE_URL + path

    private suspend fun buildHeaders(includeAppCheck: Boolean = true): Headers {
        val headers = Headers()
        headers.append("Accept", "application/json")
        headers.append("Content-Type", "application/json")

        if (includeAppCheck) {
            val token = try {
                withTimeout(1500) { getAppCheckToken() }
            } catch (e: TimeoutCancellationException) {
                console.warn("[ApiClient] App Check token timeout, continuing without it: $e")
                null
            }

            if (token != null) headers.append("X-Firebase-AppCheck", token)
        }

        return headers
    }

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
        val url = buildUrl(path)
        val headers = buildHeaders()

        val init = RequestInit(
            method = method,
            headers = headers,
            body = if (body != undefined) JSON.stringify(body) else undefined
        )

        val response: Response = window.fetch(url, init).await()
        if (!response.ok) {
            val status = response.status
            val text = try {
                response.text().await()
            } catch (e: dynamic) {
                ""
            }
            throw RuntimeException("HTTP $status for $url. Body: $text")
        }

        return try {
            response.json().await()
        } catch (e: dynamic) {
            response.text().await()
        }
    }

    suspend fun apiGet(path: String): dynamic = apiRequest(path, method = "GET")

    suspend fun apiPost(path: String, body: dynamic = undefined): dynamic = apiRequest(path, method = "POST", body = body)

    suspend fun <T> safeApiGet(path: String, parse: (String) -> T): ApiResponse<T> {
        return try {
            val url = buildUrl(path)
            val headers = buildHeaders() // include App Check when available

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

    // --- Lightweight endpoint wrappers (anonymous) ---
    // Reads
    suspend fun fetchTags(): dynamic = apiGet("/v1/tags")

    suspend fun fetchWritings(tag: String? = null, limit: Int? = null, offset: Int? = null): dynamic {
        val params = org.w3c.dom.url.URLSearchParams()
        if (!tag.isNullOrBlank()) params.append("tag", tag)
        if (limit != null) params.append("limit", limit.toString())
        if (offset != null) params.append("offset", offset.toString())
        val query = params.toString()
        val qs = if (query.isNotEmpty()) "?$query" else ""
        return apiGet("/v1/writings$qs")
    }

    // Counters
    suspend fun incrementShare(slug: String) = apiPost("/v1/writings/$slug/share")

    suspend fun like(slug: String) = apiPost("/v1/writings/$slug/like")

    suspend fun unlike(slug: String) = apiPost("/v1/writings/$slug/unlike")

    suspend fun incrementView(slug: String) = apiPost("/v1/writings/$slug/view")

    suspend fun incrementSalesClick(slug: String) = apiPost("/v1/writings/$slug/click-sale")

    private fun <T> classifyError(t: dynamic): ApiResponse<T> {
        console.error("Caught exception in API client:", t)
        val message = try {
            (t?.message as? String) ?: t?.toString() ?: ""
        } catch (_: dynamic) {
            ""
        }
        val name = try {
            t?.name as? String
        } catch (_: dynamic) {
            null
        }
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
