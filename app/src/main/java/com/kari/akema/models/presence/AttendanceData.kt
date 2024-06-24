package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class AttendanceData (
    @SerializedName("attendance")
    var attendance: List<Attendance>,

    @SerializedName("category")
    var category: List<Category>
)