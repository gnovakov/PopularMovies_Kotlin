package com.example.popularmovies_kotlin.di.modules

import com.example.popularmovies_kotlin.Const.BASE_URL
import com.gnova.data.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class ApiModule {

    @Provides
    @Reusable
    fun providesRetrofit(
                         okHttpClient: OkHttpClient.Builder): MovieApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                okHttpClient
                    .build()
            )
            .addConverterFactory( MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // Needed for Rx
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Reusable
    fun providesOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

}
