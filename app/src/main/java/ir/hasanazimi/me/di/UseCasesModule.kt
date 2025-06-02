package ir.hasanazimi.me.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.me.data.repository.sources.XRepository
import ir.hasanazimi.me.domain.XUseCase
import ir.hasanazimi.me.domain.XUseCaseImpl
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