package com.example.iconfinder.pojo

import com.google.gson.annotations.SerializedName

data class Emoji (

    @SerializedName("emojiName")
    val emojiName: String,

    @SerializedName("paid")
    val paid: Boolean,

    @SerializedName("price")
    val price: String

    //TODO:add emoji image type
//    @SerializedName("emojiImage")
//    val emojiImage:

)