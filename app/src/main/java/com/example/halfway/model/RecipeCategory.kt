package com.example.halfway.model

import com.google.gson.annotations.SerializedName

data class RecipeCategory(
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_image")
    val categoryImage: String
) {

}
