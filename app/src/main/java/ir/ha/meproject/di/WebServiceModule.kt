package ir.ha.meproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.model.network.OtherWebServices
import ir.ha.meproject.model.network.WebServices
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {


    @Singleton
    @Provides
    fun provideWebServices1(@Named("mock-retrofit") retrofit: Retrofit.Builder) : WebServices{
        return retrofit.build().create(WebServices::class.java)
    }

    @Singleton
    @Provides
    fun provideWebServices2(@Named("withoutBaseUrl-retrofit") retrofit: Retrofit.Builder) : OtherWebServices{
        return retrofit.build().create(OtherWebServices::class.java)
    }


}