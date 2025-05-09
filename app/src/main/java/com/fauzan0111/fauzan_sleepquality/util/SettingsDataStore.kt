package com.fauzan0111.fauzan_sleepquality.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preference"
)

class SettingsDataStore (private val context: Context) {
    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val MODE_HITAM = booleanPreferencesKey("dark_mode")
    }

    val layoutFlow: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    suspend fun saveLayout(isList: Boolean){
        context.datastore.edit{ preferences ->
            preferences[IS_LIST] = isList
        }
    }

    fun getTheme(): Flow<Boolean> {
        return context.datastore.data.map { preferences ->
            preferences[MODE_HITAM] ?: false
        }
    }

    suspend fun setTheme(isModeHitam: Boolean) {
        context.datastore.edit { preferences ->
            preferences[MODE_HITAM] = isModeHitam
        }
    }
}



