package ir.hasanazimi.me.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.me.data.model.repositories.developer_info.DeveloperInfoRepository
import ir.hasanazimi.me.data.model.use_cases.DeveloperInfoUseCase
import ir.hasanazimi.me.data.model.use_cases.DeveloperInfoUseCaseImpl
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