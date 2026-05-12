package com.coding.higamerapp.feature_profile.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("PROFILE_SETTINGS")

    private suspend fun <T> DataStore<Preferences>.getFromLocalStorage(
        PreferencesKey: Preferences.Key<T>, func: T.() -> Unit
    ) {
        data.map {
            it[PreferencesKey]
        }.collect {
            it?.let { func.invoke(it as T) }
        }
    }

    suspend fun <T> storeValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> readValue(key: Preferences.Key<T>, responseFunc: T.() -> Unit) {
        context.dataStore.getFromLocalStorage(key) {
            responseFunc.invoke(this)
        }
    }

    suspend fun readIntValueDataStore(key: Preferences.Key<Int>): Int? {
        return context.dataStore.data.map {
            it[key]
        }.first()
    }

    suspend fun readStringValueDataStore(key: Preferences.Key<String>): String? {
        return context.dataStore.data.map {
            it[key]
        }.first()
    }

    suspend fun readBooleanValueDataStore(key: Preferences.Key<Boolean>): Boolean? {
        return context.dataStore.data.map {
            it[key]
        }.first()
    }
}