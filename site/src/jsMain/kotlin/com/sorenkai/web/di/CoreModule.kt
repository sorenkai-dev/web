package com.sorenkai.web.di

import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.auth.Auth
import com.sorenkai.web.auth.IAuthProvider
import org.koin.dsl.module

val coreModule = module {
    single<IAuthProvider> { Auth.instance }
    single { ApiClient(get()) }
}
