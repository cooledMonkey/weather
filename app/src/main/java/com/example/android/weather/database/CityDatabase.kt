package com.example.android.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CityInfoEntity::class, SummerTemperatures::class,
        AutumnTemperatures::class,
        WinterTemperatures::class,
        SpringTemperatures::class], version = 1, exportSchema = false
)
abstract class CityDatabase : RoomDatabase() {

    abstract val cityDatabaseDao: CityInfoDao

    companion object {
        //lazy singleton?
        @Volatile
        private var INSTANCE: CityDatabase? = null

        fun getInstance(context: Context): CityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CityDatabase::class.java,
                        "city_info"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
