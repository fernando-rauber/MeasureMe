package uk.fernando.convert.viewmodel

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.usecase.PurchaseUseCase
import uk.fernando.convert.util.Resource
import uk.fernando.snackbar.SnackBarSealed


class SettingsViewModel(
    private val useCase: PurchaseUseCase,
    val prefs: PrefsStore
) : BaseViewModel() {

    val snackBar: MutableState<SnackBarSealed?> = mutableStateOf(null)
    private val scope = CoroutineScope(        Job() + Dispatchers.Main    )

    init {
        initialiseBillingHelper()
    }

    fun updateDarkMode(value: Boolean) {
        launchIO { prefs.storeDarkMode(value) }
    }

    fun updateDynamicColor(value: Boolean) {
        launchIO { prefs.storeDynamicColor(value) }
    }

   private fun initialiseBillingHelper() {
        useCase.startInAppPurchaseJourney(scope)

        scope.launch {

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
        useCase.requestPayment(activity, scope)
    }

    fun restorePremium() {
        useCase.restorePremium(scope)
    }

    override fun onCleared() {
        scope.cancel()
    }
}