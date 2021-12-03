package com.example.iconfinder.auth

import com.example.iconfinder.api.ApiClient
import com.example.iconfinder.utils.NetworkResult
import com.example.iconfinder.utils.safeApiCall
import com.google.gson.JsonObject
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepo @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getOtp(jsonObject: RequestBody): NetworkResult<JsonObject> {
        var networkResult: NetworkResult<JsonObject>? = null

        return networkResult!!
    }
}