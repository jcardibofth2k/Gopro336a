package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import io.netty.channel.ChannelHandlerContext;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class419;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NetworkManager.class})
public class MixinNetworkManager {
    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method766(Packet<?> packet, CallbackInfo callbackInfo) {
        SendPacketEvent event = new SendPacketEvent(packet);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        } else if (event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            Class419.Method1113(((CPacketPlayer)event.getPacket()).getYaw(0.0f));
            Class419.Method1116(((CPacketPlayer)event.getPacket()).getPitch(0.0f));
        }
    }

    @Inject(method={"channelRead0"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method767(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent event = new PacketEvent(packet);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"exceptionCaught"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method768(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo ci) {
        ci.cancel();
    }
}