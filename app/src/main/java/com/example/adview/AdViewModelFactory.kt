package com.example.adview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.sql.CommonDataSource

class AdViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdViewModel::class.java)) {
            return AdViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}