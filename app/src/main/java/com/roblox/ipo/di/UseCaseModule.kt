package com.roblox.ipo.di

import com.roblox.ipo.data.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindDealsUseCase(
        dealsUseCaseImpl: FakeDealsUseCase
    ): DealsUseCase

    @Binds
    abstract fun bindAuthUseCase(
        authUseCaseImpl: AuthUseCaseImpl
    ): AuthUseCase

    @Binds
    abstract fun bindStatisticUseCase(
        statisticUseCase: FakeStatisticUseCase
    ): StatisticUseCase

}