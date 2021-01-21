package com.example.halfway.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import com.example.halfway.model.PreferencesTypes
import com.example.halfway.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.halfway.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.halfway.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.halfway.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.halfway.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.halfway.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.halfway.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object preferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun savePreferenceTypes(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit {
            it[preferenceKeys.selectedMealType] = mealType
            it[preferenceKeys.selectedMealTypeId] = mealTypeId
            it[preferenceKeys.selectedDietType] = dietType
            it[preferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readPreferencesTypes: Flow<PreferencesTypes> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        val selectedMealType = it[preferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
        val selectedMealTypeId = it[preferenceKeys.selectedMealTypeId] ?: 0
        val selectedDietType = it[preferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
        val selectedDietTypeId = it[preferenceKeys.selectedDietTypeId] ?: 0
        PreferencesTypes(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
    }
}
