package com.natan.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pokemon {

    @Expose
    @SerializedName("id")
    var id: Int? = null

    @Expose
    @SerializedName("name")
    var name: String? = null
}