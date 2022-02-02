package com.example.iconfinder.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class RasterSize(

    @SerializedName("size_height")
    val sizeHeight: String,

    @SerializedName("size")
    val size: String,

    @SerializedName("size_width")
    val sizeWidth: String,

    @SerializedName("formats")
    val formats: List<Format>

) : Parcelable