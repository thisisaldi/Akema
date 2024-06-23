package com.kari.akema.models

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("_id")
    var id: String,

    @SerializedName("nim")
    var nim: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("classOf")
    var classOf: Int,

    @SerializedName("courses")
    var courses: List<Course>
)