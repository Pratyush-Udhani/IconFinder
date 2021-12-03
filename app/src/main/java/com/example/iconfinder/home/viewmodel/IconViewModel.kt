package com.example.iconfinder.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.iconfinder.home.repo.IconRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconViewModel @Inject constructor(private val iconRepo: IconRepo): ViewModel() {


}