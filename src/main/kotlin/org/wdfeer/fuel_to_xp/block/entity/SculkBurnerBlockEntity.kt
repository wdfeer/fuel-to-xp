package org.wdfeer.fuel_to_xp.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import org.wdfeer.fuel_to_xp.FuelToXp

class SculkBurnerBlockEntity(
    pos: BlockPos?,
    state: BlockState?
) : BlockEntity(blockEntityType, pos, state) {
    companion object {
        val blockEntityType: BlockEntityType<SculkBurnerBlockEntity> =
            BlockEntityType.Builder
                .create({ pos, state -> SculkBurnerBlockEntity(pos, state)}, FuelToXp.block)
                .build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, FuelToXp.BLOCK_ID))
    }
}