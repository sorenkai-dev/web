package com.sorenkai.web.di

import com.sorenkai.web.api.DiscussionApi
import com.sorenkai.web.api.dto.discussions.mapper.DiscussionPresentationMapper
import com.sorenkai.web.repository.DiscussionRepository
import com.sorenkai.web.repository.interfaces.IDiscussionRepository
import com.sorenkai.web.services.DiscussionService
import com.sorenkai.web.ui.viewmodels.DiscussionsViewModel
import org.koin.dsl.module

val discussionsModule = module {
    single { DiscussionService() }
    single { DiscussionApi(get()) }
    single { DiscussionPresentationMapper() }
    single<IDiscussionRepository> { DiscussionRepository(get(), get()) }
    factory { DiscussionsViewModel(get(), get(), get()) }
}
