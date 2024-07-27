package org.wdfeer.fuel_to_xp

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.wdfeer.fuel_to_xp.block.SculkBurnerBlock
import org.wdfeer.fuel_to_xp.block.entity.SculkBurnerBlockEntity
import org.wdfeer.fuel_to_xp.item.SculkBurnerBlockItem

object FuelToXp : ModInitializer {
	private const val MOD_ID = "fuel_to_xp"
	const val BLOCK_ID = "sculk_burner"

	private fun getId(path: String): Identifier = Identifier(MOD_ID, path)

	private val logger = LoggerFactory.getLogger(MOD_ID)

	val block = SculkBurnerBlock()
	private val item = SculkBurnerBlockItem()

	override fun onInitialize() {
		Registry.register(Registries.BLOCK, getId(BLOCK_ID), block)
		Registry.register(Registries.ITEM, getId(BLOCK_ID), item)
		Registry.register(Registries.BLOCK_ENTITY_TYPE, getId(BLOCK_ID + "_entity"), SculkBurnerBlockEntity.blockEntityType)

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register { it.add(item) }

		logger.info("Fuel to Xp initialized!")
	}
}