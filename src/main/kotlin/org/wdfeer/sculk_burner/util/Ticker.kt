package org.wdfeer.sculk_burner.util

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Ticker<T : BlockEntity?> (val action: (world: World?, pos: BlockPos?, state: BlockState?, blockEntity: T) -> Unit) : BlockEntityTicker<T> {
    override fun tick(world: World?, pos: BlockPos?, state: BlockState?, blockEntity: T): Unit = this.action(world, pos, state, blockEntity)
}