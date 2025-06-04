package org.wdfeer.fuel_to_xp.config

import net.fabricmc.loader.api.FabricLoader
import org.wdfeer.fuel_to_xp.FuelToXp
import java.io.File

data class Config(
    val fuelPerXp: Int = 200,
    val delayTicks: Int = 20
)

private fun FuelToXp.getConfigFile(): File {
    val path = FabricLoader.getInstance().configDir.resolve("$MOD_ID.cfg")
    return path.toFile()
}

fun FuelToXp.loadConfig(): Config {
    val file = getConfigFile()
    if (!file.exists()) {
        logger.info("Config file not found, writing a default config.")
        writeDefaultConfig()
        return Config()
    }

    val lines = file.readLines()
    val map = lines.associate { line ->
        line.takeWhile { it != '=' }.trim() to line.takeLastWhile { it != '=' }.trim()
    }

    val fuelPerXp: Int? = map["fuelPerXp"]?.toIntOrNull()
    val delayTicks: Int? = map["delayTicks"]?.toIntOrNull()

    return if (fuelPerXp != null && delayTicks != null) {
        Config(fuelPerXp, delayTicks)
    } else {
        logger.error("Invalid config found! Creating a default config.")
        writeDefaultConfig()
        Config()
    }
}

private fun FuelToXp.writeDefaultConfig() {
    val file = getConfigFile()

    val defaultConfig = Config()
    val str = "fuelPerXp = ${defaultConfig.fuelPerXp}\ndelayTicks = ${defaultConfig.delayTicks}"

    if (!file.exists())
        file.createNewFile()
    else {
        file.delete()
        file.createNewFile()
    }

    file.writeText(str)
}