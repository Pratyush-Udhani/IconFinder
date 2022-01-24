package com.example.iconfinder.pojo

import com.google.gson.annotations.SerializedName

class Category (

    @SerializedName("name")
    val name: String,

    @SerializedName("identifier")
    val identifier: String

)