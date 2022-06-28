package uk.fernando.measure.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import uk.fernando.measure.database.MyDatabase
import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.datastore.PrefsStoreImpl
import uk.fernando.measure.repository.FirstTimeRepository
import uk.fernando.measure.viewmodel.HomeViewModel
import uk.fernando.measure.viewmodel.SplashViewModel

object KoinModule {

    /**
     * Keep the order applied
     * @return List<Module>
     */
    fun allModules(): List<Module> = listOf(coreModule, databaseModule, repositoryModule, viewModelModule)

    private val coreModule = module {
        fun provideDataStore(app: Context): PrefsStore {
            return PrefsStoreImpl(app)
        }

        single { provideDataStore(androidApplication()) }
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
            factory { FirstTimeRepository(get()) }
        }

    private val viewModelModule: Module
        get() = module {

            viewModel { HomeViewModel() }
            viewModel { SplashViewModel(get(), get()) }
        }

    private const val DB_NAME = "measure_me_fun.db"

}


