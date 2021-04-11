package me.darki.konas.command.commands;

import cookiedragon.eventsystem.EventDispatcher;
import java.util.regex.Pattern;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.ExtraChat;
import me.darki.konas.unremaped.Class469;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class509;
import me.darki.konas.unremaped.Class595;
import me.darki.konas.util.command.FriendCommandOptions;
import me.darki.konas.unremaped.Class645;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class friendCommand
extends Command {
    public static void Method176(StringBuilder stringBuilder, Class469 class469) {
        stringBuilder.append(class469.Method2205() + "\n");
    }

    public friendCommand() {
        super("friend", "Add and remove friends", new FriendCommandOptions("<add/del/list>"), new Class595("[name]"));
    }

    @Override
    public void Method174(String[] stringArray) {
        block19: {
            block18: {
                if (stringArray.length == 2) {
                    if (stringArray[1].equalsIgnoreCase("list")) {
                        if (!Class492.Method1993().isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\n");
                            Class492.Method1993().stream().forEach(arg_0 -> friendCommand.Method176(stringBuilder, arg_0));
                            Logger.Method1118(stringBuilder.toString());
                        } else {
                            Logger.Method1118("You dont have any friends :(");
                        }
                    } else {
                        Logger.Method1119("Invalid Argument. Check syntax");
                    }
                    return;
                }
                if (stringArray.length != this.Method189().size() + 1) {
                    Logger.Method1118(this.Method191());
                    return;
                }
                if (!stringArray[1].equalsIgnoreCase("add")) break block18;
                if (stringArray[2].equalsIgnoreCase(friendCommand.Field123.player.getName())) {
                    Logger.Method1119("You cant add yourself as a friend");
                    return;
                }
                if (stringArray[2].equalsIgnoreCase("subaru")) {
                    Logger.Method1119("Downloading subaruhack...");
                    return;
                }
                stringArray[2] = stringArray[2].replaceAll(String.valueOf(Pattern.compile("[^a-zA-Z0-9_]{1,16}")), "");
                if (Minecraft.getMinecraft().world.getPlayerEntityByName(stringArray[2]) != null) {
                    if (Class492.Method1989(stringArray[2])) {
                        Logger.Method1118(stringArray[2] + " is already your friend!");
                        return;
                    }
                    EntityPlayer entityPlayer = Minecraft.getMinecraft().world.getPlayerEntityByName(stringArray[2]);
                    Class492.Method1990(entityPlayer.getName(), entityPlayer.getUniqueID().toString().replace("-", ""));
                    if (ModuleManager.getModuleByClass(ExtraChat.class).isEnabled() && ExtraChat.notifyFriended.getValue().booleanValue()) {
                        EventDispatcher.Companion.dispatch(new Class645(stringArray[2], "I just friended you on Konas!"));
                    }
                    Logger.Method1118("Added &b" + stringArray[2] + "&f as friend");
                } else if (Class509.Method1349(stringArray[2]) != null) {
                    if (Class492.Method1989(stringArray[2])) {
                        Logger.Method1118(stringArray[2] + " is already your friend!");
                        return;
                    }
                    Class492.Method1990(Class509.Method1351(Class509.Method1349(stringArray[2])), Class509.Method1349(stringArray[2]));
                    if (ModuleManager.getModuleByClass(ExtraChat.class).isEnabled() && ExtraChat.notifyFriended.getValue().booleanValue()) {
                        EventDispatcher.Companion.dispatch(new Class645(stringArray[2], "I just friended you on Konas!"));
                    }
                    Logger.Method1118("Added &b" + Class509.Method1351(Class509.Method1349(stringArray[2])) + "&f as friend");
                } else {
                    Logger.Method1119("Player not found");
                }
                break block19;
            }
            if (!stringArray[1].equalsIgnoreCase("del")) break block19;
            stringArray[2] = stringArray[2].replaceAll(String.valueOf(Pattern.compile("[^a-zA-Z0-9_]{1,16}")), "");
            if (Class492.Method1989(stringArray[2])) {
                Class492.Method1992(stringArray[2]);
                Logger.Method1118("Removed &b" + stringArray[2] + "&f as friend");
            } else {
                Logger.Method1119("Player not found");
            }
        }
    }
}
