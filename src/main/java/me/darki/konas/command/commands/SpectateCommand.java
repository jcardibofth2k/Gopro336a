package me.darki.konas.command.commands;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class595;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;

public class SpectateCommand
extends Command {
    public EntityPlayer Field347;
    public boolean Field348 = false;

    public void Method508() {
        this.Field348 = false;
        Field123.setRenderViewEntity(SpectateCommand.Field123.player);
        Logger.Method1118("Stopped spectating " + Command.Field122 + "b" + this.Field347.getName());
        this.Field347 = null;
    }

    public SpectateCommand() {
        super("spectate", "Makes you spectate other players", new Class595("<player>"));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Subscriber
    public void Method509(SendPacketEvent sendPacketEvent) {
        block2: {
            if (SpectateCommand.Field123.world == null || SpectateCommand.Field123.player == null) {
                return;
            }
            if (!this.Field348) {
                return;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer.PositionRotation) && !(sendPacketEvent.getPacket() instanceof CPacketPlayer.Position) && !(sendPacketEvent.getPacket() instanceof CPacketPlayer.Rotation) && (!(sendPacketEvent.getPacket() instanceof CPacketAnimation) || !this.Field348)) break block2;
            sendPacketEvent.setCanceled(true);
        }
    }

    @Subscriber
    public void Method510(TickEvent tickEvent) {
        block2: {
            if (SpectateCommand.Field123.world == null || SpectateCommand.Field123.player == null) {
                return;
            }
            if (!this.Field348) {
                return;
            }
            if (SpectateCommand.Field123.world.getEntityByID(this.Field347.getEntityId()) != null) break block2;
            this.Method508();
        }
    }

    @Subscriber
    public void Method511(PacketEvent packetEvent) {
        block6: {
            SPacketAnimation sPacketAnimation;
            block5: {
                block4: {
                    if (SpectateCommand.Field123.world == null) break block4;
                    if (SpectateCommand.Field123.player != null) break block5;
                }
                return;
            }
            if (!this.Field348) {
                return;
            }
            if (!(packetEvent.getPacket() instanceof SPacketAnimation) || (sPacketAnimation = (SPacketAnimation) packetEvent.getPacket()).getAnimationType() != 0) break block6;
            SpectateCommand.Field123.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        if (stringArray[1].equals("off") && this.Field347 != null) {
            this.Method508();
            return;
        }
        if (SpectateCommand.Field123.world.getPlayerEntityByName(stringArray[1]) != null) {
            this.Field347 = SpectateCommand.Field123.world.getPlayerEntityByName(stringArray[1]);
            this.Field348 = true;
            Field123.setRenderViewEntity(this.Field347);
            Logger.Method1118("You are now spectating " + Command.Field122 + "b" + this.Field347.getName());
        } else {
            Logger.Method1118("Cant find player " + Command.Field122 + "b" + stringArray[1]);
        }
    }
}
