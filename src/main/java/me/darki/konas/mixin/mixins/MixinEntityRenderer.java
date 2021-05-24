package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import java.util.Objects;
import me.darki.konas.unremaped.Class110;
import me.darki.konas.unremaped.Class14;
import me.darki.konas.unremaped.Class3;
import me.darki.konas.unremaped.Class33;
import me.darki.konas.unremaped.Class35;
import me.darki.konas.unremaped.Class40;
import me.darki.konas.ViewPort;
import me.darki.konas.unremaped.Class46;
import me.darki.konas.unremaped.Class5;
import me.darki.konas.unremaped.EspRenderUtil;
import me.darki.konas.unremaped.Class61;
import me.darki.konas.unremaped.Class67;
import me.darki.konas.unremaped.Class75;
import me.darki.konas.unremaped.Class76;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.unremaped.Class93;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityRenderer.class}, priority=0x7FFFFFFF)
public class MixinEntityRenderer {
    @Inject(method={"setupCameraTransform"}, at={@At(value="HEAD")})
    private void Method154(float partialTicks, int pass, CallbackInfo ci) {
        Class75 event = new Class75();
        EventDispatcher.Companion.dispatch(event);
    }

    @Inject(method={"setupCameraTransform"}, at={@At(value="TAIL")})
    private void Method155(float partialTicks, int pass, CallbackInfo ci) {
        Class67 event = new Class67();
        EventDispatcher.Companion.dispatch(event);
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal=1, shift=At.Shift.BEFORE)})
    private void Method156(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        if (Display.isActive() || Display.isVisible()) {
            EspRenderUtil.Method1386();
            EspRenderUtil.Method1385();
            Render3DEvent event = Render3DEvent.Method437(partialTicks);
            EventDispatcher.Companion.dispatch(event);
            GlStateManager.resetColor();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Inject(method={"renderWorldPass"}, at={@At(value="TAIL")})
    private void Method157(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        if (Display.isActive() || Display.isVisible()) {
            Class14 pEvent = new Class14();
            EventDispatcher.Companion.dispatch(pEvent);
        }
    }

    @Inject(method={"hurtCameraEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method158(float ticks, CallbackInfo info) {
        Class93 event = new Class93();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }

    @Inject(method={"isDrawBlockOutline"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method159(CallbackInfoReturnable<Boolean> cir) {
        Class40 event = Class40.Method280();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method={"getMouseOver"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;")}, cancellable=true)
    public void Method160(float partialTicks, CallbackInfo ci) {
        Class33 event = new Class33();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=3, at=@At(value="STORE", ordinal=0), require=1)
    public double Method161(double distance) {
        Class3 event = new Class3(distance);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return event.Method216();
        }
        return distance;
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=7, at=@At(value="STORE", ordinal=0), require=1)
    public double Method162(double distance) {
        Class5 event = new Class5(distance);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return event.Method216();
        }
        return distance;
    }

    @ModifyVariable(method={"getMouseOver"}, ordinal=0, index=2, name={"entity"}, at=@At(value="STORE", ordinal=0))
    private Entity Method163(Entity entity) {
        Class76 event = new Class76(entity);
        EventDispatcher.Companion.dispatch(event);
        return event.Method605();
    }

    @Inject(method={"renderHand"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method164(CallbackInfo ci) {
        Class110 event = new Class110();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"updateCameraAndRender"}, at={@At(value="HEAD")})
    private void Method165(float partialTicks, long nanoTime, CallbackInfo ci) {
        Class61 event = new Class61();
        EventDispatcher.Companion.dispatch(event);
    }

    @Inject(method={"updateCameraAndRender"}, at={@At(value="TAIL")})
    private void Method166(float partialTicks, long nanoTime, CallbackInfo ci) {
        Class35 event = new Class35();
        EventDispatcher.Companion.dispatch(event);
    }

    @Redirect(method={"setupCameraTransform"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void Method167(float fovy, float aspect, float zNear, float zFar) {
        ViewPort.Method569(fovy, aspect, zNear, zFar);
    }

    @Redirect(method={"renderHand"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void Method168(float fovy, float aspect, float zNear, float zFar) {
        ViewPort.Method570(fovy, aspect, zNear, zFar, true);
    }

    @Redirect(method={"renderWorldPass"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void Method169(float fovy, float aspect, float zNear, float zFar) {
        ViewPort.Method569(fovy, aspect, zNear, zFar);
    }

    @Redirect(method={"renderCloudsCheck"}, at=@At(value="INVOKE", target="Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void Method170(float fovy, float aspect, float zNear, float zFar) {
        ViewPort.Method569(fovy, aspect, zNear, zFar);
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private Entity Method171(Minecraft mc) {
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled() && Keyboard.isKeyDown(56)) {
            return mc.player;
        }
        return mc.getRenderViewEntity();
    }

    @Redirect(method={"updateCameraAndRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void Method172(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            Class46 event = new Class46();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                if (Keyboard.isKeyDown(56)) {
                    mc.player.turn(yaw, pitch);
                } else {
                    Objects.requireNonNull(mc.getRenderViewEntity(), "Render Entity").turn(yaw, pitch);
                }
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        entityPlayerSP.turn(yaw, pitch);
    }

    @Redirect(method={"renderWorldPass"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
    public boolean Method173(EntityPlayerSP entityPlayerSP) {
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return true;
        }
        if (entityPlayerSP != null) {
            return entityPlayerSP.isSpectator();
        }
        return false;
    }
}