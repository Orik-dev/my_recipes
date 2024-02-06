package com.example.my_recipes.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_recipes.data.DataStoreRepository
import com.example.my_recipes.util.Constants
import com.example.my_recipes.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.my_recipes.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.my_recipes.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.my_recipes.util.Constants.Companion.QUERY_API_KEY
import com.example.my_recipes.util.Constants.Companion.QUERY_DIET
import com.example.my_recipes.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.my_recipes.util.Constants.Companion.QUERY_NUMBER
import com.example.my_recipes.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    @ApplicationContext  private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    fun saveMealAndDietType(mealType: String, mealTypeId: Int,dietType: String, dietTypeId:Int){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }


        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"


        return queries
    }

    fun showNetworkConnection(){
        if(!networkStatus){
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

}