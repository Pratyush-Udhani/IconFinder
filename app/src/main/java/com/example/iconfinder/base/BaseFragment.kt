package com.example.iconfinder.base

import androidx.fragment.app.Fragment
import com.example.iconfinder.api.ApiClient
import com.example.iconfinder.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var apiClient: ApiClient

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


}