package org.wdfeer.sculk_burner

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.wdfeer.sculk_burner.block.SculkBurnerBlock
import org.wdfeer.sculk_burner.item.SculkBurnerBlockItem

object SculkBurner : ModInitializer {
	private const val MOD_ID = "sculk_burner"
	const val BLOCK_ID = "sculk_burner"

	private fun getId(path: String): Identifier = Identifier(MOD_ID, path)

	private val logger = LoggerFactory.getLogger(MOD_ID)

	val block = SculkBurnerBlock()
	private val item = SculkBurnerBlockItem()

	override fun onInitialize() {
		Registry.register(Registries.BLOCK, getId(BLOCK_ID), block)
		Registry.register(Registries.ITEM, getId(BLOCK_ID), item)

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register { it.add(item) }

		logger.info("Sculk Burner initialized!")
	}
}