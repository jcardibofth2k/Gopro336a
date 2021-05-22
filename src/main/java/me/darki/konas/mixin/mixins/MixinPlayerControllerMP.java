package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class1;
import me.darki.konas.unremaped.rightClickBlockEvent;
import me.darki.konas.unremaped.Class23;
import me.darki.konas.unremaped.Class39;
import me.darki.konas.unremaped.Class41;
import me.darki.konas.unremaped.Class571;
import me.darki.konas.unremaped.Class644;
import me.darki.konas.unremaped.Class646;
import me.darki.konas.unremaped.ClickBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public abstract class MixinPlayerControllerMP {
    @Shadow
    private int Field1517;
    @Shadow
    private float Field1518;
    @Shadow
    @Final
    private Minecraft Field1519;
    private boolean Field1520 = false;

    @Shadow
    public abstract void Method1526();

    @Shadow
    public abstract float Method1527();

    @Inject(method={"onStoppedUsingItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1528(EntityPlayer playerIn, CallbackInfo ci) {
        if (playerIn.equals(Minecraft.getMinecraft().player)) {
            Class41 event = new Class41();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                if (event.Method278()) {
                    this.Method1526();
                    playerIn.stopActiveHand();
                }
                ci.cancel();
            }
        }
    }

    @Inject(method={"clickBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1529(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        ClickBlockEvent clickEvent = new ClickBlockEvent(this.Field1519.player, posBlock, directionFacing, ForgeHooks.rayTraceEyeHitVec(this.Field1519.player, this.Method1527() + 1.0f));
        EventDispatcher.Companion.dispatch(clickEvent);
        if (clickEvent.isCanceled()) {
            cir.setReturnValue(false);
        } else {
            Class646 event = Class646.Method1233(posBlock, directionFacing, this.Field1517, this.Field1518);
            this.Field1517 = event.Method450();
            this.Field1518 = event.Method213();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                cir.setReturnValue(false);
                return;
            }
            this.Field1520 = true;
        }
    }

    @Inject(method={"onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1530(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        Class646 event = Class646.Method1233(posBlock, directionFacing, this.Field1517, this.Field1518);
        EventDispatcher.Companion.dispatch(event);
        this.Field1517 = event.Method450();
        this.Field1518 = event.Method213();
        if (event.isCanceled()) {
            cir.setReturnValue(false);
            return;
        }
        this.Field1520 = true;
    }

    @Inject(method={"clickBlock"}, at={@At(value="RETURN")})
    private void Method1531(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (this.Field1520) {
            Class23 event = new Class23();
            EventDispatcher.Companion.dispatch(event);
        }
    }

    @Inject(method={"onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"}, at={@At(value="RETURN")})
    private void Method1532(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (this.Field1520) {
            Class23 event = new Class23();
            EventDispatcher.Companion.dispatch(event);
        }
    }

    @Inject(method={"getBlockReachDistance"}, at={@At(value="RETURN")}, cancellable=true)
    private void Method1533(CallbackInfoReturnable<Float> cir) {
        Class571 event = Class571.Method573(cir.getReturnValue().floatValue());
        EventDispatcher.Companion.dispatch(event);
        cir.setReturnValue(Float.valueOf(event.Method571()));
    }

    @Inject(method={"windowClick"}, at={@At(value="RETURN")})
    private void Method1534(int windowId, int slotId, int mouseButton, ClickType type, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir) {
        Class644 event = new Class644();
        EventDispatcher.Companion.dispatch(event);
    }

    @Inject(method={"syncCurrentPlayItem"}, at={@At(value="FIELD", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;connection:Lnet/minecraft/client/network/NetHandlerPlayClient;")})
    private void Method1535(CallbackInfo ci) {
        Class39 event = new Class39();
        EventDispatcher.Companion.dispatch(event);
    }

    @Inject(method={"onPlayerDestroyBlock"}, at={@At(value="INVOKE", target="net/minecraft/block/Block.removedByPlayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/EntityPlayer;Z)Z")}, cancellable=true)
    private void Method1536(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        Class1 event = new Class1(blockPos);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method={"processRightClickBlock"}, at={@At(value="HEAD")})
    public void Method1537(EntityPlayerSP player, WorldClient worldIn, BlockPos pos, EnumFacing direction, Vec3d vec, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
        rightClickBlockEvent event = new rightClickBlockEvent(player, worldIn, pos, direction, vec, hand);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            event.setCanceled(true);
        }
    }
}