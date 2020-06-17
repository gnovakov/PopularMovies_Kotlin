package com.example.popularmovies_kotlin

import com.example.popularmovies_kotlin.di.AppComponent
import com.example.popularmovies_kotlin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector():
            AndroidInjector<out DaggerApplication> {

        //Build app component
        val appComponent: AppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        //inject application instance
        appComponent.inject(this)
        return appComponent
    }

}