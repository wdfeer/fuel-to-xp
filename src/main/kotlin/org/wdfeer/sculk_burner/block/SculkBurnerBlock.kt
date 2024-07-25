package org.wdfeer.sculk_burner.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.sculk_burner.block.entity.SculkBurnerBlockEntity
import org.wdfeer.sculk_burner.util.Ticker

class SculkBurnerBlock : Block(
    FabricBlockSettings.create()
        .mapColor(MapColor.PALE_PURPLE)
        .strength(3.5f)), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity = SculkBurnerBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> = Ticker(::tick)

    private fun <T : BlockEntity?> tick(world: World?, blockPos: BlockPos?, blockState: BlockState?, blockEntity: T) {
        TODO("Implement ticking behavior")
    }
}