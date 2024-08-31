package com.example.android.weather

import androidx.lifecycle.MutableLiveData
import com.example.android.weather.database.AutumnTemperatures
import com.example.android.weather.database.CityInfoDao
import com.example.android.weather.database.SpringTemperatures
import com.example.android.weather.database.SummerTemperatures
import com.example.android.weather.database.WinterTemperatures

class Seasons() {
    enum class Months(val value: Array<String>) {
        WINTER(
            arrayOf(
                "December", "January", "February"
            )
        ),
        SPRING(arrayOf("March", "April", "May")),
        SUMMER(
            arrayOf(
                "June", "July", "August"
            )
        ),
        AUTUMN(arrayOf("September", "October", "November"))
    }

    var summerTemperatures = MutableLiveData(arrayOf(0.0, 0.0, 0.0))
    var autumnTemperatures = MutableLiveData(arrayOf(0.0, 0.0, 0.0))
    var winterTemperatures = MutableLiveData(arrayOf(0.0, 0.0, 0.0))
    var springTemperatures = MutableLiveData(arrayOf(0.0, 0.0, 0.0))

    var season = MutableLiveData(Months.WINTER)

    fun setTemperaturesInArray(
        monthTemp: Array<Double>, temp1: Double,
        temp2: Double, temp3: Double
    ) {
        monthTemp[0] = temp1
        monthTemp[1] = temp2
        monthTemp[2] = temp3
    }

    fun getWinterItem(id: Long): WinterTemperatures {
        return WinterTemperatures(
            id, winterTemperatures.value!![0],
            winterTemperatures.value!![1], winterTemperatures.value!![2]
        )
    }

    fun getSpringItem(id: Long): SpringTemperatures {
        return SpringTemperatures(
            id, springTemperatures.value!![0],
            springTemperatures.value!![1], springTemperatures.value!![2]
        )
    }

    fun getSummerItem(id: Long): SummerTemperatures {
        return SummerTemperatures(
            id, summerTemperatures.value!![0],
            summerTemperatures.value!![1], summerTemperatures.value!![2]
        )
    }

    fun getAutumnItem(id: Long): AutumnTemperatures{
        return AutumnTemperatures(id, autumnTemperatures.value!![0],
            autumnTemperatures.value!![1], autumnTemperatures.value!![2])
    }

}