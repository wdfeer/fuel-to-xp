package org.wdfeer.skulk_burner.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import org.wdfeer.skulk_burner.SkulkBurner

class SkulkBurnerBlockEntity(
    pos: BlockPos?,
    state: BlockState?
) : BlockEntity(blockEntityType, pos, state) {
    companion object {
        val blockEntityType: BlockEntityType<SkulkBurnerBlockEntity> =
            BlockEntityType.Builder
                .create({ pos, state -> SkulkBurnerBlockEntity(pos, state)}, SkulkBurner.block)
                .build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, SkulkBurner.BLOCK_ID))
    }
}