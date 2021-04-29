package me.darki.konas.mixin.mixins;

import me.darki.konas.unremaped.Class11;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ItemBlock.class})
public abstract class MixinItemBlock {
    @Shadow
    protected abstract Block Method152();

    @Inject(method={"onItemUse"}, at={@At(value="INVOKE")}, cancellable=true)
    public void Method153(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir) {
        Class11 event = new Class11(player, worldIn, pos, facing, hitX, hitY, hitZ);
        if (event.isCanceled()) {
            ItemStack itemstack;
            if (!this.Method152().isReplaceable(worldIn, pos)) {
                pos = pos.offset(facing);
            }
            if (!(itemstack = player.getHeldItem(hand)).isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.Method152(), pos, false, facing, player)) {
                cir.setReturnValue(EnumActionResult.SUCCESS);
            } else {
                cir.setReturnValue(EnumActionResult.FAIL);
            }
        }
    }
}