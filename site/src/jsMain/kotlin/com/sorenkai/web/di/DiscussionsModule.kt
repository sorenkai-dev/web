package com.sorenkai.web.di

import com.sorenkai.web.api.DiscussionApi
import com.sorenkai.web.api.WritingApi
import com.sorenkai.web.components.data.model.community.discussions.repository.IDiscussionRepository
import com.sorenkai.web.components.data.model.community.discussions.repository.DiscussionRepository
import com.sorenkai.web.components.data.model.community.discussions.service.DiscussionService
import org.koin.dsl.module

val discussionsModule = module {
    single { DiscussionService() }
    single { DiscussionApi() }
    single { WritingApi() }
    single<IDiscussionRepository> { DiscussionRepository(get<DiscussionService>(), get<DiscussionApi>()) }
}
