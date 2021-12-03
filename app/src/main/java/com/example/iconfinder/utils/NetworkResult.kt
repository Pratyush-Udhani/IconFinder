package com.example.iconfinder.utils

import org.json.JSONObject

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: JSONObject?, val errorCode: Int = -1) : NetworkResult<Nothing>()
}