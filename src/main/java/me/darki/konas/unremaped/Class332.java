package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import me.darki.konas.module.misc.ChatAppend;
import me.darki.konas.util.LanguageUtil;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;

public class Class332
extends Module {
    public static ReentrantLock Field433 = new ReentrantLock();
    public boolean Field434 = false;
    public static String Field435 = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void Method550(String[] stringArray, String[] stringArray2) {
        try {
            ArrayList<String> arrayList;
            Field433.lock();
            arrayList();
            ArrayList<String> arrayList2 = arrayList;
            for (String string : stringArray) {
                arrayList2.add(LanguageUtil.Method750(string));
            }
            stringArray2[0] = arrayList2.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            Logger.Method1118("Couldnt find Input Language");
        }
        finally {
            Field433.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void Method551(String[] stringArray, String string, String[] stringArray2, String string2) {
        try {
            Field433.lock();
            stringArray[0] = LanguageUtil.Method751(string, stringArray2[0], string2);
            if (!stringArray[0].equals("")) {
                this.Field434 = true;
                Class332.mc.player.sendChatMessage(stringArray[0]);
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            Logger.Method1118("Couldn't translate message");
        }
        finally {
            Field433.unlock();
        }
    }

    public Class332() {
        super("Translate", "Translate your chat message into any language", Category.MISC, new String[0]);
    }

    @Subscriber
    public void Method552(Class648 class648) {
        Thread thread;
        Thread thread2;
        String string = class648.Method1201();
        if (string.startsWith("/") || string.startsWith(Command.Method190())) {
            return;
        }
        if (this.Field434) {
            class648.Cancel();
            this.Field434 = false;
        }
        if (string.endsWith(ChatAppend.string)) {
            string = string.substring(0, string.length() - ChatAppend.string.length());
        }
        String[] stringArray = string.split(" ");
        String[] stringArray2 = new String[]{null};
        Thread thread3 = thread2;
        Thread thread4 = thread2;
        Runnable runnable = () -> Class332.Method550(stringArray, stringArray2);
        thread3(runnable);
        try {
            thread4.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        String[] stringArray3 = new String[]{""};
        String string2 = Field435;
        String string3 = string;
        Thread thread5 = thread;
        Thread thread6 = thread;
        Runnable runnable2 = () -> this.Method551(stringArray3, string3, stringArray2, string2);
        thread5(runnable2);
        try {
            thread6.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        block0: {
            if (Field435 != null) break block0;
            Logger.Method1119("Target language is null, please use the " + Command.Method190() + "language command to set your target language!");
            this.toggle();
        }
    }
}