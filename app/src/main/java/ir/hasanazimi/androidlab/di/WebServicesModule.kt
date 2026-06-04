package ir.hasanazimi.androidlab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.androidlab.data.repository.remote.web_services.XWebService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebServicesModule {

    @Singleton
    @Provides
    fun provideXWebServices(@Named("regular") retrofit: Retrofit.Builder): XWebService {
        return retrofit.build().create(XWebService::class.java)
    }


}
