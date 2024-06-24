package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("status")
    var status: String,

    @SerializedName("timestamp")
    var timestamp: String
)
