package com.sorenkai.web.di

import com.sorenkai.web.api.WritingApi
import com.sorenkai.web.components.data.model.writing.repository.IWritingRepository
import com.sorenkai.web.components.data.model.writing.repository.WritingRepository
import org.koin.dsl.module

val writingsModule = module {
    single { WritingApi(get()) }
    single<IWritingRepository> { WritingRepository(get(), get()) }
}
