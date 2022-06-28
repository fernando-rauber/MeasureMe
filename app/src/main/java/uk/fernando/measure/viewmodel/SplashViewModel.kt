package uk.fernando.measure.viewmodel

import uk.fernando.measure.datastore.PrefsStore
import uk.fernando.measure.repository.FirstTimeRepository

class SplashViewModel(private val prefs: PrefsStore, private val rep: FirstTimeRepository) : BaseViewModel() {

    fun firstSetUp(isDarkMode: Boolean) {
        launchIO {
            if (prefs.isFirstTime()) {
                prefs.storeDarkMode(isDarkMode)
                prefs.storeFirstTime(false)

                rep.setUpDatabase()
            }
        }
    }
}