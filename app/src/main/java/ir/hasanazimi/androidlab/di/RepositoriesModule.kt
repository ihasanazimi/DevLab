package ir.hasanazimi.androidlab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.androidlab.data.repository.remote.web_services.DeveloperWebServices
import ir.hasanazimi.androidlab.data.repository.sources.DeveloperInfoRepository
import ir.hasanazimi.androidlab.data.repository.sources.DeveloperInfoRepositoryImpl
import ir.hasanazimi.androidlab.data.repository.remote.web_services.XWebService
import ir.hasanazimi.androidlab.data.repository.sources.XRepository
import ir.hasanazimi.androidlab.data.repository.sources.XRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideXRepository(xWebService: XWebService): XRepository {
        return XRepositoryImpl(xWebService)
    }


    @Provides
    @Singleton
    fun provideDeveloperInfoRepository(developerWebServices: DeveloperWebServices) : DeveloperInfoRepository {
        return DeveloperInfoRepositoryImpl(developerWebServices)
    }


}
