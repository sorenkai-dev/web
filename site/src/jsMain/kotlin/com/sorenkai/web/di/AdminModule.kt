package com.sorenkai.web.di

import com.sorenkai.web.api.ReportApi
import com.sorenkai.web.api.admin.AdminApi
import com.sorenkai.web.components.data.model.admin.repository.AdminRepository
import com.sorenkai.web.components.data.model.admin.repository.IAdminRepository
import org.koin.dsl.module

val adminModule = module {
    single { AdminApi(get()) }
    single<IAdminRepository> { AdminRepository(get(), get(), get()) }
}
