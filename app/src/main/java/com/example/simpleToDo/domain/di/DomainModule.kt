package com.example.simpleToDo.domain.di

import com.example.simpleToDo.domain.usecases.GetDealUseCase
import com.example.simpleToDo.domain.usecases.GetDealUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {
	@Binds
	fun bindGetDealUseCase(
		getDealUseCaseImpl: GetDealUseCaseImpl
	): GetDealUseCase
}