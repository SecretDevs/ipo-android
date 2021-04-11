package com.roblox.ipo.di

import com.roblox.ipo.data.source.DealsDataSource
import com.roblox.ipo.data.source.RemoteDealsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier


@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @RemoteDataSource
    @Binds
    abstract fun bindRemoteDealsDataSource(
        remoteDataSource: RemoteDealsDataSourceImpl
    ): DealsDataSource

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource