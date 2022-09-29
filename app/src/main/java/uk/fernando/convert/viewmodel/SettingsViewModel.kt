package uk.fernando.convert.viewmodel

import uk.fernando.convert.datastore.PrefsStore


class SettingsViewModel(private val prefs: PrefsStore) : BaseViewModel() {

//    val snackBar: MutableState<SnackBarSealed?> = mutableStateOf(null)
//    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun updateDarkMode(value: Boolean) {
        launchIO { prefs.storeDarkMode(value) }
    }

    fun updateDynamicColor(value: Boolean) {
        launchIO { prefs.storeDynamicColor(value) }
    }

//    private fun initialiseBillingHelper() {
//        useCase.startInAppPurchaseJourney(scope)
//
//        scope.launch {
//
//            useCase.getBillingState().collect() { state ->
//                when (state) {
//                    is Resource.Error -> snackBar.value = SnackBarSealed.Error(state.data)
//                    is Resource.Success -> snackBar.value = SnackBarSealed.Success(state.data)
//                    else -> {}
//                }
//            }
//        }
//    }
//
//    fun requestPayment(activity: Activity) {
//        useCase.requestPayment(activity, scope)
//    }
//
//    fun restorePremium() {
//        useCase.restorePremium(scope)
//    }
//
//    override fun onCleared() {
//        scope.cancel()
//        useCase.getBillingState().value = Resource.Loading(false) // Just to clear the last message
//    }
}