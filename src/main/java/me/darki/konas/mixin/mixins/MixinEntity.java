package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class34;
import me.darki.konas.unremaped.Class36;
import me.darki.konas.unremaped.Class37;
import me.darki.konas.unremaped.Class80;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Entity.class}, priority=0x7FFFFFFF)
public abstract class MixinEntity {
    @Shadow
    private int Field8;
    @Shadow
    protected boolean Field9;
    @Shadow
    public double Field10;
    @Shadow
    public double Field11;
    @Shadow
    public double Field12;

    @Shadow
    public void Method10(MoverType type, double x, double y, double z) {
    }

    @Shadow
    public abstract boolean equals(Object var1);

    @Shadow
    public abstract int Method11();

    @ModifyVariable(method={"addVelocity"}, at=@At(value="HEAD"), ordinal=0)
    private double Method12(double x) {
        Class34 event = Class34.Method287((Entity)this, x, Class36.HORIZONTAL);
        EventDispatcher.Companion.dispatch(event);
        return event.Method88();
    }

    @ModifyVariable(method={"addVelocity"}, at=@At(value="HEAD"), ordinal=1)
    private double Method13(double y) {
        Class34 event = Class34.Method287((Entity)this, y, Class36.VERTICAL);
        EventDispatcher.Companion.dispatch(event);
        return event.Method88();
    }

    @ModifyVariable(method={"addVelocity"}, at=@At(value="HEAD"), ordinal=2)
    private double Method14(double z) {
        Class34 event = Class34.Method287((Entity)this, z, Class36.HORIZONTAL);
        EventDispatcher.Companion.dispatch(event);
        return event.Method88();
    }

    @Inject(method={"move"}, at={@At(value="HEAD")})
    public void Method15(MoverType type, double x, double y, double z, CallbackInfo ci) {
        if (this.Field9) {
            Class80 event = new Class80();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                this.Field9 = false;
            }
        }
    }

    @Inject(method={"turn"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method16(float yaw, float pitch, CallbackInfo ci) {
        Class37 event = new Class37(yaw, pitch);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}