package org.wdfeer.fuel_to_xp

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object FuelToXpClient : ClientModInitializer {
	override fun onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(FuelToXp.block, RenderLayer.getCutout())
	}
}