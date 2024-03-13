package id.myone.firestore_project_example.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.myone.firestore_project_example.models.channel.UserChannelModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class SecureDataStore(
    private val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app-store")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_AVATAR = stringPreferencesKey("user_avatar")
    }

    suspend fun getUserId(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID] ?: ""
        }.first()
    }

    suspend fun getUserName(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: ""
        }.first()
    }

    suspend fun getUserAvatar(): String {
        return context.dataStore.data.map { preferences ->
            preferences[USER_AVATAR] ?: ""
        }.first()
    }


    suspend fun loginChannelSession(user: UserChannelModel) {
        context.dataStore.edit { settings ->
            settings[USER_ID] = user.id
            settings[USER_NAME] = user.userName
            settings[USER_AVATAR] = user.avatar
        }
    }

    suspend fun logoutChannelSession() {
        context.dataStore.edit { settings ->
            settings[USER_ID] = ""
            settings[USER_NAME] = ""
            settings[USER_AVATAR] = ""
        }
    }

}