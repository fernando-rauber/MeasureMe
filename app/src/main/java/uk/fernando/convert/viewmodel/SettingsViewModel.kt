package uk.fernando.convert.viewmodel

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.usecase.settings.SettingsUseCase
import uk.fernando.convert.util.Resource
import uk.fernando.snackbar.SnackBarSealed


class SettingsViewModel(
    private val useCase: SettingsUseCase,
    val prefs: PrefsStore
) : BaseViewModel() {

    val snackBar: MutableState<SnackBarSealed?> = mutableStateOf(null)

    fun updateDarkMode(value: Boolean) {
        launchIO { prefs.storeDarkMode(value) }
    }

    fun updateDynamicColor(value: Boolean) {
        launchIO { prefs.storeDynamicColor(value) }
    }

    fun initialiseBillingHelper() {
        launchDefault {
            useCase.startInAppPurchaseJourney()

            useCase.getBillingState().collect() { state ->
                when (state) {
                    is Resource.Error -> snackBar.value = SnackBarSealed.Error(state.data)
                    is Resource.Success -> snackBar.value = SnackBarSealed.Success(state.data)
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