package uk.fernando.convert.viewmodel

import androidx.compose.runtime.mutableStateOf
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.repository.FirstTimeRepository
import uk.fernando.convert.usecase.ShowVideoAdUseCase

class SplashViewModel(
    private val prefs: PrefsStore,
    private val rep: FirstTimeRepository,
    private val showVideoAdUserCase: ShowVideoAdUseCase
) : BaseViewModel() {

    val showVideoAd = mutableStateOf(false)

    fun firstSetUp(isDarkMode: Boolean) {
        launchIO {
            if (prefs.isFirstTime()) {
                prefs.storeDarkMode(isDarkMode)
                prefs.storeFirstTime(false)

                rep.setUpDatabase()
            }

            showVideoAd.value = showVideoAdUserCase.invoke()
        }
    }
}