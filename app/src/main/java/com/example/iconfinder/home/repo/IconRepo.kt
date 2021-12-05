package com.example.iconfinder.home.repo

import androidx.lifecycle.MutableLiveData
import com.example.iconfinder.BuildConfig
import com.example.iconfinder.api.ApiClient
import com.example.iconfinder.pojo.ApiResponse
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class IconRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getIcons(query: String, count: Int, index: Int): NetworkResult<ApiResponse> {
        var networkResult: NetworkResult<ApiResponse>? = null

        safeApiCall({
            apiClient.getIcons(params(query, count, index))
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    private fun params(query: String, count: Int, index: Int): Map<String, String> =
        mapOf(
            QUERY to query,
            COUNT to count.toString(),
            START_INDEX to index.toString(),
            CLIENT_ID to BuildConfig.CLIENT_ID,
            CLIENT_SECRET to BuildConfig.CLIENT_SECRET
        )

}