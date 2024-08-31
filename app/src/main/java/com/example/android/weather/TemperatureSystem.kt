package com.example.android.weather

interface TemperatureSystem {
    fun calculateTemperature(temperature: Double): Double
}

class CelsiusSystem : TemperatureSystem {
    override fun calculateTemperature(temperature: Double): Double {
        return temperature
    }
}

class FahrenheitSystem : TemperatureSystem {
    override fun calculateTemperature(temperature: Double): Double {
        return (temperature * (9.0 / 5.0)) + 32
    }
}

class KelvinSystem : TemperatureSystem{
    override fun calculateTemperature(temperature: Double): Double {
        return 273.15 + temperature
    }
}

class TemperatureCalc{
    var temperatureSystem: TemperatureSystem = CelsiusSystem()

    fun getTemperature(temperature: Double): Double{
        return temperatureSystem.calculateTemperature(temperature)
    }

    fun changeSystem(newTemperatureSystem: TemperatureSystem){
        temperatureSystem = newTemperatureSystem
    }

}