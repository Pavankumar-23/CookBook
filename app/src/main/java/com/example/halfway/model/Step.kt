package com.example.halfway.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Step(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
) : Serializable