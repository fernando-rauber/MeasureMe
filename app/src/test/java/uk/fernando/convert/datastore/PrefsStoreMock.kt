package uk.fernando.convert.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PrefsStoreMock : PrefsStore {

    private var version = 1
    private var isDarkMode = false
    private var isDynamicColor = false
    private var length = 1.0
    private var weight = 1.0
    private var volume = 1.0
    private var temperature = 1.0

    override suspend fun getVersion(): Int {
        return version
    }

    override fun isDarkMode(): Flow<Boolean> {
        return flow { isDarkMode }
    }

    override fun isDynamicColor(): Flow<Boolean> {
        return flow { isDynamicColor }
    }

    override suspend fun getLength(): Double {
        return length
    }

    override suspend fun getWeight(): Double {
        return weight
    }

    override suspend fun getVolume(): Double {
        return volume
    }

    override suspend fun getTemperature(): Double {
        return temperature
    }

    override suspend fun storeVersion(value: Int) {
        version = value
    }

    override suspend fun storeDarkMode(value: Boolean) {
        isDarkMode = value
    }

    override suspend fun storeDynamicColor(value: Boolean) {
        isDynamicColor = value
    }

    override suspend fun storeLength(value: Double) {
        length = value
    }

    override suspend fun storeWeight(value: Double) {
        weight = value
    }

    override suspend fun storeVolume(value: Double) {
        volume = value
    }

    override suspend fun storeTemperature(value: Double) {
        temperature = value
    }


}