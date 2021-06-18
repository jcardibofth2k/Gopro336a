package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.util.Locale;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class NoDesync
extends Module {
    public static Setting<Boolean> place = new Setting<>("Place", true);
    public static Setting<Boolean> destroy = new Setting<>("Destroy", false);
    public static Setting<Boolean> limit = new Setting<>("Limit", false);
    public static Setting<Boolean> rightClick = new Setting<>("RightClick", false);
    public Setting<Float> timeout = new Setting<>("Timeout", Float.valueOf(1.0f), Float.valueOf(30.0f), Float.valueOf(0.5f), Float.valueOf(0.5f)).visibleIf(Field877::getValue);
    public static Setting<Boolean> use = new Setting<>("Use", false);
    public static Setting<Class312> mode = new Setting<>("Mode", Class312.FOOD).visibleIf(Field879::getValue);
    public static Setting<Class531> items = new Setting<>("Items", new Class531(new String[0]));
    public static TimerUtil Field882 = new TimerUtil();
    public static boolean Field883 = false;
    public long Field884 = -1L;
    public float[] Field885 = new float[22];
    public int Field886 = 0;
    public int Field887 = -1;

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block6: {
            if (NoDesync.mc.player == null || NoDesync.mc.world == null) {
                return;
            }
            if (tickEvent.Method324() != net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) break block6;
            if (((Boolean)use.getValue()).booleanValue() && this.Method938(NoDesync.mc.player.getActiveItemStack()) && NoDesync.mc.player.getItemInUseCount() <= 0) {
                this.Field887 = NoDesync.mc.player.inventory.currentItem;
            }
            if (((Boolean)limit.getValue()).booleanValue()) {
                float f = 0.0f;
                float f2 = 0.0f;
                for (float f3 : this.Field885) {
                    if (!(f3 > 0.0f)) continue;
                    f += f3;
                    f2 += 1.0f;
                }
                f /= f2;
                if ((f = Math.max(f, 0.5f)) < 0.9f) {
                    KonasGlobals.INSTANCE.Field1134.Method746(this, 1000, f);
                } else {
                    KonasGlobals.INSTANCE.Field1134.Method749(this);
                }
            }
        }
    }

    public NoDesync() {
        super("NoDesync", "Helps prevent desync", Category.MISC, "NoGlitchBlocks");
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (((Boolean)rightClick.getValue()).booleanValue() && !Field883 && !Field882.GetDifferenceTiming(1000.0f * ((Float)this.timeout.getValue()).floatValue()) && sendPacketEvent.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            Field882.SetCurrentTime(0L);
            NoDesync.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(NoDesync.mc.player.rotationYaw, NoDesync.mc.player.rotationPitch, NoDesync.mc.player.onGround));
        }
        if (sendPacketEvent.getPacket() instanceof CPacketPlayer) {
            if (((Boolean)limit.getValue()).booleanValue()) {
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
            if (!((Boolean)place.getValue()).booleanValue()) break block0;
            class11.setCanceled(true);
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (this.Field887 != -1 && ((Boolean)use.getValue()).booleanValue()) {
            NoDesync.mc.player.inventory.currentItem = this.Field887;
            NoDesync.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field887));
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
        if (mode.getValue() == Class312.ALL) {
            return true;
        }
        if (mode.getValue() == Class312.FOOD) {
            return itemStack.getItem() instanceof ItemFood;
        }
        return ((Class531)items.getValue()).Method1146().contains(itemStack.getDisplayName().toLowerCase(Locale.ENGLISH));
    }

    @Subscriber
    public void Method939(Class1 class1) {
        block0: {
            if (!((Boolean)destroy.getValue()).booleanValue()) break block0;
            class1.setCanceled(true);
        }
    }
}