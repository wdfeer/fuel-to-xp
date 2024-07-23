package org.wdfeer.skulk_burner.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.MapColor

class SkulkBurnerBlock : Block(
    FabricBlockSettings.create().mapColor(MapColor.PALE_PURPLE)
        .strength(3.5f)
        .luminance(Blocks.createLightLevelFromLitBlockState(10))) {

}