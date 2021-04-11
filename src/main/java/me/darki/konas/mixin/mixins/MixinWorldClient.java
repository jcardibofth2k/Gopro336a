package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class77;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={WorldClient.class})
public class MixinWorldClient {
    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void Method28(CallbackInfo callbackInfo) {
        ModuleManager.handleWorldJoin();
        Class77 event = new Class77();
        EventDispatcher.Companion.dispatch(event);
    }
}