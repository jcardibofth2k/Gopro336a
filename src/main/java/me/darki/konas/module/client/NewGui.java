package me.darki.konas.module.client;

import com.konasclient.loader.Loader;
import cookiedragon.eventsystem.EventDispatcher;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.config.Config;
import me.darki.konas.event.EventProcessor;
import me.darki.konas.managers.CommandManager;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.CommandUtil;
import me.darki.konas.util.ConfigUtil;
import me.darki.konas.util.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import sun.misc.Unsafe;

public class NewGui {
    public static NewGui INSTANCE = new NewGui();
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
    public Class573 Field1140;

    /*
     * Unable to fully structure code
     */
    public void Method1138() {
        block52: {
            block51: {
                ModuleManager.Method1620();
                CommandManager.Method206();
                this.Field1136 = new Class109();
                this.Field1136.Method2200();
                v0 = Unsafe.class;
                v1 = "theUnsafe";
                v2 = v0.getDeclaredField(v1);
                v3 = var1_1 = v2;
                v4 = true;
                v3.setAccessible(v4);
                v5 = var1_1;
                v6 = null;
                v7 = v5.get(v6);
                v8 = (Unsafe)v7;
                v9 = Loader.INSTANCE;
                v10 = v9.getAddress();
                v11 = v8.getByte(v10);
                var2_4 = v11;
                if (var2_4 != 111) break block51;
                v12 = MinecraftForge.EVENT_BUS;
                v13 = EventProcessor.INSTANCE;
                v12.register((Object)v13);
                v14 = EventDispatcher.Companion;
                v15 = Class54.Field200;
                v14.register(v15);
                v16 = EventDispatcher.Companion;
                v17 = CommandUtil.Field163;
                v16.register(v17);
                v18 = EventDispatcher.Companion;
                v19 = Class54.Field200;
                v18.subscribe(v19);
                v20 = EventDispatcher.Companion;
                v21 = CommandUtil.Field163;
                v20.subscribe(v21);
                v22 = EventDispatcher.Companion;
                v23 = Class456.Field486;
                v22.register(v23);
                v24 = EventDispatcher.Companion;
                v25 = Class456.Field486;
                v24.subscribe(v25);
                v26 = EventDispatcher.Companion;
                v27 = Class51.Field221;
                v26.register(v27);
                v28 = EventDispatcher.Companion;
                v29 = Class51.Field221;
                v28.subscribe(v29);
                v30 = EventDispatcher.Companion;
                v31 = Class81.Field274;
                v30.register(v31);
                v32 = EventDispatcher.Companion;
                v33 = Class81.Field274;
                v32.subscribe(v33);
                v34 = EventDispatcher.Companion;
                v35 = ConfigUtil.Field532;
                v34.register(v35);
                v36 = EventDispatcher.Companion;
                v37 = ConfigUtil.Field532;
                try {
                    v36.subscribe(v37);
                }
                catch (Exception var1_2) {
                    // empty catch block
                }
            }
            this.Field1130 = new Class193();
            this.Field1130.Method121();
            this.Field1131 = new Class147();
            this.Field1131.Method613();
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
            this.Field1132 = new Class166(Minecraft.getMinecraft().currentScreen);
            this.Field1132.Method121();
            Config.Method2274(Config.Method2223(), true);
            Runtime.getRuntime().addShutdownHook(new Class588());
            v38 = "os.arch";
            v39 = System.getProperty(v38);
            v40 = "aarch64";
            v41 = v39.equals(v40);
            if (v41) ** GOTO lbl142
            v42 = this;
            v43 = v44;
            v45 = v44;
            v43();
            try {
                v42.Field1140 = v45;
                break block52;
lbl142:
                // 1 sources

                this.Field1140 = null;
            }
            catch (Exception var1_3) {
                this.Field1140 = null;
            }
        }
    }

    public void Method1139() {
        ModuleManager.Field1692.start();
        if (!Config.KONAS_FOLDER.exists()) {
            Config.KONAS_FOLDER.mkdir();
        }
        if (!Config.CONFIGS.exists()) {
            Config.CONFIGS.mkdir();
        }
        if (Config.KONAS_FOLDER.listFiles() != null) {
            List list = Arrays.stream(Config.KONAS_FOLDER.listFiles()).filter(NewGui::Method1140).collect(Collectors.toList());
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

    public static boolean Method1140(File file) {
        return file.getName().endsWith(".json") && !file.getName().equalsIgnoreCase("config.json") && !file.getName().equalsIgnoreCase("accounts.json");
    }
}