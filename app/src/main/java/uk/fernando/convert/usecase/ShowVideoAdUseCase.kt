package uk.fernando.convert.usecase

import android.util.Log
import uk.fernando.convert.datastore.PrefsStore
import uk.fernando.convert.ext.TAG


class ShowVideoAdUseCase(private val prefs: PrefsStore) {

    suspend operator fun invoke(): Boolean {
        val shouldShowAd = prefs.showVideoAd()

        Log.e(TAG, "*****invoke: $shouldShowAd", )

        if (shouldShowAd)
            prefs.addCounterShowVideoAd(-5.0)
        else
            prefs.addCounterShowVideoAd(1.0)

        return shouldShowAd
    }
}