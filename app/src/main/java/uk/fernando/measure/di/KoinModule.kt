package uk.fernando.measure.di


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import uk.fernando.measure.viewmodel.HomeViewModel

object KoinModule {

    /**
     * Keep the order applied
     * @return List<Module>
     */
    fun allModules(): List<Module> =
        listOf(viewModelModule)


    private val viewModelModule: Module
        get() = module {

            viewModel { HomeViewModel() }
        }


}


