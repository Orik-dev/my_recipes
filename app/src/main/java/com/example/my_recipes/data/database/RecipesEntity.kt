package com.example.my_recipes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.my_recipes.models.FoodRecipe
import com.example.my_recipes.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {

    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}