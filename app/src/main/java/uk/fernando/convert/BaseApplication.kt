package uk.fernando.convert

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.fernando.advertising.MyAdvertising
import uk.fernando.convert.di.KoinModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        MyAdvertising.setDeviceID(listOf("F8941E8AB78DA8CAD502FE0CE3ABF687"))
        MyAdvertising.initialize(this)

        startKoin {
            androidContext(this@BaseApplication)
            modules(KoinModule.allModules())
        }
    }
}