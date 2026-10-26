package com.example.smartcare.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activos")
data class Activo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val tipo: String,

    val descripcion: String,

    val estado: String,

    val proximoMantenimiento: String
)