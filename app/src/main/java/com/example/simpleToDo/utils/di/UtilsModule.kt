package com.example.simpleToDo.utils.di

import com.example.simpleToDo.utils.log.Logger
import com.example.simpleToDo.utils.log.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UtilsModule {

    @Binds
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}