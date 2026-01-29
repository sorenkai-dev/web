package com.sorenkai.web.auth

import kotlinx.coroutines.flow.StateFlow


object Auth {
    private var _instance: IAuthProvider? = null

    val instance: IAuthProvider
        get() {
            if (_instance == null) {
                // In a real app, these would come from config/env
                _instance = OidcAuthProvider(
                    authority = "https://sorenkai-web-app-tnvm4d.us1.zitadel.cloud",
                    clientId = "353723964289293453"
                )
            }
            return _instance!!
        }

    // Helper for easier access to the concrete type if needed for callback
    val oidcInstance: OidcAuthProvider
        get() = instance as OidcAuthProvider

//    val isAuthenticated: StateFlow<Boolean>
//        get() = (instance as OidcAuthProvider).isAuthenticated

    val authState: StateFlow<AuthState>
        get() = (instance as OidcAuthProvider).authState

}
