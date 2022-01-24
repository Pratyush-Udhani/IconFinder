package com.example.iconfinder.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iconfinder.home.repo.IconRepo
import com.example.iconfinder.pojo.Category
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.utils.NetworkResult
import com.example.iconfinder.utils.isLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class IconViewModel @Inject constructor(private val iconRepo: IconRepo): ViewModel() {


    private var recentQuery = "\"\""
    private var recentCount = 0
    private var recentIndex = 0
    private var latestData = listOf<Icon>()
    private var latestCatData = listOf<Category>()

    private val _iconsLiveData = MutableLiveData<List<Icon>>()
    val iconsLiveData: LiveData<List<Icon>>
        get() = _iconsLiveData

    private val _categoryLiveData = MutableLiveData<List<Category>>()
    val categoryLiveData: LiveData<List<Category>>
        get() = _categoryLiveData

    fun getIcons(query: String, count: Int, index: Int) {
        if (query == recentQuery && recentCount == count && recentIndex == index) {
            _iconsLiveData.postValue(latestData)
        }
        recentQuery = query
        recentCount = count
        recentIndex = index

        isLoading = true
        viewModelScope.launch {

            when(val response = iconRepo.getIcons(query, count, index)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    val data = response.data
                    latestData = data.icons
                    _iconsLiveData.postValue(data.icons)
                }
                is NetworkResult.Error -> {
                    isLoading = false
                }
            }
        }
    }

    fun getCategories(count: Int) {
        isLoading = true
        viewModelScope.launch {

            when(val response = iconRepo.getCategories(count)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    val data = response.data
                    latestCatData = data.categories
                    _categoryLiveData.postValue(data.categories)

                }
                is NetworkResult.Error -> {
                    isLoading = false
                }
            }
        }
    }

}