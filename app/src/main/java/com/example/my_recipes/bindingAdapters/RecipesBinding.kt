package com.example.my_recipes.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.example.my_recipes.data.database.RecipesEntity
import com.example.my_recipes.models.FoodRecipe
import com.example.my_recipes.util.NetworkResult
import retrofit2.Response

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe?>,
            database: List<RecipesEntity>
        ) {

            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }else if (apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
            }

        }
    }
}