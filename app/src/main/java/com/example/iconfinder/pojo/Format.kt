package com.example.iconfinder.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Format (
    val format: String,

    @SerializedName("download_url")
    val downloadUrl: String,

    @SerializedName("preview_url")
    val previewUrl: String

) : Parcelable