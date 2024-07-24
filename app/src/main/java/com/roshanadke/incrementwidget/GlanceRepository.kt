package com.roshanadke.incrementwidget

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

const val PREFERENCES_NAME = "widget_pref"

class GlanceRepository(
    private val context: Context
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private object PreferencesKeys {
        val count = intPreferencesKey("count")
    }

    companion object {
        private val Context.datastore by preferencesDataStore(name = PREFERENCES_NAME)
    }

    val userPreferencesFlow: Flow<Int> = context.datastore.data
        .map { preferences ->
            preferences[PreferencesKeys.count] ?: 0
        }

    fun incrementCount() {
        scope.launch {
            context.datastore.edit { preferences ->
                var count = preferences[PreferencesKeys.count]
                preferences[PreferencesKeys.count] = count?.plus(1) ?: 1
            }
        }
    }

    fun decrementCount() {
        scope.launch {
            context.datastore.edit { preferences ->
                var count = preferences[PreferencesKeys.count]
                preferences[PreferencesKeys.count] = count?.minus(1) ?: -1
            }
        }
    }

}