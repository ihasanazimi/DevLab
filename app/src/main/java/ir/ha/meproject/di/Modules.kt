package ir.ha.meproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.ha.meproject.data.remote.UserWebService
import ir.ha.meproject.data.repository.UserRepository
import ir.ha.meproject.data.repository.UserRepositoryImpl
import ir.ha.meproject.domain.UserUseCase
import ir.ha.meproject.domain.UserUseCaseImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSession


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun getOkHttpClient(
        context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .followSslRedirects(false)
            .addInterceptor(
                ChuckerInterceptor.Builder(context).redactHeaders("Auth-Token", "Bearer").build()
            )
            .addNetworkInterceptor(StethoInterceptor())
            .hostnameVerifier { hostname: String, session: SSLSession -> true }
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

}


@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {


    @Provides
    @Singleton
    fun provideUsersWebService(retrofit: Retrofit.Builder): UserWebService {
        return retrofit.build().create(UserWebService::class.java)
    }


}


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userWebService: UserWebService): UserRepository {
        return UserRepositoryImpl(userWebService)
    }

}


@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }

}