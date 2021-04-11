package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import java.util.List;
import javax.annotation.Nullable;
import me.darki.konas.unremaped.Class570;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={BlockStateContainer.StateImplementation.class}, priority=0x7FFFFFFF)
public class MixinBlockStateContainerStateImplementation {
    @Shadow
    @Final
    private Block Field15;

    @Redirect(method={"addCollisionBoxToList"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/Block;addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"))
    public void Method24(Block blk, IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        Class570 event = Class570.Method576(blk, state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        EventDispatcher.Companion.dispatch(event);
        if (!event.isCanceled()) {
            this.Field15.addCollisionBoxToList(event.Method582(), event.Method575(), event.Method251(), event.Method578(), event.Method580(), event.Method579(), event.Method574());
        }
    }
}