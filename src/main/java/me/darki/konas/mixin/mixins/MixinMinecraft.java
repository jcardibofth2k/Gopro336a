package me.darki.konas.mixin.mixins;

import com.viaversion.viafabric.ViaFabric;
import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class28;
import me.darki.konas.unremaped.Class38;
import me.darki.konas.unremaped.Class651;
import me.darki.konas.unremaped.Class653;
import me.darki.konas.KonasMod;
import me.darki.konas.module.render.NoRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class}, priority=2147483547)
public class MixinMinecraft {
    @Redirect(method={"sendClickBlockToController"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    public boolean Method1746(EntityPlayerSP entityPlayerSP) {
        Class651 event = new Class651(entityPlayerSP.isHandActive());
        EventDispatcher.Companion.dispatch(event);
        return event.Method1157();
    }

    @Inject(method={"runGameLoop"}, at={@At(value="HEAD")})
    private void Method1747(CallbackInfo callbackInfo) {
        Class28 event = new Class28();
        EventDispatcher.Companion.dispatch(event);
    }

    @Redirect(method={"runGameLoop"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/toasts/GuiToast;drawToast(Lnet/minecraft/client/gui/ScaledResolution;)V"))
    public void Method1748(GuiToast guiToast, ScaledResolution resolution) {
        if (ModuleManager.getModuleByClass(NoRender.class).isEnabled() && NoRender.toasts.getValue().booleanValue()) {
            return;
        }
        guiToast.drawToast(resolution);
    }

    @Inject(method={"runTickKeyboard"}, at={@At(value="INVOKE", target="Lnet/minecraftforge/fml/common/FMLCommonHandler;fireKeyInput()V")})
    public void Method1749(CallbackInfo ci) {
        if (Keyboard.getEventKeyState()) {
            Class653 event = new Class653(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey());
            EventDispatcher.Companion.dispatch(event);
        }
    }

    @Inject(method={"runTickMouse"}, at={@At(value="INVOKE", target="Lnet/minecraftforge/fml/common/FMLCommonHandler;fireMouseInput()V")})
    public void Method1750(CallbackInfo ci) {
        if (Mouse.getEventButtonState()) {
            Class653 event = new Class653(Mouse.getEventButton() - 100);
            EventDispatcher.Companion.dispatch(event);
        }
    }

    @Redirect(method={"rightClickMouse"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal=0), require=1)
    public boolean Method1751(PlayerControllerMP playerControllerMP) {
        Class651 event = new Class651(playerControllerMP.getIsHittingBlock());
        EventDispatcher.Companion.dispatch(event);
        return event.Method1157();
    }

    @Redirect(method={"sendClickBlockToController"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;resetBlockRemoving()V"))
    public void Method1752(PlayerControllerMP playerControllerMP) {
        Class38 event = Class38.Method290();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return;
        }
        playerControllerMP.resetBlockRemoving();
    }

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void Method1753(GameConfiguration p_i45547_1_, CallbackInfo ci) {
        try {
            ViaFabric.getInstance().start();
        }
        catch (Exception e) {
            KonasMod.LOGGER.error("Error starting ViaVersion");
        }
    }
}