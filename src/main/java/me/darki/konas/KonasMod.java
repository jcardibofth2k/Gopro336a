package me.darki.konas;

import me.darki.konas.module.client.KonasGlobals;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(modid="konas", name="Konas", version="0.10.2")
public class KonasMod {
    public static String ID = "konas";
    public static String NAME = "Konas";
    public static String VERSION = "0.10.2";

    public static Logger LOGGER = LogManager.getLogger((String)"Konas");

    @Mod.EventHandler
    public void Method787(FMLPreInitializationEvent fMLPreInitializationEvent) {
        KonasGlobals.INSTANCE.preInit();
    }


    @Mod.EventHandler
    public void Method788(FMLInitializationEvent fMLInitializationEvent) {
        KonasGlobals.INSTANCE.init();
    }
}