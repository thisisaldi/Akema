package com.kari.akema.models.student

import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("id")
    var id: String,

    @SerializedName("subject")
    var subject: String,

    @SerializedName("room")
    var room: String,

    @SerializedName("starttime")
    var starttime: String,

    @SerializedName("endtime")
    var endtime: String,

    @SerializedName("day")
    var day: String

)