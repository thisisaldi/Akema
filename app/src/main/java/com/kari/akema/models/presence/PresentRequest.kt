package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class PresentRequest(
    @SerializedName("courseCode")
    var code: String,

    @SerializedName("izinIsTrue")
    var izin: Boolean
)
