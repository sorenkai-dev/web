package com.sorenkai.web.di

import com.sorenkai.web.api.ReportApi
import com.sorenkai.web.components.data.model.report.repository.IReportRepository
import com.sorenkai.web.components.data.model.report.repository.ReportRepository
import org.koin.dsl.module

val reportsModule = module {
    single { ReportApi(get()) }
    single<IReportRepository> { ReportRepository(get(), get()) }
}
