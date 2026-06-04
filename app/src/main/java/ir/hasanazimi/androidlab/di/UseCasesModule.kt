package ir.hasanazimi.androidlab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.androidlab.data.repository.sources.DeveloperInfoRepository
import ir.hasanazimi.androidlab.domain.DeveloperInfoUseCase
import ir.hasanazimi.androidlab.domain.DeveloperInfoUseCaseImpl
import ir.hasanazimi.androidlab.data.repository.sources.XRepository
import ir.hasanazimi.androidlab.domain.XUseCase
import ir.hasanazimi.androidlab.domain.XUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideXUseCase(xRepository: XRepository): XUseCase {
        return XUseCaseImpl(xRepository)
    }



    @Provides
    @Singleton
    fun provideDeveloperInfoUseCase(developerInfoRepository: DeveloperInfoRepository) : DeveloperInfoUseCase{
        return DeveloperInfoUseCaseImpl(developerInfoRepository)
    }

}