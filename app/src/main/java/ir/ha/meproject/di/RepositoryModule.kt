package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.model.network.WebServices
import ir.ha.meproject.model.repositories.developer_info.DeveloperInfoRepository
import ir.ha.meproject.model.repositories.developer_info.DeveloperInfoRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDeveloperInfoRepository(webServices: WebServices) : DeveloperInfoRepository {
        return DeveloperInfoRepositoryImpl(webServices)
    }

}