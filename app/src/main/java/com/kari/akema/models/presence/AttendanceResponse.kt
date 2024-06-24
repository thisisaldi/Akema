package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class AttendanceResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("data")
    var data: AttendanceData
)