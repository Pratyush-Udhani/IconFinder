package com.example.iconfinder.pojo

import com.google.gson.annotations.SerializedName


data class ApiResponse (

    @SerializedName("icons")
    val icons: List<Icon>,

    @SerializedName("categories")
    val categories: List<Category>,

    @SerializedName("iconsets")
    val iconSet: List<IconSet>
)