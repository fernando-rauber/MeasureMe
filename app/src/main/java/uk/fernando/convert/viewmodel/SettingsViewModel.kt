package uk.fernando.convert.viewmodel

import android.app.Application
import uk.fernando.convert.datastore.PrefsStore

const val PREMIUM_PRODUCT = "convert_me_premium"

class SettingsViewModel(
    private val application: Application,
    val prefs: PrefsStore
) : BaseViewModel() {

//    private var billingHelper: BillingHelper? = null
    private var isPremium = false

    fun updateDarkMode(isDarkMode: Boolean) {
        launchIO { prefs.storeDarkMode(isDarkMode) }
    }

}



