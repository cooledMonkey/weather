package com.example.android.weather.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CityInfoDao {
    @Insert
    fun insertCity(item: CityInfoEntity)

    @Insert
    fun insertSummer(item:SummerTemperatures)

    @Insert
    fun insertAutumn(item: AutumnTemperatures)

    @Insert
    fun insertWinter(item: WinterTemperatures)

    @Insert
    fun insertSpring(item: SpringTemperatures)

    @Insert
    fun insertAllSeasons(summer: SummerTemperatures, autumn: AutumnTemperatures,
                  winter: WinterTemperatures, spring: SpringTemperatures)

    @Delete
    fun deleteCity(item: CityInfoEntity)

    @Delete
    fun deleteAll(item: CityInfoEntity, summer: SummerTemperatures, autumn: AutumnTemperatures,
               winter: WinterTemperatures, spring: SpringTemperatures)

    @Query("Select * from city_info where city_name=:cityName")
    fun findCityInfoByName(cityName: String): CityInfoEntity?

    @Query("Select * from city_info where id=:id")
    fun findCityInfoById(id: Long): CityInfoEntity?

    @Query("Select * from summer_temperatures where summerId=:id")
    fun findSummerInfo(id: Long): SummerTemperatures

    @Query("Select * from autumn_temperatures where autumnId=:id")
    fun findAutumnInfo(id: Long): AutumnTemperatures

    @Query("Select * from winter_temperatures where winterId=:id")
    fun findWinterInfo(id: Long): WinterTemperatures

    @Query("Select * from spring_temperatures where springId=:id")
    fun findSpringInfo(id: Long): SpringTemperatures

    @Query("select * from city_info")
    fun getAllCities(): LiveData<List<CityInfoEntity>>

    @Query("select city_name from city_info")
    fun getAllCityNames(): LiveData<String>

    @Update
    fun update(cityItem: CityInfoEntity, winterItem: WinterTemperatures, springItem: SpringTemperatures,
               summerItem: SummerTemperatures, autumn: AutumnTemperatures)
}