package com.example.simpleToDo.data.di

import android.content.Context
import androidx.room.Room
import com.example.simpleToDo.data.database.AppDatabase
import com.example.simpleToDo.data.repositories.DealsRepositoryImpl
import com.example.simpleToDo.domain.repositories.DealsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    fun bindApiRepository(
        dealsRepositoryImpl: DealsRepositoryImpl,
    ): DealsRepository
}

@Module
@InstallIn(SingletonComponent::class)
class DataProvidesModule {
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "SimpleToDo"
        )
            .build()
}
