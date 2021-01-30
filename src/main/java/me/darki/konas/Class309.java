package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.Locale;

import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class Class309
extends Module {
    public static Setting<Boolean> Field874 = new Setting<>("Place", true);
    public static Setting<Boolean> Field875 = new Setting<>("Destroy", false);
    public static Setting<Boolean> Field876 = new Setting<>("Limit", false);
    public static Setting<Boolean> Field877 = new Setting<>("RightClick", false);
    public Setting<Float> Field878 = new Setting<>("Timeout", Float.valueOf(1.0f), Float.valueOf(30.0f), Float.valueOf(0.5f), Float.valueOf(0.5f)).Method1191(Field877::getValue);
    public static Setting<Boolean> Field879 = new Setting<>("Use", false);
    public static Setting<Class312> Field880 = new Setting<>("Mode", Class312.FOOD).Method1191(Field879::getValue);
    public static Setting<Class531> Field881 = new Setting<>("Items", new Class531(new String[0]));
    public static Class566 Field882 = new Class566();
    public static boolean Field883 = false;
    public long Field884 = -1L;
    public float[] Field885 = new float[22];
    public int Field886 = 0;
    public int Field887 = -1;

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block6: {
            if (Class309.mc.player == null || Class309.mc.world == null) {
                return;
            }
            if (tickEvent.Method324() != net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) break block6;
            if (((Boolean)Field879.getValue()).booleanValue() && this.Method938(Class309.mc.player.getActiveItemStack()) && Class309.mc.player.getItemInUseCount() <= 0) {
                this.Field887 = Class309.mc.player.inventory.currentItem;
            }
            if (((Boolean)Field876.getValue()).booleanValue()) {
                float f = 0.0f;
                float f2 = 0.0f;
                for (float f3 : this.Field885) {
                    if (!(f3 > 0.0f)) continue;
                    f += f3;
                    f2 += 1.0f;
                }
                f /= f2;
                if ((f = Math.max(f, 0.5f)) < 0.9f) {
                    NewGui.INSTANCE.Field1134.Method746(this, 1000, f);
                } else {
                    NewGui.INSTANCE.Field1134.Method749(this);
                }
            }
        }
    }

    public Class309() {
        super("NoDesync", "Helps prevent desync", Category.MISC, "NoGlitchBlocks");
    }

    @Subscriber
    public void Method536(Class24 class24) {
        if (((Boolean)Field877.getValue()).booleanValue() && !Field883 && !Field882.Method737(1000.0f * ((Float)this.Field878.getValue()).floatValue()) && class24.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            Field882.Method738(0L);
            Class309.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(Class309.mc.player.rotationYaw, Class309.mc.player.rotationPitch, Class309.mc.player.onGround));
        }
        if (class24.getPacket() instanceof CPacketPlayer) {
            if (((Boolean)Field876.getValue()).booleanValue()) {
                if (this.Field884 != -1L) {
                    float f;
                    this.Field885[this.Field886 % this.Field885.length] = f = (float)(System.currentTimeMillis() - this.Field884) / 50.0f;
                    ++this.Field886;
                }
                this.Field884 = System.currentTimeMillis();
            }
        }
    }

    @Subscriber
    public void Method937(Class11 class11) {
        block0: {
            if (!((Boolean)Field874.getValue()).booleanValue()) break block0;
            class11.setCanceled(true);
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (this.Field887 != -1 && ((Boolean)Field879.getValue()).booleanValue()) {
            Class309.mc.player.inventory.currentItem = this.Field887;
            Class309.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field887));
            this.Field887 = -1;
            return;
        }
    }

    @Override
    public void onEnable() {
        Field883 = false;
        this.Field884 = -1L;
        this.Field885 = new float[44];
        this.Field886 = 0;
    }

    public boolean Method938(ItemStack itemStack) {
        if (Field880.getValue() == Class312.ALL) {
            return true;
        }
        if (Field880.getValue() == Class312.FOOD) {
            return itemStack.getItem() instanceof ItemFood;
        }
        return ((Class531)Field881.getValue()).Method1146().contains(itemStack.getDisplayName().toLowerCase(Locale.ENGLISH));
    }

    @Subscriber
    public void Method939(Class1 class1) {
        block0: {
            if (!((Boolean)Field875.getValue()).booleanValue()) break block0;
            class1.setCanceled(true);
        }
    }
}
