package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.EntityTravelEvent;
import me.darki.konas.unremaped.Class4;
import me.darki.konas.unremaped.Class53;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends MixinEntityLivingBase {
    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method183(float strafe, float vertical, float forward, CallbackInfo info) {
        if (this.equals(Minecraft.getMinecraft().player)) {
            EntityTravelEvent event = new EntityTravelEvent(strafe, vertical, forward);
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                this.Method10(MoverType.SELF, this.Field10, this.Field11, this.Field12);
                info.cancel();
            }
        }
    }

    @Inject(method={"isPushedByWater()Z"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method184(CallbackInfoReturnable<Boolean> info) {
        Class4 event = new Class4();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }

    @ModifyArgs(method={"travel"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/EntityLivingBase;travel(FFF)V"))
    public void Method185(Args args) {
        Class53 event = new Class53(this.Method11(), ((Float)args.get(0)).floatValue(), ((Float)args.get(1)).floatValue(), ((Float)args.get(2)).floatValue());
        EventDispatcher.Companion.dispatch(event);
        args.set(0, (Object)Float.valueOf(event.Method332()));
        args.set(1, (Object)Float.valueOf(event.Method335()));
        args.set(2, (Object)Float.valueOf(event.Method333()));
    }
}