package uk.fernando.measure.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    suspend fun isFirstTime(): Boolean
    fun isPremium(): Flow<Boolean>
    fun isDarkMode(): Flow<Boolean>

    suspend fun storeFirstTime(value: Boolean)
    suspend fun storePremium(value: Boolean)
    suspend fun storeDarkMode(value: Boolean)
}