package com.kari.akema.models.student

import com.google.gson.annotations.SerializedName
import com.kari.akema.models.student.Student

data class StudentDataResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("data")
    var data: Student
)