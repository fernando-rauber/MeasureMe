package uk.fernando.convert.usecase

import uk.fernando.convert.datastore.PrefsStore

class AddVideoAdCounterUseCase(private val prefs: PrefsStore) {

    suspend operator fun invoke(value: Int) {
        prefs.addCounterVideoAd(value)
    }
}