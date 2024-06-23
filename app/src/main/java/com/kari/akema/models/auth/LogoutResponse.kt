package com.kari.akema.models.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String
)