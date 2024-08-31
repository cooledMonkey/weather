package com.example.android.weather.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.android.weather.database.CityInfoDao
import com.example.android.weather.settings.SettingsViewModel

class InfoViewModelFactory(private val database: CityInfoDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return InfoViewModel(database) as T
    }
}