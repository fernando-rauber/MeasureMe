package uk.fernando.convert.datastore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {

    suspend fun getVersion(): Int
    fun isDarkMode(): Flow<Boolean>
    fun isDynamicColor(): Flow<Boolean>

    suspend fun getLength(): Double
    suspend fun getWeight(): Double
    suspend fun getVolume(): Double
    suspend fun getTemperature(): Double

    suspend fun storeVersion(value: Int)
    suspend fun storeDarkMode(value: Boolean)
    suspend fun storeDynamicColor(value: Boolean)

    suspend fun storeLength(value: Double)
    suspend fun storeWeight(value: Double)
    suspend fun storeVolume(value: Double)
    suspend fun storeTemperature(value: Double)
}