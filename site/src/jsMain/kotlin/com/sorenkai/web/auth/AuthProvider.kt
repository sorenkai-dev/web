package com.sorenkai.web.auth

interface AuthProvider {
    fun login(returnUrl: String? = null)

    fun logout()

    fun isAuthenticated(): Boolean

    suspend fun getAccessToken(): String?
}
