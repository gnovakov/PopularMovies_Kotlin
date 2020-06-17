package com.example.popularmovies_kotlin.di

import com.example.popularmovies_kotlin.MainActivity
import com.example.popularmovies_kotlin.ui.detail.DetailFragment
import com.example.popularmovies_kotlin.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): DetailFragment

    //@ContributesAndroidInjector
    //abstract fun bindDetailActivity(): DetailActivity

}