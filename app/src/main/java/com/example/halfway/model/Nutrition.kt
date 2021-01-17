package com.example.halfway.model


import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Nutrition(
    @SerializedName("nutrients")
    val nutrients: List<NutrientX>
) : Serializable