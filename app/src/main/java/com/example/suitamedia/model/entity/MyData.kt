package com.example.suitamedia.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_data")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val avatar:String?,
    val name:String
)
