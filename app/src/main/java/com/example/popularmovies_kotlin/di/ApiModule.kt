package com.example.popularmovies_kotlin.di

import android.app.Application
import com.example.popularmovies_kotlin.api.MovieApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class ApiModule {

    private val cacheSizeBytes = 1024 * 1024 * 5

    @Provides
    @Reusable
    fun providesRetrofit(cache: Cache, okHttpClient: OkHttpClient.Builder): MovieApi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(
                okHttpClient
                    .cache(cache)
                    .build()
            )
            .addConverterFactory(
                MoshiConverterFactory.create(
//                    Moshi.Builder()
//                        .add(KotlinJsonAdapterFactory())
//                        .build()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Needed for Coroutines
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Reusable
    fun providesOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

    @Provides
    @Reusable
    fun provideCache(application: Application): Cache =
        Cache(application.cacheDir, cacheSizeBytes.toLong())

}

//private const val BASE_URL = "https://api.themoviedb.org/3/"

// Instantiate Moshi
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

//// Create the Retrofit Builder
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Needed for Coroutines
//    .baseUrl(BASE_URL)
//    .build()

//// Initialize the Retrofit service
//object MovieServiceApi {
//    val retrofitService : MovieApi by lazy {
//        retrofit.create(MovieApi::class.java) }
//}