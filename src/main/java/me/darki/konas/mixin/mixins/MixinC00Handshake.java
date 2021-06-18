package me.darki.konas.mixin.mixins;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.client.NoForge;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={C00Handshake.class})
public class MixinC00Handshake {
    @Shadow
    int Field269;
    @Shadow
    String Field270;
    @Shadow
    int Field271;
    @Shadow
    EnumConnectionState Field272;

    @Inject(method={"writePacketData"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method446(PacketBuffer buf, CallbackInfo info) {
        if (ModuleManager.getModuleByClass(NoForge.class).isEnabled()) {
            System.out.println("Cancelling packet data");
            info.cancel();
            buf.writeVarInt(this.Field269);
            buf.writeString(this.Field270);
            buf.writeShort(this.Field271);
            buf.writeVarInt(this.Field272.getId());
        }
    }
}