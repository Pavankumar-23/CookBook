package com.example.halfway.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FactsList(
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("rows")
    @Expose
    var rows: List<Facts>
)