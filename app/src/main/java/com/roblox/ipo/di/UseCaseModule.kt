package com.roblox.ipo.di

import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.data.usecase.DealsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindDealsUseCase(
        dealsUseCaseImpl: DealsUseCaseImpl
    ): DealsUseCase

}