package com.example.smartcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.smartcare.ui.theme.SmartCareTheme
import com.example.smartcare.data.SmartCareDatabase
import com.example.smartcare.navigation.AppNavigation
import com.example.smartcare.repository.SmartCareRepository
import com.example.smartcare.viewmodel.AuthViewModel
import com.example.smartcare.viewmodel.SmartCareViewModel
import com.example.smartcare.viewmodel.SmartCareViewModelFactory


class MainActivity :
    ComponentActivity() {

    // =====================================================
    // DATABASE
    // =====================================================

    private val database by lazy {

        SmartCareDatabase
            .getDatabase(
                applicationContext
            )
    }


    // =====================================================
    // REPOSITORY
    // =====================================================

    private val repository by lazy {

        SmartCareRepository(
            activoDao =
                database.activoDao(),

            mantenimientoDao =
                database.mantenimientoDao()
        )
    }


    // =====================================================
    // SMARTCARE VIEWMODEL
    // =====================================================

    private val smartCareViewModel:
            SmartCareViewModel by viewModels {

        SmartCareViewModelFactory(
            repository
        )
    }


    // =====================================================
    // FIREBASE AUTH VIEWMODEL
    // =====================================================

    private val authViewModel:
            AuthViewModel by viewModels()


    // =====================================================
    // ACTIVITY
    // =====================================================

    override fun onCreate(
        savedInstanceState:
        Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        setContent {

            SmartCareTheme {

                AppNavigation(
                    smartCareViewModel =
                        smartCareViewModel,

                    authViewModel =
                        authViewModel
                )
            }
        }
    }
}