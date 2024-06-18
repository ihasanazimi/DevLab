package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.model.repositories.developer_info.DeveloperInfoRepository
import ir.ha.meproject.model.use_cases.DeveloperInfoUseCase
import ir.ha.meproject.model.use_cases.DeveloperInfoUseCaseImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideDeveloperInfoUseCase(developerInfoRepository: DeveloperInfoRepository) : DeveloperInfoUseCase{
        return DeveloperInfoUseCaseImpl(developerInfoRepository)
    }
}