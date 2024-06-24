package com.kari.akema.models.presence

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("count")
    var count: Int,

    @SerializedName("category")
    var category: String
)
