package com.example.popularmovies_kotlin.di

import android.app.Application
import android.content.Context
import com.example.popularmovies_kotlin.App
import com.example.popularmovies_kotlin.MainActivity
import com.example.popularmovies_kotlin.di.modules.ApiModule
import com.example.popularmovies_kotlin.di.modules.AppModule
import com.example.popularmovies_kotlin.ui.detail.DetailFragment
import com.example.popularmovies_kotlin.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

//@Singleton
//@Component(
//    modules = [
//        AppModule::class,
//        ApiModule::class,
//        ActivityModule::class
//    ]
//)
//
//interface AppComponent : AndroidInjector<App> {
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }
//}

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailFragment)

}