package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class PresentResponse(
    @SerializedName("status")
    var status: String,

    @SerializedName("message")
    var message: String
)
