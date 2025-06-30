package ir.hasanazimi.devlab.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLSession


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("regular")
    fun provideRegularRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://sample.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().serializeNulls().setLenient().create()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .followSslRedirects(false)
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .redactHeaders("Auth-Token", "Bearer")
                    .build()
            )
            .addNetworkInterceptor(StethoInterceptor())
            .hostnameVerifier { hostname: String, session: SSLSession -> true }
            .build()
    }



}