package uk.fernando.convert.di


import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import uk.fernando.convert.BuildConfig
import uk.fernando.convert.database.MyDatabase
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.datastore.PrefsStoreImpl
import uk.fernando.convert.repository.*
import uk.fernando.convert.usecase.AddUnitUseCase
import uk.fernando.convert.usecase.GetUnitsUseCase
import uk.fernando.convert.viewmodel.AddUnitViewModel
import uk.fernando.convert.viewmodel.SettingsViewModel
import uk.fernando.convert.viewmodel.SplashViewModel
import uk.fernando.convert.viewmodel.UnitViewModel
import uk.fernando.logger.AndroidLogger
import uk.fernando.logger.MyLogger

object KoinModule {

    /**
     * Keep the order applied
     * @return List<Module>
     */
    fun allModules(): List<Module> = listOf(coreModule, databaseModule, repositoryModule, useCaseModule, viewModelModule)

    private val coreModule = module {

        single { getAndroidLogger() }
        single<PrefsStore> { PrefsStoreImpl(androidApplication()) }
    }

    private val databaseModule = module {

        fun provideDatabase(application: Application): MyDatabase {
            return Room.databaseBuilder(application, MyDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        single { provideDatabase(androidApplication()) }
        factory { get<MyDatabase>().unitDao() }
    }

    private val repositoryModule: Module
        get() = module {
            factory<FirstTimeRepository> { FirstTimeRepositoryImpl(get(), get()) }
            factory<UnitRepository> { UnitRepositoryImpl(get()) }
            factory<AddUnitRepository> { AddUnitRepositoryImpl(get()) }
        }

    private val useCaseModule: Module
        get() = module {
            single { GetUnitsUseCase(get(), get(), get()) }
            single { AddUnitUseCase(get(), get(), get()) }
        }

    private val viewModelModule: Module
        get() = module {

            viewModel { UnitViewModel(get()) }
            viewModel { AddUnitViewModel(get()) }
            viewModel { SettingsViewModel(get()) }
            viewModel { SplashViewModel(get(), get()) }
        }

    private const val DB_NAME = "measure_me_fun.db"

    fun getAndroidLogger(): MyLogger {
        return if (BuildConfig.BUILD_TYPE == "debug")
            AndroidLogger(MyLogger.LogLevel.DEBUG)
        else
            AndroidLogger(MyLogger.LogLevel.ERROR)
    }
}


