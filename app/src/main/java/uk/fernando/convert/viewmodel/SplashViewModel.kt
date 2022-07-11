package uk.fernando.convert.viewmodel

import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.repository.FirstTimeRepository

class SplashViewModel(
    private val prefs: PrefsStore,
    private val rep: FirstTimeRepository
) : BaseViewModel() {

    fun firstSetUp(isDarkMode: Boolean) {
        launchIO {
            if (prefs.getVersion() == 1) {
                prefs.storeDarkMode(isDarkMode)
                prefs.storeVersion(2)

                rep.setUpDatabase()
            }
        }
    }
}