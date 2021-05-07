package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Class595
extends SyntaxChunk {
    public Class595(String string) {
        super(string);
    }

    public String Method177(String string) {
        for (EntityPlayer entityPlayer : Minecraft.getMinecraft().world.playerEntities) {
            if (!entityPlayer.getName().toLowerCase().startsWith(string.toLowerCase())) continue;
            return entityPlayer.getName();
        }
        return string;
    }
}