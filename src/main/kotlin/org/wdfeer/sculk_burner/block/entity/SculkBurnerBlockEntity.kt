package org.wdfeer.sculk_burner.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import org.wdfeer.sculk_burner.SculkBurner

class SculkBurnerBlockEntity(
    pos: BlockPos?,
    state: BlockState?
) : BlockEntity(blockEntityType, pos, state) {
    companion object {
        val blockEntityType: BlockEntityType<SculkBurnerBlockEntity> =
            BlockEntityType.Builder
                .create({ pos, state -> SculkBurnerBlockEntity(pos, state)}, SculkBurner.block)
                .build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, SculkBurner.BLOCK_ID))
    }
}