package com.example.android.weather.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.weather.CelsiusSystem
import com.example.android.weather.FahrenheitSystem
import com.example.android.weather.KelvinSystem
import com.example.android.weather.Seasons
import com.example.android.weather.TemperatureCalc
import com.example.android.weather.database.CityInfoDao
import com.example.android.weather.database.CityInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class InfoViewModel(val database: CityInfoDao): ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private lateinit var currentCity: CityInfoEntity
    private var currentSeason = MutableLiveData(Seasons.Months.WINTER)
    var cityType = MutableLiveData("")

    var cityList = database.getAllCities()
    var seasonTemperature = MutableLiveData(0.0)

    val temperatureCalc: TemperatureCalc = TemperatureCalc()

    fun getSelectedItem(city: CityInfoEntity){
            currentCity = city
    }

    fun radioButtonWinter() {
        currentSeason.value = Seasons.Months.WINTER
    }

    fun radioButtonSpring() {
        currentSeason.value = Seasons.Months.SPRING
    }

    fun radioButtonSummer() {
        currentSeason.value = Seasons.Months.SUMMER
    }

    fun radioButtonAutumn() {
        currentSeason.value = Seasons.Months.AUTUMN
    }

    fun searchButton(){
        cityType.value = currentCity.cityType
        searchSeason()
    }

    fun radioButtonCelsius(){
        temperatureCalc.changeSystem(CelsiusSystem())
    }

    fun radioButtonFahrenheit(){
        temperatureCalc.changeSystem(FahrenheitSystem())
    }

    fun radioButtonKelvin(){
        temperatureCalc.changeSystem(KelvinSystem())
    }

    private fun roundToDouble(number: Double): Double{
        return (number*100).roundToInt()/100.0
    }

    private fun searchSeason(){
        viewModelScope.launch{
            when(currentSeason.value){
                Seasons.Months.WINTER -> {
                    val item = database.findWinterInfo(currentCity.id)
                    seasonTemperature.postValue(
                        temperatureCalc.getTemperature(roundToDouble((item.december + item.february + item.january) / 3)))
                }
                Seasons.Months.SPRING -> {
                    val item = database.findSpringInfo(currentCity.id)
                    seasonTemperature.postValue(
                        temperatureCalc.getTemperature(roundToDouble((item.april + item.march + item.may) / 3)))
                }
                Seasons.Months.SUMMER -> {
                    val item = database.findSummerInfo(currentCity.id)
                    seasonTemperature.postValue(
                        temperatureCalc.getTemperature(roundToDouble((item.june + item.july + item.august) / 3)))
                }
                Seasons.Months.AUTUMN -> {
                    val item = database.findAutumnInfo(currentCity.id)
                    seasonTemperature.postValue(
                        temperatureCalc.getTemperature(roundToDouble((item.september + item.october + item.november) / 3)))
                }

                else -> {
                    seasonTemperature.postValue(-273.3)
                }
            }
        }
    }
}