package com.sorenkai.web.auth

import kotlinx.coroutines.flow.StateFlow

interface IAuthProvider {
    fun login(returnUrl: String? = null)

    fun logout(returnUrl: String? = null)

    suspend fun ensureHydrated()

    val authState: StateFlow<AuthState>

    suspend fun getAccessToken(): String?

    fun getUserId(): String?
    fun getUsername(): String?
    fun getRoles(): List<String>


    val userId: StateFlow<String?>
}
