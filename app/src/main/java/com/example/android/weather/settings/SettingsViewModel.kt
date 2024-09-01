package com.example.android.weather.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.weather.Seasons
import com.example.android.weather.database.CityInfoDao
import com.example.android.weather.database.CityInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class CityTypes(var value: String) {
    SMALL_CITY("small"), MEDIUM_CITY("medium"),
    BIG_CITY("big")
}

class SettingsViewModel(val database: CityInfoDao) : ViewModel() {
    private val cityItemId = MutableLiveData<Long?>(null)
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val seasonsClass = Seasons()

    var cityName = MutableLiveData("")
    private var cityType = MutableLiveData(CityTypes.BIG_CITY)
    var season = seasonsClass.season

    private var summerTemperature = seasonsClass.summerTemperatures
    private var autumnTemperature = seasonsClass.autumnTemperatures
    private var winterTemperature = seasonsClass.winterTemperatures
    private var springTemperature = seasonsClass.springTemperatures

    var temp1String = MutableLiveData("0.0")
    var temp2String = MutableLiveData("0.0")
    var temp3String = MutableLiveData("0.0")

    var cityList = database.getAllCities()

    fun radioButtonBig() {
        cityType.value = CityTypes.BIG_CITY
    }

    fun radioButtonMedium() {
        cityType.value = CityTypes.MEDIUM_CITY
    }

    fun radioButtonSmall() {
        cityType.value = CityTypes.SMALL_CITY
    }

    fun radioButtonWinter() {
        season.value?.let { saveTemperatures(it) }
        season.value = Seasons.Months.WINTER
        winterTemperature.value?.let { setNewTemperature(it) }
    }

    fun radioButtonSpring() {
        season.value?.let { saveTemperatures(it) }
        season.value = Seasons.Months.SPRING
        springTemperature.value?.let { setNewTemperature(it) }
    }

    fun radioButtonSummer() {
        season.value?.let { saveTemperatures(it) }
        season.value = Seasons.Months.SUMMER
        summerTemperature.value?.let { setNewTemperature(it) }
    }

    fun radioButtonAutumn() {
        season.value?.let { saveTemperatures(it) }
        season.value = Seasons.Months.AUTUMN
        autumnTemperature.value?.let { setNewTemperature(it) }
    }

    private fun saveTemperatures(season: Seasons.Months) {
        when (season) {
            Seasons.Months.WINTER -> {
                winterTemperature.value?.let {
                    seasonsClass.setTemperaturesInArray(it, temp1String.value?.toDouble() ?: 0.0,
                        temp2String.value?.toDouble() ?: 0.0, temp3String.value?.toDouble() ?: 0.0
                    )
                }
            }

            Seasons.Months.AUTUMN -> {
                autumnTemperature.value?.let {
                    seasonsClass.setTemperaturesInArray(it, temp1String.value?.toDouble() ?: 0.0,
                        temp2String.value?.toDouble() ?: 0.0, temp3String.value?.toDouble() ?: 0.0
                    )
                }
            }

            Seasons.Months.SPRING -> {
                springTemperature.value?.let {
                    seasonsClass.setTemperaturesInArray(it, temp1String.value?.toDouble() ?: 0.0,
                        temp2String.value?.toDouble() ?: 0.0, temp3String.value?.toDouble() ?: 0.0
                    )
                }
            }

            Seasons.Months.SUMMER -> {
                summerTemperature.value?.let {
                    seasonsClass.setTemperaturesInArray(it, temp1String.value?.toDouble() ?: 0.0,
                        temp2String.value?.toDouble() ?: 0.0, temp3String.value?.toDouble() ?: 0.0
                    )
                }
            }
        }
    }

    private fun setNewTemperature(monthTemp: Array<Double>) {
        temp1String.postValue(monthTemp[0].toString())
        temp2String.postValue(monthTemp[1].toString())
        temp3String.postValue(monthTemp[2].toString())
    }

    fun addInfoButton() {
        season.value?.let { saveTemperatures(it) }
        addInfo()
    }

