package com.sorenkai.web.api

import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.util.Constants.BASE_URL
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response

class ApiClient(private val auth: IAuthProvider) {
    private fun buildUrl(path: String): String = if (path.startsWith("http")) path else BASE_URL + path

    private suspend fun buildHeaders(): Headers {
        val headers = Headers()
        headers.append("Accept", "application/json")
        headers.append("Content-Type", "application/json")
        auth.getAccessToken()?.let { token ->
            headers.set("Authorization", "Bearer $token")
        }

        return headers
    }

    suspend fun <T> patch(path: String, body: dynamic = undefined, parse: (String) -> T): ApiResponse<T> =
        request(path, "PATCH", body = body, parse = parse)

    suspend fun <T> request(
        path: String,
        method: String,
        body: dynamic = undefined,
        parse: (String) -> T
    ): ApiResponse<T> {
        return try {
            val url = buildUrl(path)
            val headers = buildHeaders()

            val init = RequestInit(
                method = method,
                headers = headers,
                body = if (body != undefined) {
                    if (js("typeof body === 'string'") as Boolean) {
                        body
                    } else {
                        JSON.stringify(body)
                    }
                } else {
                    undefined
                }
            )

            val response: Response = window.fetch(url, init).await()
            val responseText = response.text().await()

            if (!response.ok) {
                return ApiResponse.HttpError(response.status.toInt(), responseText.ifBlank { "HTTP ${response.status}" })
            }

            ApiResponse.Success(parse(responseText))
        } catch (t: dynamic) {
            classifyError(t)
        }
    }

    suspend fun <T> get(path: String, parse: (String) -> T): ApiResponse<T> =
        request(path, "GET", parse = parse)

    suspend fun <T> post(path: String, body: dynamic = undefined, parse: (String) -> T): ApiResponse<T> =
        request(path, "POST", body = body, parse = parse)

    suspend fun <T> put(path: String, body: dynamic = undefined, parse: (String) -> T): ApiResponse<T> =
        request(path, "PUT", body = body, parse = parse)

    suspend fun <T> delete(path: String, parse: (String) -> T): ApiResponse<T> =
        request(path, "DELETE", parse = parse)

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
