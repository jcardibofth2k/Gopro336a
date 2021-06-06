package me.darki.konas.module.client;

//import com.konasclient.loader.Loader;
import cookiedragon.eventsystem.EventDispatcher;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.config.Config;
import me.darki.konas.event.EventProcessor;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class147;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class230;
import me.darki.konas.managers.CommandManager;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.CommandUtil;
import me.darki.konas.util.ConfigUtil;
import me.darki.konas.util.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import sun.misc.Unsafe;

public class KonasGlobals {
    public static KonasGlobals INSTANCE = new KonasGlobals();
    public Class193 Field1130;
    public Class147 Field1131;
    public Class166 Field1132;
    public Class87 Field1133;
    public Class565 Field1134;
    public Class518 Field1135;
    public Class109 Field1136;
    public Class487 Field1137;
    public Class562 Field1138;
    public RotationUtil Field1139;
    public DiscordRpcUtil Field1140;

    public void init() {
        ModuleManager.Method1620();
        CommandManager.add();
        (this.Field1136 = new Class109()).Method2200();
        try {
            final Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            //if (((Unsafe)declaredField.get(null)).getByte(Loader.INSTANCE.getAddress()) == 111) {
            //This is to "crack" the client
            if (true) {
                MinecraftForge.EVENT_BUS.register((Object)EventProcessor.INSTANCE);
                EventDispatcher.Companion.register(Class54.Field200);
                EventDispatcher.Companion.register(CommandUtil.Field163);
                EventDispatcher.Companion.subscribe(Class54.Field200);
                EventDispatcher.Companion.subscribe(CommandUtil.Field163);
                EventDispatcher.Companion.register(Class456.Field486);
                EventDispatcher.Companion.subscribe(Class456.Field486);
                EventDispatcher.Companion.register(Class51.Field221);
                EventDispatcher.Companion.subscribe(Class51.Field221);
                EventDispatcher.Companion.register(Class81.Field274);
                EventDispatcher.Companion.subscribe(Class81.Field274);
                EventDispatcher.Companion.register(ConfigUtil.Field532);
                EventDispatcher.Companion.subscribe(ConfigUtil.Field532);
            }
        }
        catch (Exception ex) {}
        (this.Field1130 = new Class193()).Method121();
        (this.Field1131 = new Class147()).Method613();
        Class230.Method2171();
        this.Field1133 = new Class87();
        EventDispatcher.Companion.register(this.Field1133);
        EventDispatcher.Companion.subscribe(this.Field1133);
        this.Field1134 = new Class565();
        EventDispatcher.Companion.register(this.Field1134);
        EventDispatcher.Companion.subscribe(this.Field1134);
        this.Field1137 = new Class487();
        EventDispatcher.Companion.register(this.Field1137);
        EventDispatcher.Companion.subscribe(this.Field1137);
        this.Field1139 = new RotationUtil();
        EventDispatcher.Companion.register(this.Field1139);
        EventDispatcher.Companion.subscribe(this.Field1139);
        this.Field1138 = new Class562();
        this.Field1135 = new Class518();
        Class473.Field2557 = new Class473();
        (this.Field1132 = new Class166(Minecraft.getMinecraft().currentScreen)).Method121();
        Config.Method2274(Config.Method2223(), true);
        Runtime.getRuntime().addShutdownHook(new Class588());
        try {
            if (!System.getProperty("os.arch").equals("aarch64")) {
                this.Field1140 = new DiscordRpcUtil();
            }
            else {
                this.Field1140 = null;
            }
        }
        catch (Exception ex2) {
            this.Field1140 = null;
        }
    }

    public void preInit() {
        ModuleManager.Field1692.start();
        if (!Config.KONAS_FOLDER.exists()) {
            Config.KONAS_FOLDER.mkdir();
        }
        if (!Config.CONFIGS.exists()) {
            Config.CONFIGS.mkdir();
        }
        if (Config.KONAS_FOLDER.listFiles() != null) {
            List<File> list = Arrays.stream(Config.KONAS_FOLDER.listFiles()).filter(KonasGlobals::Method1140).collect(Collectors.toList());
            for (File file : list) {
                Config.Method2252(file, new File(Config.CONFIGS, file.getName()));
            }
        }
        Config.Method2252(Config.oldConfigs, Config.CONFIG);
        Config.Method2252(Config.oldAccounts, Config.ACCOUNTS);
        Config.Method2252(new File(Minecraft.getMinecraft().mcDataDir, "Fonts"), CfontRenderer.Field814);
        if (new File(Minecraft.getMinecraft().mcDataDir, "Fonts").exists()) {
            Config.Method2252(new File(Minecraft.getMinecraft().mcDataDir, "Fonts"), CfontRenderer.Field814);
        } else {
            CfontRenderer.Field814.mkdir();
        }
    }

    public static boolean Method1140(final File file) {
        return file.getName().endsWith(".json") && !file.getName().equalsIgnoreCase("config.json") && !file.getName().equalsIgnoreCase("accounts.json");
    }
}