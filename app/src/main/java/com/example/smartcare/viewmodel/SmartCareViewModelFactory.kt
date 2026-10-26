package com.example.smartcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.smartcare.repository.SmartCareRepository

class SmartCareViewModelFactory(
    private val repository:
    SmartCareRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        if (
            modelClass.isAssignableFrom(
                SmartCareViewModel::class.java
            )
        ) {

            return SmartCareViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}