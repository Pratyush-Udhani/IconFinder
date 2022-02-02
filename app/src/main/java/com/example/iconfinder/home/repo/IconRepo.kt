package com.example.iconfinder.home.repo

import com.example.iconfinder.BuildConfig
import com.example.iconfinder.api.ApiClient
import com.example.iconfinder.pojo.ApiResponse
import com.example.iconfinder.utils.*
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

    suspend fun getCategories(count: Int): NetworkResult<ApiResponse> {
        var networkResult: NetworkResult<ApiResponse>? = null

        safeApiCall({
            apiClient.getCategories(mapOf(COUNT to count.toString()))
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getIconSet(query: String, count: Int): NetworkResult<ApiResponse> {
        var networkResult: NetworkResult<ApiResponse>? = null

        safeApiCall({
            apiClient.getIconSet(query, mapOf(COUNT to count.toString()))
        }, {
            networkResult = it
        }, {
            networkResult = it
        })

        return networkResult!!
    }

    suspend fun getIconsInIconSet(iconset: String, count: Int, index: Int) : NetworkResult<ApiResponse> {
        var networkResult: NetworkResult<ApiResponse>? = null

        safeApiCall({
            apiClient.getIconInIconSet(iconset, mapOf(COUNT to count.toString(), START_INDEX to index.toString()))
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
            CLIENT_ID to BuildConfig.CLIENT_ID
        )

}