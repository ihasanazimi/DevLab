package ir.hasanazimi.devlab.data.repository.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.flow

val Context.dataStore by preferencesDataStore(name = "DataStoreFile")

class DataStoreManager(private val context: Context) {

    val TAG = "DataStoreManager"

    /** keys */
    companion object {
        val USER_NAME = stringPreferencesKey("USER_NAME")
    }


    /** samples */
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name.also {
                Log.i(TAG, "saveUserName: $it")
            }
        }
    }

    fun getUserName() = flow {
        context.dataStore.data.collect { preferences ->
            emit(preferences[USER_NAME]).also {
                Log.i(TAG, "getUserName ")
            }
        }
    }

}