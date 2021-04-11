package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import java.util.List;
import javax.annotation.Nullable;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class443;
import me.darki.konas.XRay;
import me.darki.konas.unremaped.Class643;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Block.class}, priority=0x7FFFFFFF)
public class MixinBlock {
    private Class643 Field2008;

    @Inject(method={"addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"}, at={@At(value="HEAD")})
    private void Method1838(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState, CallbackInfo ci) {
        Class643 event;
        if (Minecraft.getMinecraft().player == null || entity == null || world == null || entityBox == null) {
            return;
        }
        Block block = (Block)this;
        this.Field2008 = event = Class643.Method1240(block, pos, block.getCollisionBoundingBox(state, world, pos), collidingBoxes, entity);
        EventDispatcher.Companion.dispatch(event);
    }

    @Redirect(method={"addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/Block;addCollisionBoxToList(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/util/math/AxisAlignedBB;)V"))
    private void Method1839(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB rawBlockBox) {
        AxisAlignedBB axisalignedbb;
        AxisAlignedBB blockBox = this.Field2008 == null ? rawBlockBox : this.Field2008.Method1242();
        this.Field2008 = null;
        if (blockBox != null && blockBox != Block.NULL_AABB && entityBox.intersects(axisalignedbb = blockBox.offset(pos))) {
            collidingBoxes.add(axisalignedbb);
        }
    }

    @Inject(method={"isFullCube"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1840(IBlockState blockState, CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.getModuleByClass(XRay.class) == null) {
            return;
        }
        if (ModuleManager.getModuleByClass(XRay.class).isEnabled()) {
            info.setReturnValue(((Class443) XRay.blocks.getValue()).Method682().contains(Block.class.cast(this)));
            info.cancel();
        }
    }
}