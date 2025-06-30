package ir.hasanazimi.devlab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.devlab.data.repository.sources.XRepository
import ir.hasanazimi.devlab.domain.XUseCase
import ir.hasanazimi.devlab.domain.XUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideXUseCase(xRepository: XRepository): XUseCase {
        return XUseCaseImpl(xRepository)
    }


}