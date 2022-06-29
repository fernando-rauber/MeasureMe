package uk.fernando.measure.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*

private const val STORE_NAME = "measure_me_data_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)

class PrefsStoreImpl(context: Context) : PrefsStore {

    private val dataStore = context.dataStore
    override suspend fun isFirstTime(): Boolean {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.FIRST_TIME] ?: true }.first()
    }

    override fun isPremium(): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.PREMIUM] ?: false }
    }

    override fun isDarkMode(): Flow<Boolean> {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.DARK_MODE] ?: false }
    }

    override suspend fun getLength(): Double {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.LENGTH] ?: 1.0 }.first()
    }

    override suspend fun getWeight(): Double {
        return dataStore.data.map { prefs -> prefs[PreferencesKeys.WEIGHT] ?: 1.0 }.first()
    }

    override suspend fun storeFirstTime(value: Boolean) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.FIRST_TIME] = value }
    }

    override suspend fun storePremium(value: Boolean) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.PREMIUM] = value }
    }

    override suspend fun storeDarkMode(value: Boolean) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.DARK_MODE] = value }
    }

    override suspend fun storeLength(value: Double) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.LENGTH] = value }
    }

    override suspend fun storeWeight(value: Double) {
        dataStore.edit { prefs -> prefs[PreferencesKeys.WEIGHT] = value }
    }

    private object PreferencesKeys {
        val FIRST_TIME = booleanPreferencesKey("first_time")
        val PREMIUM = booleanPreferencesKey("premium")
        val DARK_MODE = booleanPreferencesKey("dark_mode")

        val LENGTH = doublePreferencesKey("length")
        val WEIGHT = doublePreferencesKey("weight")
    }
}