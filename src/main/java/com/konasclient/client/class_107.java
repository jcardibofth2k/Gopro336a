package com.konasclient.client;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.text.StringsKt;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

// $FF: renamed from: com.konasclient.client.6
public class class_107 implements IMixinConfigPlugin {
   // $FF: renamed from: c java.io.File
   @NotNull
   public File field_354 = new File(System.getProperty("user.home") + "/Konas/mixin.refmap.json");
   // $FF: renamed from: c java.util.List
   @NotNull
   public List field_355;

   // $FF: renamed from: c () java.io.File
   @NotNull
   public File method_418() {
      return this.field_354;
   }

   // $FF: renamed from: c (java.io.File) void
   public void method_419(@NotNull File var1) {
      this.field_354 = var1;
   }

   // $FF: renamed from: c () java.util.List
   @NotNull
   public List method_420() {
      return this.field_355;
   }

   public void onLoad(@Nullable String var1) {
      Launch.classLoader.addURL(this.field_354.toURI().toURL());
      File var2 = this.field_354;
      boolean var3 = false;
      (new FileOutputStream(var2)).write(RotationManager.INSTANCE.getRefmap());
      this.field_354.deleteOnExit();
      Iterator var7 = RotationManager.INSTANCE.getMixinCache().iterator();

      while(var7.hasNext()) {
         String var6 = (String)var7.next();
         this.field_355.add(StringsKt.substringAfterLast$default(var6, ".", (String)null, 2, (Object)null));
         if (FMLLaunchHandler.isDeobfuscatedEnvironment()) {
            try {
               Launch.classLoader.loadClass(var6);
            } catch (ClassNotFoundException var5) {
               var5.printStackTrace();
            }
         }
      }

   }

   public void preApply(@Nullable String var1, @Nullable ClassNode var2, @Nullable String var3, @Nullable IMixinInfo var4) {
   }

   public void postApply(@Nullable String var1, @Nullable ClassNode var2, @Nullable String var3, @Nullable IMixinInfo var4) {
   }

   @NotNull
   public String getRefMapperConfig() {
      String var1 = StringsKt.replace$default(this.field_354.getAbsolutePath(), File.separatorChar, '/', false, 4, (Object)null);
      return (StringsKt.startsWith$default(var1, "/", false, 2, (Object)null) ? "file:" : "file:/") + var1;
   }

   @NotNull
   public List getMixins() {
      return this.field_355;
   }

   public void acceptTargets(@Nullable Set var1, @Nullable Set var2) {
   }

   public boolean shouldApplyMixin(@Nullable String var1, @Nullable String var2) {
      return true;
   }

   public class_107() {
      boolean var2 = false;
      List var3 = (List)(new ArrayList());
      this.field_355 = var3;
   }
}
