package com.example.halfway.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NutrientX(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("percentOfDailyNeeds")
    val percentOfDailyNeeds: Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("unit")
    val unit: String
) : Serializable