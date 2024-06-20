package com.kari.akema.models

import com.google.gson.annotations.SerializedName

data class StudentDataResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("data")
    var data: Student
)