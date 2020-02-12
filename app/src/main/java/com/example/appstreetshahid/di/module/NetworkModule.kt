package com.example.appstreetshahid.di.module

import com.example.appstreetshahid.di.IDataService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by shahid on 12/8/16.
 */

@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideDataService(retrofit: Retrofit): IDataService {
        return retrofit.create(IDataService::class.java)
    }

}