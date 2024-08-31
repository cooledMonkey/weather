package com.example.android.weather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("city_info", indices = [Index(value = ["city_name"], unique = true)])
data class CityInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "city_type")
    val cityType: String
) {
    override fun toString(): String {
        return cityName
    }
}

@Entity("summer_temperatures")
data class SummerTemperatures(
    @PrimaryKey
    val summerId: Long,
    val june: Double,
    val july: Double,
    val august: Double
)

@Entity("autumn_temperatures")
data class AutumnTemperatures(
    @PrimaryKey
    val autumnId: Long,
    val september: Double,
    val october: Double,
    val november: Double
)

@Entity("winter_temperatures")
data class WinterTemperatures(
    @PrimaryKey
    val winterId: Long,
    val december: Double,
    val january: Double,
    val february: Double
)

@Entity("spring_temperatures")
data class SpringTemperatures(
    @PrimaryKey
    val springId: Long,
    val march: Double,
    val april: Double,
    val may: Double
)

