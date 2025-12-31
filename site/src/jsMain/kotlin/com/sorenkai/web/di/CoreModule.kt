package com.sorenkai.web.di

import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.auth.Auth
import com.sorenkai.web.auth.AuthProvider
import org.koin.dsl.module

val coreModule = module {
    single<AuthProvider> { Auth.instance }
    single { ApiClient(get()) }
}
