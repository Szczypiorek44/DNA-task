package io.dnatask.app

import android.app.Application
import io.dnatask.data.di.dataModule
import io.dnatask.domain.di.domainModule
import io.dnatask.presentation.di.presentationModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(listOf(dataModule, domainModule, presentationModule))
        }
    }
}