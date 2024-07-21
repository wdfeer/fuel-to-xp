package org.wdfeer.skulk_burner

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object SkulkBurner : ModInitializer {
	private const val MOD_ID = "skulk_burner"
    private val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		logger.info("Skulk Burner initialized!")
	}
}