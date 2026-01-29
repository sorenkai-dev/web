package com.sorenkai.web.di

import com.sorenkai.web.api.WritingApi
import com.sorenkai.web.repository.WritingRepository
import com.sorenkai.web.repository.interfaces.IWritingRepository
import com.sorenkai.web.ui.viewmodels.WritingsViewModel
import org.koin.dsl.module

val writingsModule = module {
    single { WritingApi(get()) }
    single<IWritingRepository> { WritingRepository(get(), get()) }
    factory { WritingsViewModel(get(),get(), get()) }
}
