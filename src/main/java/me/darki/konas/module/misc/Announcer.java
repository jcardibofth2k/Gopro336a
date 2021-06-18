package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.TimerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class Announcer
extends Module {
    public Setting<Boolean> welcome = new Setting<>("Welcome", true);
    public Setting<Float> welcomeDelay = new Setting<>("WelcomeDelay", Float.valueOf(2.0f), Float.valueOf(60.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public Setting<Boolean> announce = new Setting<>("Announce", false);
    public Setting<Float> announceDelay = new Setting<>("AnnounceDelay", Float.valueOf(20.0f), Float.valueOf(60.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
    public Setting<Float> globalDelay = new Setting<>("GlobalDelay", Float.valueOf(1.0f), Float.valueOf(60.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public Setting<Boolean> advertisement = new Setting<>("Advertisement", true);
    public static ArrayList<String> arrayList = new ArrayList();
    public static ArrayList<String> arrayList1 = new ArrayList();
    public Random random = new Random();
    public Random random1 = new Random();
    public TimerUtil Field2492 = new TimerUtil();
    public TimerUtil Field2493 = new TimerUtil();
    public TimerUtil Field2494 = new TimerUtil();
    public LinkedHashMap<Class357, Integer> Field2495 = new LinkedHashMap();
    public static double Field2496;
    public static double Field2497;
    public static double Field2498;

    public Announcer() {
        super("Announcer", Category.MISC, "Welcomer");
        arrayList.add("Welcome <player>!");
        arrayList.add("Hello <player>");
        arrayList.add("Nice weather isn't it, <player>");
        arrayList1.add("Goodbye <player>!");
        arrayList1.add("Have a good day <player>");
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block1: {
            CPacketPlayerDigging cPacketPlayerDigging;
            block2: {
                block0: {
                    if (!(sendPacketEvent.getPacket() instanceof CPacketPlayerTryUseItemOnBlock)) break block0;
                    this.Method2135(Class357.PLACE);
                    break block1;
                }
                if (!(sendPacketEvent.getPacket() instanceof CPacketPlayerDigging)) break block1;
                cPacketPlayerDigging = (CPacketPlayerDigging) sendPacketEvent.getPacket();
                if (cPacketPlayerDigging.getAction() != CPacketPlayerDigging.Action.DROP_ALL_ITEMS && cPacketPlayerDigging.getAction() != CPacketPlayerDigging.Action.DROP_ITEM) break block2;
                this.Method2135(Class357.DROP);
                break block1;
            }
            if (cPacketPlayerDigging.getAction() != CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) break block1;
            this.Method2135(Class357.BREAK);
        }
    }

    public double Method1170(double d, double d2, double d3) {
        double d4 = d - Announcer.mc.player.posX;
        double d5 = d2 - Announcer.mc.player.posY;
        double d6 = d3 - Announcer.mc.player.posZ;
        return Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
    }

    public String Method2133(Class357 class357, int n) {
        String string = this.advertisement.getValue() != false ? " thanks to Konas!" : "!";
        String string2 = n > 1 ? "s" : "";
        switch (Class350.Field2538[class357.ordinal()]) {
            case 1: {
                return "I just picked up " + n + " item" + string2 + string;
            }
            case 2: {
                return "I just crafted " + n + " item" + string2 + string;
            }
            case 3: {
                return "I just broke " + n + " block" + string2 + string;
            }
            case 4: {
                return "I just placed " + n + " block" + string2 + string;
            }
            case 5: {
                return "I just dropped " + n + " item" + string2 + string;
            }
            case 6: {
                return "I just ate " + n + " item" + string2 + string;
            }
            case 7: {
                return "I just jumped " + n + " time" + string2 + string;
            }
            case 8: {
                return "I just walked " + n + " block" + string2 + string;
            }
        }
        return "I just did " + class357 + " " + n + " time" + string2 + string;
    }

    public void Method714(String string) {
        Announcer.mc.player.sendChatMessage(arrayList1.get(this.random.nextInt(arrayList1.size())).replace("<player>", string));
    }

    public void Method2134(String string) {
        Announcer.mc.player.sendChatMessage(arrayList.get(this.random.nextInt(arrayList.size())).replace("<player>", string));
    }

    public void Method2135(Class357 class357) {
        if (this.Field2495.containsKey(class357)) {
            this.Field2495.put(class357, this.Field2495.get(class357) + 1);
        } else {
            this.Field2495.put(class357, 1);
        }
    }

    @Subscriber
    public void Method2136(LivingEvent.LivingJumpEvent livingJumpEvent) {
        block0: {
            if (!livingJumpEvent.getEntityLiving().equals(Announcer.mc.player)) break block0;
            this.Method2135(Class357.JUMP);
        }
    }

    @Subscriber
    public void Method2137(finishUseItemEvent finishUseItemEvent) {
        block0: {
            if (finishUseItemEvent.Method269() != Announcer.mc.player) break block0;
            this.Method2135(Class357.EAT);
        }
    }

    @Subscriber
    public void Method2138(PlayerEvent.ItemCraftedEvent itemCraftedEvent) {
        block0: {
            if (!itemCraftedEvent.player.equals(Announcer.mc.player)) break block0;
            this.Method2135(Class357.CRAFT);
        }
    }

    @Subscriber
    public void Method2139(PlayerEvent.ItemPickupEvent itemPickupEvent) {
        block0: {
            if (!itemPickupEvent.player.equals(Announcer.mc.player)) break block0;
            this.Method2135(Class357.PICKUP);
        }
    }

    @Subscriber
    public void Method1798(Class16 class16) {
        block1: {
            if (Announcer.mc.player == null || Announcer.mc.world == null) {
                return;
            }
            if (!this.welcome.getValue().booleanValue() || Announcer.mc.player.ticksExisted <= 100 || class16.Method209() == Announcer.mc.player.getUniqueID() || !this.Field2492.GetDifferenceTiming(this.welcomeDelay.getValue().floatValue() * 1000.0f) || !this.Field2494.GetDifferenceTiming(this.globalDelay.getValue().floatValue() * 1000.0f)) break block1;
            this.Method2134(class16.Method211());
            this.Field2492.UpdateCurrentTime();
            this.Field2494.UpdateCurrentTime();
        }
    }

    @Subscriber
    public void Method1796(uuidHelper uuidHelper) {
        block1: {
            EntityPlayer entityPlayer;
            if (Announcer.mc.player == null || Announcer.mc.world == null) {
                return;
            }
            if (!this.welcome.getValue().booleanValue() || Announcer.mc.player.ticksExisted <= 100 || uuidHelper.Method209() == Announcer.mc.player.getUniqueID() || !this.Field2492.GetDifferenceTiming(this.welcomeDelay.getValue().floatValue() * 1000.0f) || !this.Field2494.GetDifferenceTiming(this.globalDelay.getValue().floatValue() * 1000.0f) || (entityPlayer = Announcer.mc.world.getPlayerEntityByUUID(uuidHelper.Method209())) == null) break block1;
            this.Method714(entityPlayer.getName());
            this.Field2492.UpdateCurrentTime();
            this.Field2494.UpdateCurrentTime();
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Announcer.mc.player == null || Announcer.mc.world == null) {
            return;
        }
        if (this.announce.getValue().booleanValue() && this.Field2494.GetDifferenceTiming(this.globalDelay.getValue().floatValue() * 1000.0f) && this.Field2493.GetDifferenceTiming(this.announceDelay.getValue().floatValue() * 1000.0f)) {
            double d = this.Method1170(Field2496, Field2497, Field2498);
            if (d > 0.0 && d < 5000.0 && Announcer.mc.player.ticksExisted > 1000) {
                this.Field2495.put(Class357.WALK, (int)d);
            }
            Field2496 = Announcer.mc.player.posX;
            Field2497 = Announcer.mc.player.posY;
            Field2498 = Announcer.mc.player.posZ;
            if (this.Field2495.isEmpty()) {
                return;
            }
            int n = this.random1.nextInt(this.Field2495.entrySet().size());
            for (int i = 0; i < this.Field2495.entrySet().size(); ++i) {
                if (i != n) continue;
                ArrayList<Map.Entry<Class357, Integer>> arrayList = new ArrayList<Map.Entry<Class357, Integer>>(this.Field2495.entrySet());
                Map.Entry entry = arrayList.get(i);
                Announcer.mc.player.sendChatMessage(this.Method2133((Class357) entry.getKey(), (Integer)entry.getValue()));
                this.Field2493.UpdateCurrentTime();
                this.Field2494.UpdateCurrentTime();
                this.Field2495.clear();
            }
        }
    }
}