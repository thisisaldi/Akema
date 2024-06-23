package com.kari.akema.models.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("nim")
    var nim: String,

    @SerializedName("password")
    var password: String
)