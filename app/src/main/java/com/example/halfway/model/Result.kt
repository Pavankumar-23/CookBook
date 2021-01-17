package com.example.halfway.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "recipe_info")
data class Result(

    @ColumnInfo(name = "analyzedInstructions", defaultValue = "")
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction>,

    @SerializedName("cookingMinutes")
    val cookingMinutes: Int,

    @SerializedName("cuisines")
    val cuisines: List<String>,

    @SerializedName("dairyFree")
    val dairyFree: Boolean,

    @SerializedName("diets")
    val diets: List<String>,

    @SerializedName("dishTypes")
    val dishTypes: List<String>,

    @ColumnInfo(name = "extendedIngredients", defaultValue = "")
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,

    @SerializedName("glutenFree")
    val glutenFree: Boolean,

    @SerializedName("healthScore")
    val healthScore: Double,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(defaultValue = "")
    @SerializedName("image")
    val image: String,

    @ColumnInfo(defaultValue = "")
    @SerializedName("imageType")
    val imageType: String,

    @ColumnInfo(defaultValue = "")
    @SerializedName("license")
    val license: String,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("occasions")
    val occasions: List<String>,

    @SerializedName("preparationMinutes")
    val preparationMinutes: Int,

    @SerializedName("pricePerServing")
    val pricePerServing: Double,

    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,

    @SerializedName("servings")
    val servings: Int,

    @ColumnInfo(defaultValue = "")
    @SerializedName("sourceName")
    val sourceName: String,

    @ColumnInfo(defaultValue = "")
    @SerializedName("sourceUrl")
    val sourceUrl: String,

    @ColumnInfo(defaultValue = "")
    @SerializedName("summary")
    val summary: String,

    @ColumnInfo(defaultValue = "")
    @SerializedName("title")
    val title: String,

    @SerializedName("vegan")
    val vegan: Boolean,

    @SerializedName("vegetarian")
    val vegetarian: Boolean,

    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,

    @SerializedName("veryPopular")
    val veryPopular: Boolean,

    @ColumnInfo(name = "nutrition", defaultValue = "")
    @SerializedName("nutrition")
    val nutrition: Nutrition,

    ) : Serializable