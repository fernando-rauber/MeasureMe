package uk.fernando.convert.usecase

import android.app.Activity
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.fernando.billing.BillingHelper
import uk.fernando.billing.BillingState
import uk.fernando.convert.BuildConfig
import uk.fernando.convert.R
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.ext.TAG
import uk.fernando.convert.ext.isNetworkAvailable
import uk.fernando.convert.util.Resource
import uk.fernando.logger.MyLogger

const val PREMIUM_PRODUCT = "convert_me_premium"

class PurchaseUseCase(
    private val application: Application,
    private val prefs: PrefsStore,
    private val logger: MyLogger
) {

    private var billingHelper: BillingHelper? = null
    private val billingState = MutableStateFlow<Resource<Int>?>(null)
    private var isPremium = false

    fun startInAppPurchaseJourney(scope: CoroutineScope) {
        if (application.isNetworkAvailable()) {

            scope.launch {
                val isPremiumDS = prefs.isPremium().first()
                isPremium = isPremiumDS

                if (!isPremium) {
                    billingHelper = BillingHelper.getInstance(
                        application,
                        scope,
                        arrayOf(PREMIUM_PRODUCT), // one only purchase
                        arrayOf(), // subscription
                        BuildConfig.BILLING_PUBLIC_KEY
                    )

                    observeBillingState()
                }
            }
        }
    }

    fun requestPayment(activity: Activity, scope: CoroutineScope) {
        if (!application.isNetworkAvailable()) {
            billingState.value = Resource.Error("", R.string.internet_required)
            return
        }

        scope.launch {
            if (billingHelper == null) {
                startInAppPurchaseJourney(scope)
                delay(1000)
            }
            billingHelper?.launchBillingFlow(activity, PREMIUM_PRODUCT)
        }
    }

    fun restorePremium(scope: CoroutineScope) {
        if (!application.isNetworkAvailable()) {
            billingState.value = Resource.Error("", R.string.internet_required)
            return
        }

        if (isPremium)
            billingState.value = Resource.Success(R.string.restore_restored)
        else
            scope.launch {
                if (billingHelper == null) {
                    startInAppPurchaseJourney(scope)
                    delay(1000)
                }

                val isPurchased = billingHelper?.isPurchased(PREMIUM_PRODUCT)?.distinctUntilChanged()?.first()

                if (isPurchased == true) {
                    prefs.storePremium(true)
                    billingState.value = Resource.Success(R.string.restore_restored)
                } else {
                    billingState.value = Resource.Error("", R.string.restore_not_found)
                }
            }
    }

    fun getBillingState() = billingState

    private suspend fun observeBillingState() {
        billingHelper?.getBillingState()?.collect { state ->
            when (state) {
                is BillingState.Error -> {
                    billingState.value = Resource.Error("", R.string.purchase_error)
                    logger.e(TAG, state.message)
                    logger.addMessageToCrashlytics(TAG, "Error - Purchase In App: msg: ${state.message}")
                }
                is BillingState.Success -> {
                    billingState.value = Resource.Success(R.string.purchase_success)
                    prefs.storePremium(true)
                }
                is BillingState.Crashlytics -> {
                    logger.e(TAG, state.message)
                    logger.addMessageToCrashlytics(TAG, "CrashAnalytics - Purchase In App: msg: ${state.message}")
                }
                else -> {}
            }
        }
    }
}