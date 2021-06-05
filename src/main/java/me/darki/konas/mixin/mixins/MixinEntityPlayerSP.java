package me.darki.konas.mixin.mixins;

import com.mojang.authlib.GameProfile;
import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class0;
import me.darki.konas.gui.screen.KonasBeaconGui;
import me.darki.konas.unremaped.Class19;
import me.darki.konas.unremaped.Class26;
import me.darki.konas.unremaped.NoDesync;
import me.darki.konas.unremaped.Class46;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.unremaped.Class50;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Class550;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.unremaped.Class648;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityPlayerSP.class}, priority=0x7FFFFFFF)
public abstract class MixinEntityPlayerSP
extends EntityPlayer {
    @Shadow
    protected Minecraft Field2379;
    @Shadow
    @Final
    public NetHandlerPlayClient Field2380;
    @Shadow
    public MovementInput Field2381;

    public MixinEntityPlayerSP(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Inject(method={"move"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V")}, cancellable=true)
    public void Method2087(MoverType type, double x, double y, double z, CallbackInfo ci) {
        ci.cancel();
        MoveEvent event = MoveEvent.Method87(type, x, y, z);
        EventDispatcher.Companion.dispatch(event);
        if (!event.isCanceled()) {
            super.move(event.getMoverType(), event.getX(), event.getY(), event.getZ());
        }
    }

    @Inject(method={"onUpdate"}, at={@At(value="HEAD")})
    public void Method2088(CallbackInfo info) {
        Class19 playerUpdateEvent = new Class19();
        EventDispatcher.Companion.dispatch(playerUpdateEvent);
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="TAIL")})
    public void Method2089(CallbackInfo ci) {
        Class50 postEvent = Class50.Method346(this.Field2379.player.posX, this.Field2379.player.posY, this.Field2379.player.posY, this.Field2379.player.rotationYaw, this.Field2379.player.rotationPitch, this.Field2379.player.onGround);
        EventDispatcher.Companion.dispatch(postEvent);
    }

    @Redirect(method={"onUpdate"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V"))
    public void Method2090(AbstractClientPlayer abstractClientPlayer) {
    }

    @Inject(method={"onUpdate"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method2091(CallbackInfo ci) {
        if (this.world.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            super.onUpdate();
            UpdateEvent event = UpdateEvent.Method273(this.Field2379.player.posX, this.Field2379.player.getEntityBoundingBox().minY, this.Field2379.player.posY, this.Field2379.player.rotationYaw, this.Field2379.player.rotationPitch, this.Field2379.player.onGround);
            NoDesync.Field883 = true;
            EventDispatcher.Companion.dispatch(event);
            if (KonasGlobals.INSTANCE.Field1139.Method1940()) {
                ci.cancel();
                NoDesync.Field882.UpdateCurrentTime();
                if (this.isRiding()) {
                    this.Field2380.sendPacket(new CPacketPlayer.Rotation(KonasGlobals.INSTANCE.Field1139.Method1945(), KonasGlobals.INSTANCE.Field1139.Method1944(), this.onGround));
                    this.Field2380.sendPacket(new CPacketInput(this.moveStrafing, this.moveForward, this.Field2381.jump, this.Field2381.sneak));
                    Entity entity = this.getLowestRidingEntity();
                    if (entity != this && entity.canPassengerSteer()) {
                        this.Field2380.sendPacket(new CPacketVehicleMove(entity));
                    }
                } else {
                    Class550.Method883(KonasGlobals.INSTANCE.Field1139.Method1945(), KonasGlobals.INSTANCE.Field1139.Method1944());
                }
                Class50 postEvent = Class50.Method346(this.Field2379.player.posX, this.Field2379.player.posY, this.Field2379.player.posY, this.Field2379.player.rotationYaw, this.Field2379.player.rotationPitch, this.Field2379.player.onGround);
                EventDispatcher.Companion.dispatch(postEvent);
            } else {
                NoDesync.Field883 = false;
            }
        }
        KonasGlobals.INSTANCE.Field1139.Method1938();
    }

    @Inject(method={"displayGUIChest"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method2092(IInventory chestInventory, CallbackInfo ci) {
        if (chestInventory instanceof IInteractionObject && "minecraft:beacon".equals(((IInteractionObject)chestInventory).getGuiID())) {
            Class0 event = new Class0();
            EventDispatcher.Companion.dispatch(event);
            Minecraft.getMinecraft().displayGuiScreen(new KonasBeaconGui(this.inventory, chestInventory));
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method={"dismountRidingEntity"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method2093(CallbackInfo ci) {
        Class26 event = Class26.Method219();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"sendChatMessage"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method2094(String message, CallbackInfo ci) {
        ci.cancel();
        Class648 event = new Class648(message);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return;
        }
        this.Field2380.sendPacket(new CPacketChatMessage(event.Method1201()));
    }

    @Redirect(method={"onUpdateWalkingPlayer"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean Method2095(EntityPlayerSP entityPlayerSP) {
        Minecraft mc = Minecraft.getMinecraft();
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return entityPlayerSP == mc.player;
        }
        return mc.getRenderViewEntity() == entityPlayerSP;
    }

    @Redirect(method={"updateEntityActionState"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean Method2096(EntityPlayerSP entityPlayerSP) {
        Minecraft mc = Minecraft.getMinecraft();
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return entityPlayerSP == mc.player;
        }
        return mc.getRenderViewEntity() == entityPlayerSP;
    }
}