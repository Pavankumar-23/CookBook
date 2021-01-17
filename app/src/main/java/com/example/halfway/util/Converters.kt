package com.example.halfway.util

import androidx.room.TypeConverter
import com.example.halfway.model.AnalyzedInstruction
import com.example.halfway.model.ExtendedIngredient
import com.example.halfway.model.NutrientX
import com.example.halfway.model.Nutrition
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        if (value == null) {
            return emptyList()
        }
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(
            value,
            listType
        )
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        if (list == null) {
            return ""
        }
        val gson = Gson()
        val type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromArrayListObj(list: List<ExtendedIngredient?>?): String {
        if (list == null) {
            return ""
        }
        val gson = Gson()
        val type = object : TypeToken<List<ExtendedIngredient?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStirngObj(value: String): List<ExtendedIngredient?>? {
        if (value == "") emptyList<ExtendedIngredient?>()
        val listType = object : TypeToken<List<ExtendedIngredient?>?>() {}.type
        return Gson().fromJson(
            value,
            listType
        )
    }

    @TypeConverter
    fun fromArrayListInstructions(list: List<AnalyzedInstruction?>?): String {
        if (list == null) {
            return ""
        }
        val gson = Gson()
        val type = object : TypeToken<List<AnalyzedInstruction?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStirngInstructions(value: String): List<AnalyzedInstruction?>? {
        if (value == "") emptyList<AnalyzedInstruction?>()
        val listType = object : TypeToken<List<AnalyzedInstruction?>?>() {}.type
        return Gson().fromJson(
            value,
            listType
        )
    }

    @TypeConverter
    fun fromArrayListNutrients(list: Nutrition?): String {
        if (list == null) {
            return ""
        }
        val gson = Gson()
        val type = object : TypeToken<Nutrition?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromStirngNutrients(value: String): Nutrition? {
        if (value == "") emptyList<Nutrition?>()
        val listType = object : TypeToken<Nutrition?>() {}.type
        return Gson().fromJson(
            value,
            listType
        )
    }
}