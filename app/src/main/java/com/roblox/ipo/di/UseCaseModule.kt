package com.roblox.ipo.di

import com.roblox.ipo.data.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindDealsUseCase(
        dealsUseCaseImpl: DealsUseCaseImpl
    ): DealsUseCase

    @Binds
    abstract fun bindAuthUseCase(
        authUseCaseImpl: AuthUseCaseImpl
    ): AuthUseCase

    @Binds
    abstract fun bindStatisticUseCase(
        statisticUseCase: StatisticUseCaseImpl
    ): StatisticUseCase

    @Binds
    abstract fun bindQuizUseCase(
        quizUseCase: QuizUseCaseImpl
    ): QuizUseCase

}