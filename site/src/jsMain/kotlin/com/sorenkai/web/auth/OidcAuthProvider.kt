package com.sorenkai.web.auth

import kotlin.js.Date
import kotlin.js.Promise
import kotlinx.browser.window
import kotlinx.coroutines.await

// External declarations for oidc-client-ts
@JsModule("oidc-client-ts")
@JsNonModule
external object Oidc {
    class UserManager(settings: UserManagerSettings) {
        fun signinRedirect(args: SigninRedirectArgs = definedExternally): Promise<Unit>

        fun signinRedirectCallback(url: String = definedExternally): Promise<User>

        fun signoutRedirect(args: SignoutRedirectArgs = definedExternally): Promise<Unit>

        fun getUser(): Promise<User?>

        fun signinSilent(args: SigninSilentArgs = definedExternally): Promise<User?>

        fun storeUser(user: User?): Promise<Unit>
    }

    interface UserManagerSettings {
        var authority: String
        var clientId: String
        var redirectUri: String
        var postLogoutRedirectUri: String
        var responseType: String
        var scope: String
        var loadUserInfo: Boolean
        var userStore: WebStorageStateStore
    }

    class WebStorageStateStore(settings: WebStorageStateStoreSettings)

    interface WebStorageStateStoreSettings {
        var store: dynamic
    }

    interface User {
        val accessToken: String
        val refreshToken: String?
        val idToken: String?
        val expiresAt: Double? // Unix timestamp in seconds
        val profile: UserProfile
    }

    interface UserProfile {
        val sub: String
        val email: String?
        val name: String?
    }

    interface SigninRedirectArgs {
        var state: String?
    }

    interface SignoutRedirectArgs {
        var postLogoutRedirectUri: String?
    }

    interface SigninSilentArgs {
        var redirectUri: String?
    }
}

class OidcAuthProvider(
    private val authority: String,
    private val clientId: String
) : AuthProvider {
    private val userManager: Oidc.UserManager by lazy {
        val storeSettings = js("{}").unsafeCast<Oidc.WebStorageStateStoreSettings>()
        storeSettings.store = window.localStorage
        val userStore = Oidc.WebStorageStateStore(storeSettings)

        val settings = js("{}").unsafeCast<Oidc.UserManagerSettings>()
        settings.authority = authority
        settings.clientId = clientId
        settings.redirectUri = "${window.location.origin}/auth/callback"
        settings.postLogoutRedirectUri = window.location.origin
        settings.responseType = "code"
        settings.scope = "openid profile email offline_access"
        settings.loadUserInfo = true
        settings.userStore = userStore

        Oidc.UserManager(settings)
    }

    private var cachedUser: Oidc.User? = null
    private var hydrated = false

    private suspend fun hydrate() {
        if (hydrated) return
        try {
            cachedUser = userManager.getUser().await()
        } catch (e: dynamic) {
            console.error("Hydration failed", e)
        } finally {
            hydrated = true
        }
    }

    override fun login(returnUrl: String?) {
        val args = js("{}").unsafeCast<Oidc.SigninRedirectArgs>()
        if (returnUrl != null) {
            args.state = returnUrl
        }
        userManager.signinRedirect(args)
    }

    override fun logout() {
        userManager.signoutRedirect()
    }

    override fun isAuthenticated(): Boolean {
        val user = cachedUser ?: return false
        val expiresAt = user.expiresAt ?: 0.0
        return expiresAt > (Date.now() / 1000)
    }

    override suspend fun getAccessToken(): String? {
        hydrate()

        var user = cachedUser

        if (user == null || isExpired(user)) {
            try {
                user = userManager.signinSilent().await()
                cachedUser = user
            } catch (e: dynamic) {
                console.error("Silent refresh failed", e)
                userManager.storeUser(null).await()
                cachedUser = null
                return null
            }
        }

        return user?.accessToken
    }

    override fun getUserId(): String? {
        return cachedUser?.profile?.sub
    }

    private fun isExpired(user: Oidc.User): Boolean {
        val expiresAt = user.expiresAt ?: return true
        // Refresh if expiring in less than 30 seconds
        return expiresAt < (Date.now() / 1000 + 30)
    }

    suspend fun handleCallback(): String? {
        return try {
            val user = userManager.signinRedirectCallback().await()
            cachedUser = user
            hydrated = true
            user.unsafeCast<dynamic>().state as? String
        } catch (e: dynamic) {
            console.error("Error handling auth callback", e)
            null
        }
    }
}
