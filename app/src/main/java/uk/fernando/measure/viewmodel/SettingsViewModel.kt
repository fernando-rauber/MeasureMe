package uk.fernando.measure.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import uk.fernando.measure.datastore.PrefsStore

const val PREMIUM_PRODUCT = "measure_me_premium"

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



