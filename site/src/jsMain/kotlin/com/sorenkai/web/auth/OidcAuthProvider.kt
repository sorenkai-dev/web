package com.sorenkai.web.auth

import kotlin.js.Promise
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// External declarations for oidc-client-ts
@JsModule("oidc-client-ts")
@JsNonModule
external object Oidc {
    class UserManager(settings: UserManagerSettings) {
        fun signinRedirect(args: SigninRedirectArgs = definedExternally): Promise<Unit>

        fun signinRedirectCallback(url: String = definedExternally): Promise<User>

        fun signoutRedirect(args: SignoutRedirectArgs = definedExternally): Promise<Unit>

        fun signoutRedirectCallback(url: String = definedExternally): Promise<SignoutResponse>

        fun getUser(): Promise<User?>

        fun signinSilent(args: SigninSilentArgs = definedExternally): Promise<User?>

        fun storeUser(user: User?): Promise<Unit>
    }

    interface UserManagerSettings {
        var authority: String
        var client_id: String
        var redirect_uri: String
        var post_logout_redirect_uri: String
        var response_type: String
        var scope: String
        var loadUserInfo: Boolean
        var userStore: WebStorageStateStore
    }

    class WebStorageStateStore(settings: WebStorageStateStoreSettings)

    interface WebStorageStateStoreSettings {
        var store: dynamic
    }

    interface User {
        val access_token: String
        val refresh_token: String?
        val id_token: String?
        val username: String
        val expired: Boolean
        val profile: UserProfile
    }

    interface UserProfile {
        val sub: String
        val email: String?
        val name: String?
        val username: String?
        val preferred_username: String?
    }

    interface SigninRedirectArgs {
        var state: String?
    }

    interface SignoutRedirectArgs {
        var postLogoutRedirectUri: String?
        var state: String?
    }

    interface SignoutResponse {
        val state: String?
    }

    interface SigninSilentArgs {
        var redirectUri: String?
    }
}

class OidcAuthProvider(
    private val authority: String,
    private val clientId: String
) : IAuthProvider {
    private val userManager: Oidc.UserManager by lazy {
        val storeSettings = js("{}").unsafeCast<Oidc.WebStorageStateStoreSettings>()
        storeSettings.store = window.localStorage
        val userStore = Oidc.WebStorageStateStore(storeSettings)

        val settings = js("{}").unsafeCast<Oidc.UserManagerSettings>()
        settings.authority = authority
        settings.client_id = clientId
        settings.redirect_uri = "${window.location.origin}/auth/callback"
        settings.post_logout_redirect_uri = "${window.location.origin}/logout/callback"
        settings.response_type = "code"
        settings.scope = "openid profile email offline_access"
        settings.loadUserInfo = true
        settings.userStore = userStore

        Oidc.UserManager(settings)
    }

    private var cachedUser: Oidc.User? = null
    private var hydrated = false
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override val authState: StateFlow<AuthState>
        get() = _authState
    private val _userId = MutableStateFlow<String?>(null)
    override val userId: StateFlow<String?> = _userId

    private var refreshInFlight: Promise<Oidc.User?>? = null

    private suspend fun refreshUser(): Oidc.User? {
        if (refreshInFlight != null) {
            return refreshInFlight!!.await()
        }

        _authState.value = AuthState.Refreshing

        val promise = try {
            userManager.signinSilent()
        } catch (e: dynamic) {
            refreshInFlight = null
            _authState.value = AuthState.Unauthenticated
            return null
        }

        refreshInFlight = promise

        return try {
            val user = promise.await()
            cachedUser = user
            hydrated = true

            _authState.value =
                if (user != null && !user.expired) {
                    AuthState.Authenticated
                } else {
                    AuthState.Unauthenticated
                }

            user
        } catch (e: dynamic) {
            console.error("Token refresh failed", e)
            cachedUser = null
            _authState.value = AuthState.Unauthenticated
            null
        } finally {
            refreshInFlight = null
        }
    }


    private suspend fun hydrate() {
        if (hydrated) return
        try {
            cachedUser = userManager.getUser().await()

            _authState.value =
                if (cachedUser?.expired == false) {
                    AuthState.Authenticated
                } else {
                    AuthState.Unauthenticated
                }
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
        println("redirect url ${window.location.origin}")
    }

    override fun logout(returnUrl: String?) {
        _authState.value = AuthState.Unauthenticated
        val args = js("{}").unsafeCast<Oidc.SignoutRedirectArgs>()
        if (returnUrl != null) {
            args.state = returnUrl
        }
        userManager.signoutRedirect(args)
    }

    suspend fun handleLogoutCallback(): String? {
        return try {
            val response = userManager.signoutRedirectCallback().await()
            response.state
        } catch (e: dynamic) {
            console.error("Error handling logout callback", e)
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        hydrate()

        val user = cachedUser

        // Case 1: valid token
        if (user != null && !user.expired) {
            _authState.value = AuthState.Authenticated
            return user.access_token
        }

        // Case 2: token expired but refresh token exists
        if (user?.refresh_token != null) {
            val refreshedUser = refreshUser()
            return refreshedUser?.access_token
        }

        // Case 3: no valid auth possible
        cachedUser = null
        _authState.value = AuthState.Unauthenticated
        return null
    }

    override fun getUserId(): String? {
        _userId.value = cachedUser?.profile?.sub
        return cachedUser?.profile?.sub
    }

    override fun getUsername(): String? {
        val profile = cachedUser?.profile ?: return null
        return profile.name ?: profile.username ?: profile.preferred_username ?: cachedUser?.username
    }

    override fun getRoles(): List<String> {
        val profile = cachedUser?.profile?.unsafeCast<dynamic>() ?: return emptyList()

        // Zitadel roles are in urn:zitadel:iam:org:project:{projectId}:roles or urn:zitadel:iam:org:project:roles
        // They are objects where keys are roles and values are another object (often containing "orgId")
        val roles = mutableListOf<String>()

        val keys = js("Object.keys(profile)").unsafeCast<Array<String>>()
        keys.forEach { key ->
            if (key.startsWith("urn:zitadel:iam:org:project:") && key.endsWith(":roles")) {
                val rolesObj = profile[key]
                if (rolesObj != null && rolesObj != undefined) {
                    val roleNames = js("Object.keys(rolesObj)").unsafeCast<Array<String>>()
                    roleNames.forEach { role -> roles.add(role) }
                }
            } else if (key == "urn:zitadel:iam:org:project:roles") {
                val rolesObj = profile[key]
                if (rolesObj != null && rolesObj != undefined) {
                    val roleNames = js("Object.keys(rolesObj)").unsafeCast<Array<String>>()
                    roleNames.forEach { role -> roles.add(role) }
                }
            }
        }

        return roles.distinct()
    }

    override suspend fun ensureHydrated() {
        hydrate()
    }

    suspend fun handleCallback(): String? {
        return try {
            val user = userManager.signinRedirectCallback().await()
            cachedUser = user
            hydrated = true
            _authState.value =
                if (!user.expired) AuthState.Authenticated
                else AuthState.Unauthenticated
            user.unsafeCast<dynamic>().state as? String
        } catch (e: dynamic) {
            _authState.value = AuthState.Unauthenticated
            console.error("Error handling auth callback", e)
            null
        }
    }
}
