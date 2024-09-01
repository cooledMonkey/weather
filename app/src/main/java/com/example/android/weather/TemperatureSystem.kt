package com.example.android.weather

import kotlin.math.roundToInt

interface TemperatureSystem {
    fun calculateTemperature(temperature: Double): Double
    fun getMeasurementUnits(): String
}

class CelsiusSystem : TemperatureSystem {
    override fun calculateTemperature(temperature: Double): Double {
        return temperature
    }

    override fun getMeasurementUnits(): String {
        return "°C"
    }
}

class FahrenheitSystem : TemperatureSystem {
    override fun calculateTemperature(temperature: Double): Double {
        return ((((temperature * (9.0 / 5.0)) + 32) * 100).roundToInt()) / 100.0
    }

    override fun getMeasurementUnits(): String {
        return "°F"
    }
}

class KelvinSystem : TemperatureSystem {
    override fun calculateTemperature(temperature: Double): Double {
        return (((273.15 + temperature) * 100).roundToInt()) / 100.0
    }

    override fun getMeasurementUnits(): String {
        return "K"
    }
}

class TemperatureCalc {
    private var temperatureSystem: TemperatureSystem = CelsiusSystem()

    fun getTemperature(temperature: Double): Double {
        return temperatureSystem.calculateTemperature(temperature)
    }

    fun changeSystem(newTemperatureSystem: TemperatureSystem) {
        temperatureSystem = newTemperatureSystem
    }

    fun getMeasurementUnits(): String {
        return temperatureSystem.getMeasurementUnits()
    }
}