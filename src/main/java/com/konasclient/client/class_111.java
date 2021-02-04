package com.konasclient.client;

import com.konasclient.loader.LoaderMod;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.NotNull;

// $FF: renamed from: com.konasclient.client.3
@Mod(
   modid = "konas",
   name = "Konas",
   version = "0.10"
)
public class class_111 {
   // $FF: renamed from: c kotlin.Lazy
   public Lazy field_356;
   // $FF: renamed from: c java.lang.String
   @NotNull
   public static String field_357 = "konas";
   // $FF: renamed from: 0 java.lang.String
   @NotNull
   public static String field_358 = "Konas";
   // $FF: renamed from: 1 java.lang.String
   @NotNull
   public static String field_359 = "0.10";
   // $FF: renamed from: c com.konasclient.client.1
   public static class_109 field_360 = new class_109(null);

   // $FF: renamed from: c () com.konasclient.loader.LoaderMod
   public LoaderMod method_428() {
      Lazy var1 = this.field_356;
      Object var3 = null;
      boolean var4 = false;
      return (LoaderMod)var1.getValue();
   }

   // $FF: renamed from: c (net.minecraftforge.fml.common.event.FMLPreInitializationEvent) void
   @EventHandler
   public void method_429(@NotNull FMLPreInitializationEvent var1) {
      this.method_428().preinit(var1);
   }

   // $FF: renamed from: c (net.minecraftforge.fml.common.event.FMLInitializationEvent) void
   @EventHandler
   public void method_430(@NotNull FMLInitializationEvent var1) {
      this.method_428().init(var1);
   }

   // $FF: renamed from: c (net.minecraftforge.fml.common.event.FMLPostInitializationEvent) void
   @EventHandler
   public void method_431(@NotNull FMLPostInitializationEvent var1) {
      this.method_428().postinit(var1);
   }

   public class_111() {
      this.field_356 = LazyKt.lazy((Function0)class_112.field_361);
   }
}
