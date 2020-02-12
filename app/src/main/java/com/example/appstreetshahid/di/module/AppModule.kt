package com.example.appstreetshahid.di.module

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.collection.LruCache
import com.example.appstreetshahid.AppStreetShahid
import com.example.appstreetshahid.utils.Constants
import com.example.appstreetshahid.utils.imagemanger.ImageCache
import com.example.appstreetshahid.utils.imagemanger.ImageManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by shahid on 12/8/16.
 */

@Module
class AppModule(val application: AppStreetShahid) {

    val TAG = AppModule::class.java.simpleName

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.create()
    }

    @Provides
    @Singleton
    @Named("RESTFUL")
    fun provideCacheRestful(application: Application): Cache {
        val cacheSize: Long = 15 * 1024 * 1024 // 15 MB
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    @Named("RESTFUL")
    fun provideOkhttpClientRestful(@Named("RESTFUL") cache: Cache): OkHttpClient {
        val timeout = 30
        val builder = OkHttpClient.Builder()
        builder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
        builder.cache(cache)
        builder.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                request = request.newBuilder().addHeader("content-type", "application/json").build()
                Log.d(TAG, "Request -> $request")
                val response = chain.proceed(request)
                Log.d(TAG, "Response -> $response")
                return response
            }
        })
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, @Named("RESTFUL") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.NetworkConstants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideImageCache(): ImageCache {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8

        val lruCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                val bitmapByteCount = value.rowBytes * value.height
                return bitmapByteCount / 1024
            }
        }
        return ImageCache(lruCache)
    }

    @Provides
    @Singleton
    fun provideImageManager(imageCache: ImageCache): ImageManager {
        return ImageManager(imageCache)
    }

}