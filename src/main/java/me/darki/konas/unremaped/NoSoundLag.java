package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.util.Arrays;
import java.util.List;

import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundEvent;

public class NoSoundLag
extends Module {
    public static List<SoundEvent> Field594 = Arrays.asList(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);

    public NoSoundLag() {
        super("NoSoundLag", "Prevents users from lagging you with sound", Category.MISC, "AntiSoundCrash");
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            SPacketSoundEffect sPacketSoundEffect;
            if (!(packetEvent.getPacket() instanceof SPacketSoundEffect) || !Field594.contains((sPacketSoundEffect = (SPacketSoundEffect) packetEvent.getPacket()).getSound())) break block0;
            packetEvent.setCanceled(true);
        }
    }
}