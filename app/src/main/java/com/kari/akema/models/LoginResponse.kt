package com.kari.akema.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String
)