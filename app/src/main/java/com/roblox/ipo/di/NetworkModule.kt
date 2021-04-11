package com.roblox.ipo.di

import com.roblox.ipo.data.remote.IpoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideIpoApiService(moshi: Moshi): IpoApiService =
        Retrofit.Builder()
            .baseUrl("https://ipo-app.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(IpoApiService::class.java)


}