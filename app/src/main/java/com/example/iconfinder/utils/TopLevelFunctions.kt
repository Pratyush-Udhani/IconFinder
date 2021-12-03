package com.example.iconfinder.utils

import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    call: suspend () -> T,
    onSuccess: (NetworkResult.Success<T>) -> Unit,
    onFailure: (NetworkResult.Error) -> Unit
) {
    runCatching {
        val response = call()
        onSuccess.invoke(NetworkResult.Success(response))
    }.onFailure {
        try {
            when (it) {
                is HttpException -> {
                    it.printStackTrace()
                    val json = JSONObject(it.response()?.errorBody()?.string())
                    onFailure.invoke(NetworkResult.Error(json, it.code()))
                }
                is UnknownHostException -> {
                    val json = JSONObject()
                    json.put("message", "No internet connection")
                    onFailure.invoke(NetworkResult.Error(json))
                }
                else -> {
                    val json = JSONObject()
                    json.put("message", "Some error occurred ${it.localizedMessage}")
                    onFailure.invoke(NetworkResult.Error(json))
                }
            }
        } catch (e: java.lang.Exception) {
            val json = JSONObject()
            json.put("message", "Some error occurred $e")
            onFailure.invoke(NetworkResult.Error(json))
        }
    }
}