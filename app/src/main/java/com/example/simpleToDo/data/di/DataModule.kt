package com.example.simpleToDo.data.di

import android.content.Context
import androidx.room.Room
import com.example.simpleToDo.data.database.AppDatabase
import com.example.simpleToDo.data.repositories.DealsRepositoryImpl
import com.example.simpleToDo.domain.repositories.DealsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
	@Binds
	fun bindDealsRepository(
		dealsRepositoryImpl: DealsRepositoryImpl,
	): DealsRepository
}

@Module
@InstallIn(SingletonComponent::class)
class DataProvidesModule {
	/* @Provides
	 fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
		 return FirebaseAnalytics.getInstance(context)
	 }*/
	
	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context) =
		Room.databaseBuilder(
			context, AppDatabase::class.java, "SimpleToDo"
		)
			.build()
	
	@Provides
	@Singleton
	fun providesDealDao(database: AppDatabase) =
		database.getDealDao()
	
	@Provides
	@Singleton
	fun providesTagDao(database: AppDatabase) =
		database.getTagDao()
	
	@Provides
	@Singleton
	fun provideScope() =
		CoroutineScope(Dispatchers.IO) + SupervisorJob()
}
