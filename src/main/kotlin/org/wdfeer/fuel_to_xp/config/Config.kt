package org.wdfeer.fuel_to_xp.config

import org.wdfeer.fuel_to_xp.FuelToXp

data class Config(
    val fuelPerXp: Int = 200,
    val delayTicks: Int = 20
)

fun FuelToXp.loadConfig(): Config {
    TODO()
}

private fun writeDefaultConfig() {
    TODO()
}