    private fun addInfo() {
        viewModelScope.launch {
            cityName.value?.let {
                if (cityItemId.value != null) {
                    val cityItem = database.findCityInfoById(cityItemId.value!!)
                    cityItem?.let {
                        val winterItem = seasonsClass.getWinterItem(cityItem.id)
                        val summerItem = seasonsClass.getSummerItem(cityItem.id)
                        val autumnItem = seasonsClass.getAutumnItem(cityItem.id)
                        val springItem = seasonsClass.getSpringItem(cityItem.id)
                        database.update(cityItem, winterItem, springItem, summerItem, autumnItem)
                    }
                    cityItemId.postValue(null)
                } else {
                    var cityItem = CityInfoEntity(
                        cityName = cityName.value ?: "",
                        cityType = cityType.value?.value ?: CityTypes.BIG_CITY.value
                    )

                    database.insertCity(cityItem)
                    cityItem = database.findCityInfoByName(cityName = cityName.value!!)!!
                    cityItem.let {
                        val winterItem = seasonsClass.getWinterItem(cityItem.id)
                        val summerItem = seasonsClass.getSummerItem(cityItem.id)
                        val autumnItem = seasonsClass.getAutumnItem(cityItem.id)
                        val springItem = seasonsClass.getSpringItem(cityItem.id)
                        database.insertAllSeasons(summerItem, autumnItem, winterItem, springItem)
                    }
                }
                cityName.postValue("")
                cityItemId.postValue(null)
                seasonsClass.setTemperaturesInArray(summerTemperature.value!!, 0.0, 0.0, 0.0)
                seasonsClass.setTemperaturesInArray(autumnTemperature.value!!, 0.0, 0.0, 0.0)
                seasonsClass.setTemperaturesInArray(winterTemperature.value!!, 0.0, 0.0, 0.0)
                seasonsClass.setTemperaturesInArray(springTemperature.value!!, 0.0, 0.0, 0.0)
                setNewTemperature(winterTemperature.value!!)
            }
        }
    }

    fun deleteCity(id: Long) {
        viewModelScope.launch {
            database.findCityInfoById(id)?.let {
                database.deleteAll(
                    it, database.findSummerInfo(id), database.findAutumnInfo(id),
                    database.findWinterInfo(id), database.findSpringInfo(id)
                )
            }
        }
    }

    fun changeCity(id: Long) {
        viewModelScope.launch {
            val cityItem = database.findCityInfoById(id)
            cityName.postValue(cityItem?.cityName ?: "")
            when (cityItem?.cityType) {
                CityTypes.BIG_CITY.value -> cityType.postValue(CityTypes.BIG_CITY)
                CityTypes.MEDIUM_CITY.value -> cityType.postValue(CityTypes.MEDIUM_CITY)
                CityTypes.SMALL_CITY.value -> cityType.postValue(CityTypes.SMALL_CITY)
            }
            val winterItem = database.findWinterInfo(id)
            summerTemperature.postValue(
                arrayOf(
                    winterItem.december,
                    winterItem.january,
                    winterItem.february
                )
            )
            val springItem = database.findSpringInfo(id)
            springTemperature.postValue(arrayOf(springItem.march, springItem.april, springItem.may))
            val summerItem = database.findSummerInfo(id)
            summerTemperature.postValue(
                arrayOf(
                    summerItem.june,
                    summerItem.july,
                    summerItem.august
                )
            )
            val autumnItem = database.findAutumnInfo(id)
            autumnTemperature.postValue(
                arrayOf(
                    autumnItem.september,
                    autumnItem.october,
                    autumnItem.november
                )
            )

//            season.getValue()
//
//            when(season.getValue()){
//                Seasons.Months.WINTER -> setNewTemperature(winterTemperature.value!!)
//                Seasons.Months.SPRING -> setNewTemperature(springTemperature.value!!)
//                Seasons.Months.SUMMER -> setNewTemperature(summerTemperature.value!!)
//                Seasons.Months.AUTUMN -> setNewTemperature(autumnTemperature.value!!)
//                null -> setNewTemperature(winterTemperature.value!!)
//            }


            temp1String.postValue(winterItem.december.toString())
            temp2String.postValue(winterItem.january.toString())
            temp3String.postValue(winterItem.february.toString())

            season.postValue(Seasons.Months.WINTER)
            cityItemId.postValue(id)
        }
    }
}