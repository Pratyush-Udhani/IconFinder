package com.example.iconfinder.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iconfinder.api.ApiClient
import com.example.iconfinder.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var apiClient: ApiClient

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}