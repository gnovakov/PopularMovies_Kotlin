package com.example.popularmovies_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popularmovies_kotlin.databinding.ActivityMainBinding

class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
