package com.example.smartcare.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mantenimientos",

    foreignKeys = [
        ForeignKey(
            entity = Activo::class,
            parentColumns = ["id"],
            childColumns = ["activoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["activoId"])
    ]
)
data class Mantenimiento(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val activoId: Int,

    val descripcion: String,

    val fecha: String,

    val observaciones: String,

    val proximaFecha: String
)