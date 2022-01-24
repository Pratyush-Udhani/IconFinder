package com.example.iconfinder.api

import com.example.iconfinder.pojo.ApiResponse
import com.example.iconfinder.utils.CATEGORIES_URL
import com.example.iconfinder.utils.ICONS_URL
import com.example.iconfinder.utils.ICON_SET_URL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiClient {

    @GET(ICONS_URL)
    suspend fun getIcons(@QueryMap params: Map<String, String>) : ApiResponse

    @GET(CATEGORIES_URL)
    suspend fun getCategories(@QueryMap params: Map<String, String>) : ApiResponse

    @GET(ICON_SET_URL)
    suspend fun getIconSet(@Path("category") category: String, @QueryMap params: Map<String, String>) : ApiResponse

    @GET
    @Streaming
    fun downloadFile(@Url url: String): Call<ResponseBody>

}