package me.darki.konas;

import me.darki.konas.module.client.NewGui;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid="konas", name="Konas", version="0.10.2")
public class KonasMod {
    public static String Field729;
    public static String Field730;
    public static String Field731;
    public static String Field732;
    public static Logger Field733;

    @Mod.EventHandler
    public void Method787(FMLPreInitializationEvent fMLPreInitializationEvent) {
        NewGui.INSTANCE.Method1139();
    }

    static {
        Field731 = "0.10.2";
        Field730 = "Konas";
        Field729 = "konas";
        Field732 = "Konas";
        Field733 = LogManager.getLogger((String)"Konas");
    }

    @Mod.EventHandler
    public void Method788(FMLInitializationEvent fMLInitializationEvent) {
        NewGui.INSTANCE.Method1138();
    }
}