package com.example.android.weather.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.weather.database.CityDatabase
import com.example.android.weather.database.CityInfoDao

class SettingsViewModelFactory(private val database: CityInfoDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(database) as T
    }
}