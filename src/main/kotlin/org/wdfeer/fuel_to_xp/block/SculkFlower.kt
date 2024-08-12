package org.wdfeer.fuel_to_xp.block

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.wdfeer.fuel_to_xp.block.entity.SculkFlowerBlockEntity
import org.wdfeer.fuel_to_xp.config.FuelToXpConfig
import org.wdfeer.fuel_to_xp.util.*

class SculkFlower : FlowerBlock(
    StatusEffects.NAUSEA,
    6,
    FabricBlockSettings.copy(Blocks.TORCHFLOWER)
), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity = SculkFlowerBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T> = Ticker { w, pos, _, _ -> tick(w, pos) }

    private fun tick(world: World?, blockPos: BlockPos?) {
        Blocks.TORCHFLOWER
        if (world !is ServerWorld || blockPos == null) return

        for (entity in world.iterateEntities()) {
            if (entity == null || !entity.isAlive || entity !is ItemEntity) continue

            val fuel: Int = FuelRegistry.INSTANCE[entity.stack.item] ?: continue
            if (fuel == 0) continue

            if (entity.pos.distanceTo(blockPos.toCenterPos()) < 1.5) {
                if (spawnXpDelayed(world, blockPos, getXpAmount(fuel)))
                    entity.stack.decrement(1)
            }
        }
    }

    private fun getXpAmount(fuel: Int): Int = (fuel / FuelToXpConfig.fuelPerXp.toFloat()).randomRound()

    private fun spawnXpDelayed(world: ServerWorld, blockPos: BlockPos, xp: Int): Boolean {
        if (xp <= 0) return false

        val delay = FuelToXpConfig.delayTicks

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