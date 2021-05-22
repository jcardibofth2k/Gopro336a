package me.darki.konas.unremaped;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class disconectCommand
extends Command {
    public disconectCommand() {
        super("disconnect", "Kick yourself from a server", new SyntaxChunk[0]);
    }

    @Override
    public void Method174(String[] stringArray) {
        block0: {
            if (disconectCommand.Field123.player == null) break block0;
            disconectCommand.Field123.player.connection.sendPacket((Packet)new CPacketHeldItemChange(69420));
        }
    }
}