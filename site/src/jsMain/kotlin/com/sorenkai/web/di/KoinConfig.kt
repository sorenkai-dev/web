package com.sorenkai.web.di

import com.varabyte.kobweb.core.init.InitKobweb
import org.koin.core.context.startKoin

@InitKobweb
fun initKoin() {
    startKoin {
        modules(
            coreModule,
            writingsModule,
            discussionsModule,
            reportsModule,
            adminModule
        )
    }
}
