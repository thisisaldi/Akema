package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class AttendanceRequest(
    @SerializedName("courseCode")
    var code: String
)