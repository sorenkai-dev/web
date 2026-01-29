package com.sorenkai.web.auth

object Auth {
    private var _instance: AuthProvider? = null

    val instance: AuthProvider
        get() {
            if (_instance == null) {
                // In a real app, these would come from config/env
                _instance = OidcAuthProvider(
                    authority = "https://cas-soren-kai-66m9rg.zitadel.cloud",
                    clientId = "301416805166448643@website"
                )
            }
            return _instance!!
        }

    // Helper for easier access to the concrete type if needed for callback
    val oidcInstance: OidcAuthProvider
        get() = instance as OidcAuthProvider
}
