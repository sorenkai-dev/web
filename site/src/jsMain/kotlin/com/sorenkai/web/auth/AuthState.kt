package com.sorenkai.web.auth

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object Refreshing : AuthState()
    data class Error(val cause: Throwable? = null) : AuthState()
}
