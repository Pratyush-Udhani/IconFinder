package com.example.iconfinder.pojo

import com.google.gson.annotations.SerializedName

class IconSet (

    @SerializedName("iconset_id")
    val iconSetId: String,

    @SerializedName("identifier")
    val name: String

)