package ir.hasanazimi.me.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.me.data.repository.remote.web_services.DeveloperWebServices
import ir.hasanazimi.me.data.repository.sources.DeveloperInfoRepository
import ir.hasanazimi.me.data.repository.sources.DeveloperInfoRepositoryImpl
import ir.hasanazimi.me.data.repository.remote.web_services.XWebService
import ir.hasanazimi.me.data.repository.sources.XRepository
import ir.hasanazimi.me.data.repository.sources.XRepositoryImpl
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
