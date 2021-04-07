package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.event.events.FogColorsEvent;
import me.darki.konas.unremaped.Class650;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderGlobal.class})
public class MixinRenderGlobal {
    @Redirect(method={"renderSky(FI)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/WorldClient;getSkyColor(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d Method17(WorldClient worldClient, Entity entityIn, float partialTicks) {
        Vec3d sky = Minecraft.getMinecraft().world.getSkyColor(entityIn, partialTicks);
        FogColorsEvent event = new FogColorsEvent((float)sky.x, (float)sky.y, (float)sky.z);
        EventDispatcher.Companion.dispatch(event);
        return new Vec3d(event.Method213(), event.Method215(), event.Method214());
    }

    @ModifyVariable(method={"setupTerrain"}, at=@At(value="HEAD"))
    private boolean Method18(boolean playerSpectator) {
        Class650 event = Class650.Method1165();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return true;
        }
        return playerSpectator;
    }
}