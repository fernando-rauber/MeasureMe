package uk.fernando.convert.viewmodel

import android.app.Activity
import android.util.Log
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.usecase.settings.SettingsUseCase
import uk.fernando.convert.util.Resource


class SettingsViewModel(
    private val useCase: SettingsUseCase,
    val prefs: PrefsStore
) : BaseViewModel() {

    fun updateDarkMode(value: Boolean) {
        launchIO { prefs.storeDarkMode(value) }
    }

    fun updateDynamicColor(value: Boolean) {
        launchIO { prefs.storeDynamicColor(value) }
    }

    fun initialiseBillingHelper() {
        launchDefault {
            Log.e("********", "should not initialize more than once: ")
            useCase.startInAppPurchaseJourney()


            useCase.getBillingState().collect() { state ->
                when (state) {
                    is Resource.Error -> {
                    }
                    is Resource.Success -> {
                    }
                    else -> {}
                }
            }
        }
    }

    fun requestPayment(activity: Activity) {
        launchDefault {
            useCase.requestPayment(activity)
        }
    }

    fun restorePremium() {
        launchDefault {
            useCase.restorePremium()
        }
    }


}



