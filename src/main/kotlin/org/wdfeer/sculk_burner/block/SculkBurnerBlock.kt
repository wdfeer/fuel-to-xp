package org.wdfeer.sculk_burner.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.ItemEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.sculk_burner.block.entity.SculkBurnerBlockEntity
import org.wdfeer.sculk_burner.util.Ticker

class SculkBurnerBlock : Block(
    FabricBlockSettings.create()
        .mapColor(Blocks.SCULK_CATALYST.defaultMapColor)
        .hardness(Blocks.SCULK_CATALYST.hardness)
        .resistance(Blocks.SCULK_CATALYST.blastResistance)
        .sounds(BlockSoundGroup.SCULK_CATALYST)), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity = SculkBurnerBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> = Ticker(::tick)

    private fun tick(world: World?, blockPos: BlockPos?, blockState: BlockState?, blockEntity: BlockEntity?) {
        if (world !is ServerWorld || blockPos == null) return

        for (entity in world.iterateEntities()) {
            if (entity == null || !entity.isAlive || entity !is ItemEntity) continue

            val fuel: Int = FuelRegistry.INSTANCE[entity.stack.item] ?: continue
            if (fuel == 0) continue

            if (entity.pos.distanceTo(blockPos.toCenterPos()) < 1.5) {
                entity.stack.decrement(1)
                ExperienceOrbEntity.spawn(world, blockPos.toCenterPos(), fuel / 20)
            }
        }
    }
}