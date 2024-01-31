package com.example.my_recipes.di


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_recipes.data.database.RecipesDataBase
import com.example.my_recipes.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RecipesDataBase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideDao(dataBase: RecipesDataBase) = dataBase.recipesDao()
}