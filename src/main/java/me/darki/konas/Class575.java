package me.darki.konas;

import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class Class575
extends Command {
    public Class575() {
        super("disconnect", "Kick yourself from a server");
    }

    @Override
    public void Method174(String[] stringArray) {
        block0: {
            if (Class575.Field123.player == null) break block0;
            Class575.Field123.player.connection.sendPacket(new CPacketHeldItemChange(69420));
        }
    }
}
