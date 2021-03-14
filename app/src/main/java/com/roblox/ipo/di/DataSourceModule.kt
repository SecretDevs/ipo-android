package com.roblox.ipo.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier


@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource