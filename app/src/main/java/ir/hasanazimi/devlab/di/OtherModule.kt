package ir.hasanazimi.devlab.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hasanazimi.devlab.data.repository.local.DataStoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OtherModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext


    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

}