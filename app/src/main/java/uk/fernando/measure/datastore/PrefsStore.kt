package uk.fernando.measure.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    suspend fun isFirstTime(): Boolean
    fun isPremium(): Flow<Boolean>
    fun isDarkMode(): Flow<Boolean>

    suspend fun getLength(): Double
    suspend fun getWeight(): Double
    suspend fun getVolume(): Double

    suspend fun storeFirstTime(value: Boolean)
    suspend fun storePremium(value: Boolean)
    suspend fun storeDarkMode(value: Boolean)

    suspend fun storeLength(value: Double)
    suspend fun storeWeight(value: Double)
    suspend fun storeVolume(value: Double)
}