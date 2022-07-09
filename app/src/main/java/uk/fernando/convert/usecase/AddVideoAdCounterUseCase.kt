package uk.fernando.convert.usecase

import kotlinx.coroutines.flow.first
import uk.fernando.convert.datastore.PrefsStore

class AddVideoAdCounterUseCase(private val prefs: PrefsStore) {

    suspend operator fun invoke(value: Int) {
        if (!prefs.isPremium().first())
            prefs.addCounterVideoAd(value)
    }
}