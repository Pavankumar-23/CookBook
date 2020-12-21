package com.example.halfway.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "facts_table", indices = [Index(value = ["title"], unique = true)])
data class Facts(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageHref")
    var imageUrl: String?
) : Serializable