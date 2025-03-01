package net.caffeinemc.mods.lithium.mixin.util.inventory_comparator_tracking;

import net.caffeinemc.mods.lithium.common.block.entity.inventory_comparator_tracking.ComparatorTracking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DiodeBlock.class)
public abstract class DiodeBlockMixin {

    @Inject(
            method = "onPlace(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V",
            at = @At("RETURN")
    )
    private void notifyOnBlockAdded(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        //noinspection ConstantValue
        if ((Object) this instanceof ComparatorBlock && !oldState.is(Blocks.COMPARATOR)) {
            ComparatorTracking.notifyNearbyBlockEntitiesAboutNewComparator(world, pos);
        }
    }
}
