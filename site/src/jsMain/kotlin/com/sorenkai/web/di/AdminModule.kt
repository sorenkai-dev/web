package com.sorenkai.web.di

import com.sorenkai.web.api.ReportApi
import com.sorenkai.web.api.AdminApi
import com.sorenkai.web.repository.AdminRepository
import com.sorenkai.web.repository.interfaces.IAdminRepository
import org.koin.dsl.module

val adminModule = module {
    single { AdminApi(get()) }
    single<IAdminRepository> { AdminRepository(get(), get(), get()) }
}
