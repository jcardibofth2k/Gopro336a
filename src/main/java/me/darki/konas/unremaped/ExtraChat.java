package me.darki.konas.unremaped;

import com.mojang.realmsclient.gui.ChatFormatting;
import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.command.Command;
import me.darki.konas.command.commands.PartyCommand;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ExtraChat
extends Module {
    public static Setting<Float> delay = new Setting<>("Delay", Float.valueOf(0.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public static Setting<Class295> mode = new Setting<>("Mode", Class295.MSG);
    public static Setting<Boolean> autoBackup = new Setting<>("AutoBackup", false);
    public static Setting<Boolean> global = new Setting<>("Global", false).visibleIf(Field1441::getValue);
    public Setting<Boolean> popBackup = new Setting<>("PopBackup", true).visibleIf(Field1441::getValue);
    public Setting<Boolean> taunt = new Setting<>("Taunt", false);
    public Setting<Boolean> antiRacism = new Setting<>("AntiRacism", false);
    public Setting<Boolean> antiCoordLeak = new Setting<>("AntiCoordLeak", false);
    public Setting<Boolean> embarrass = new Setting<>("Embarrass", false).visibleIf(this.Field1445::getValue);
    public Setting<Boolean> autoGroom = new Setting<>("AutoGroom", false);
    public Setting<Float> tauntDelay = new Setting<>("TauntDelay", Float.valueOf(15.0f), Float.valueOf(30.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).visibleIf(this.Field1444::getValue);
    public Setting<Float> groomDelay = new Setting<>("GroomDelay", Float.valueOf(15.0f), Float.valueOf(30.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).visibleIf(this.Field1448::getValue);
    public static Setting<Boolean> notifyFriended = new Setting<>("NotifyFriended", false);
    public Setting<Boolean> partyChat = new Setting<>("PartyChat", false);
    public Setting<Boolean> longer = new Setting<>("Longer", false);
    public Setting<Integer> chatHeight = new Setting<>("ChatHeight", 200, 500, 0, 0).visibleIf(this.Field1453::getValue);
    public Setting<Boolean> clear = new Setting<>("Clear", false);
    public Setting<Boolean> chatTimestamps = new Setting<>("ChatTimestamps", true);
    public Setting<Boolean> 24HourFormat = new Setting<>("24HourFormat", true).visibleIf(this.Field1456::getValue);
    public Setting<Boolean> hours = new Setting<>("Hours", true).visibleIf(this.Field1456::getValue);
    public Setting<Boolean> Field1459;
    public Setting<Boolean> Field1460;
    public Setting<Boolean> Field1461;
    public Random Field1462;
    public static ArrayList<String> Field1463 = new ArrayList();
    public static ArrayList<String> Field1464 = new ArrayList();

    @Subscriber
    public void Method1498(ClientChatReceivedEvent clientChatReceivedEvent) {
        block7: {
            Class645 class645;
            Object object;
            if (((Boolean)this.antiRacism.getValue()).booleanValue() && (clientChatReceivedEvent.getMessage().getUnformattedText().toLowerCase().contains("nigger") || clientChatReceivedEvent.getMessage().getUnformattedText().toLowerCase().contains("nigga")) && ((Optional)(object = KonasChat.Method715(clientChatReceivedEvent.getMessage().getUnformattedText()))).isPresent()) {
                if (((Boolean)this.embarrass.getValue()).booleanValue()) {
                    ExtraChat.mc.player.sendChatMessage((String)((Map.Entry)((Optional)object).get()).getKey() + ", don't be racist");
                } else {
                    class645 = new Class645((String)((Map.Entry)((Optional)object).get()).getKey(), "Don't be racist");
                    EventDispatcher.Companion.dispatch(class645);
                }
            }
            if (!((Boolean)this.chatTimestamps.getValue()).booleanValue()) break block7;
            object = new StringBuilder();
            if (((Boolean)this.Field1461.getValue()).booleanValue()) {
                ((StringBuilder)object).append("<");
            }
            if (((Boolean)this.hours.getValue()).booleanValue()) {
                ((StringBuilder)object).append((Boolean)this.24HourFormat.getValue() != false ? "HH" : "hh").append((Boolean)this.Field1459.getValue() != false || (Boolean)this.Field1460.getValue() != false ? ":" : "");
            }
            if (((Boolean)this.Field1459.getValue()).booleanValue()) {
                ((StringBuilder)object).append("mm").append((Boolean)this.Field1460.getValue() != false ? ":" : "");
            }
            if (((Boolean)this.Field1460.getValue()).booleanValue()) {
                ((StringBuilder)object).append("ss");
            }
            ((StringBuilder)object).append((Boolean)this.24HourFormat.getValue() != false ? "" : "aa").append((Boolean)this.Field1461.getValue() != false ? "> " : " ");
            class645 = new TextComponentString(ChatFormatting.GRAY + new SimpleDateFormat(((StringBuilder)object).toString()).format(new Date()) + ChatFormatting.RESET);
            class645.appendSibling(clientChatReceivedEvent.getMessage());
            clientChatReceivedEvent.setMessage((ITextComponent)class645);
        }
    }

    @Subscriber
    public void Method552(Class648 class648) {
        if (((Boolean)this.antiCoordLeak.getValue()).booleanValue() && class648.Method1201().replaceAll("\\D", "").length() >= 6) {
            class648.Cancel();
            ChatUtil.Method1034("AntiCoordLeak: Blocked message because it contained 6 or more digits", new Object[0]);
            return;
        }
        if (((Boolean)this.partyChat.getValue()).booleanValue() && !class648.Method1201().startsWith(Command.Method190()) && !class648.Method1201().startsWith("/")) {
            class648.Cancel();
            for (String string : PartyCommand.Field2509) {
                Class645 class645 = new Class645(string, class648.Method1201());
                EventDispatcher.Companion.dispatch(class645);
            }
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        Class645 class645;
        Entity entity;
        if (ExtraChat.mc.player == null || ExtraChat.mc.world == null) {
            return;
        }
        if (((Boolean)this.autoGroom.getValue()).booleanValue() && Class51.Field222.isEmpty() && Class51.Field223.GetDifferenceTiming(((Float)this.groomDelay.getValue()).floatValue() * 1000.0f) && (entity = KonasGlobals.INSTANCE.Field1133.Method421()) != null && !Class492.Method1989(entity.getName())) {
            class645 = new Class645(entity.getName(), Field1464.get(this.Field1462.nextInt(Field1464.size())).replaceAll("<player>", entity.getName()));
            EventDispatcher.Companion.dispatch(class645);
        }
        if (((Boolean)this.taunt.getValue()).booleanValue() && Class51.Field222.isEmpty() && Class51.Field223.GetDifferenceTiming(((Float)this.tauntDelay.getValue()).floatValue() * 1000.0f) && (entity = KonasGlobals.INSTANCE.Field1133.Method421()) != null) {
            if (!Class492.Method1989(entity.getName())) {
                class645 = new Class645(entity.getName(), Field1463.get(this.Field1462.nextInt(Field1463.size())).replaceAll("<player>", entity.getName()));
                EventDispatcher.Companion.dispatch(class645);
            }
        }
    }

    public ExtraChat() {
        super("ExtraChat", Category.MISC, "ChatTimestamp", "ChatTimestamps", "ClearChat");
        Setting setting = new Setting<>("Minutes", true);
        Setting<Boolean> setting2 = this.Field1456;
        setting2.getClass();
        this.Field1459 = setting.visibleIf(setting2::getValue);
        this.Field1460 = new Setting<>("Seconds", false).visibleIf(this.Field1456::getValue);
        this.Field1461 = new Setting<>("Brackets", true).visibleIf(this.Field1456::getValue);
        this.Field1462 = new Random();
        Field1463.add("Lol, you're so ez <player>");
        Field1463.add("ur bad");
        Field1463.add("ur fat");
        Field1463.add("you're dogwater kid");
        Field1463.add("im going to destroy you");
        Field1463.add("you like men");
        Field1464.add("Send thigh pics");
        Field1464.add("Are you a femboy?");
        Field1464.add("Where do you live?");
        Field1464.add("How old are you?");
        Field1464.add("Let's have sex");
        Field1464.add("Send skirt pics");
        Field1464.add("I'll get you nitro if you send thigh pics");
        Field1464.add("Hewwo cutie");
        Field1464.add("AwA Let's have sex");
    }

    @Subscriber
    public void Method1499(Class55 class55) {
        block0: {
            if (!((Boolean)this.longer.getValue()).booleanValue()) break block0;
            class55.Method343((Integer)this.chatHeight.getValue());
        }
    }

    @Subscriber
    public void Method1500(Class43 class43) {
        if (((Boolean)autoBackup.getValue()).booleanValue() && ((Boolean)this.popBackup.getValue()).booleanValue() && class43.Method265().getUniqueID().equals(ExtraChat.mc.player.getUniqueID())) {
            String string = "I just popped at X:" + ExtraChat.mc.player.getPosition().getX() + " Y:" + ExtraChat.mc.player.getPosition().getY() + " Z:" + ExtraChat.mc.player.getPosition().getZ() + " in the " + (ExtraChat.mc.player.dimension == -1 ? "Nether" : "Overworld") + ", and I need backup!";
            if (((Boolean)global.getValue()).booleanValue()) {
                ExtraChat.mc.player.sendChatMessage(string);
            } else {
                for (String string2 : PartyCommand.Field2509) {
                    Class645 class645 = new Class645(string2, string);
                    EventDispatcher.Companion.dispatch(class645);
                }
            }
        }
    }

    @Subscriber
    public void Method1501(Class647 class647) {
        block0: {
            if (!((Boolean)this.clear.getValue()).booleanValue()) break block0;
            class647.Cancel();
        }
    }
}