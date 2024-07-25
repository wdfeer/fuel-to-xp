package org.wdfeer.sculk_burner.block

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
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
        .hardness(Blocks.FURNACE.hardness)
        .resistance(Blocks.FURNACE.blastResistance)
        .requiresTool()
        .sounds(BlockSoundGroup.STONE)), BlockEntityProvider {
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
                if (spawnXpDelayed(world, blockPos, fuel / 20))
                    entity.stack.decrement(1)
            }
        }
    }

    private fun spawnXpDelayed(world: ServerWorld, blockPos: BlockPos, xp: Int): Boolean {
        val delay = 20

        fun createDelayedAction(world: ServerWorld, act: () -> Unit): Boolean {
            if (delayedActions.any { it.key.third == blockPos }) return false

            val key = Triple(world, world.time + delay, blockPos)
            delayedActions[key] = act

            return true
        }

        return createDelayedAction(world) {
            ExperienceOrbEntity.spawn(world, blockPos.toCenterPos(), xp)
        }
    }

    private var delayedActions: MutableMap<Triple<ServerWorld, Long, BlockPos>, () -> Unit> = mutableMapOf()

    init {
        ServerTickEvents.END_WORLD_TICK.register { world ->
            val newDelayedActions: MutableMap<Triple<ServerWorld, Long, BlockPos>, () -> Unit> = mutableMapOf()
            delayedActions.forEach {
                if (it.key.first == world && it.key.second <= world.time){
                    it.value()
                } else newDelayedActions[it.key] = it.value
            }
            delayedActions = newDelayedActions
        }
    }
}