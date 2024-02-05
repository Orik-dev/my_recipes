package com.example.my_recipes.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.my_recipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.my_recipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.my_recipes.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.my_recipes.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.my_recipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.my_recipes.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.my_recipes.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context,){

    private object PreferenceKeys{
         val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeID = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)


    private val dataStore: DataStore<Preferences> = context._dataStore


     suspend  fun saveMealAndDietType(mealType:String,mealTypeId:Int,dietType:String,dietTypeId:Int){
         dataStore.edit { preferences ->
             preferences[PreferenceKeys.selectedMealType] = mealType
             preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
             preferences[PreferenceKeys.selectedDietType] = dietType
             preferences[PreferenceKeys.selectedDietTypeID] = dietTypeId
         }
     }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data.catch { exception ->
        if (exception is IOException){
            emit(emptyPreferences())
        }else{
            throw exception
        }
    }.map { preferences ->
        val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
        val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
        val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
        val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeID] ?: 0

        MealAndDietType(
            selectedMealType,
            selectedMealTypeId,
            selectedDietType,
            selectedDietTypeId
        )
    }

    data class MealAndDietType(
        val selectedMealType : String,
        val selectedMealTypeId : Int,
        val selectedDietType : String,
        val selectedDietTypeId : Int,
    )

}