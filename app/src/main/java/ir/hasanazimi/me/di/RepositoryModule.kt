package ir.hasanazimi.me.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.me.data.model.network.WebServices
import ir.hasanazimi.me.data.model.repositories.developer_info.DeveloperInfoRepository
import ir.hasanazimi.me.data.model.repositories.developer_info.DeveloperInfoRepositoryImpl
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