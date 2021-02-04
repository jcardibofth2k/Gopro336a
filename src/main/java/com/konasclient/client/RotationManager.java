package com.konasclient.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RotationManager {
   @Nullable
   public static Map resourceCache;
   @NotNull
   public static List mixinCache;
   @Nullable
   public static byte[] refmap;
   public static RotationManager INSTANCE;

   /** @deprecated */
   @JvmStatic
   public static void resourceCache$annotations() {
   }

   @Nullable
   public static Map getResourceCache() {
      return resourceCache;
   }

   public static void setResourceCache(@Nullable Map var0) {
      resourceCache = var0;
   }

   @NotNull
   public List getMixinCache() {
      return mixinCache;
   }

   public void setMixinCache(@NotNull List var1) {
      mixinCache = var1;
   }

   @Nullable
   public byte[] getRefmap() {
      return refmap;
   }

   public void setRefmap(@Nullable byte[] var1) {
      refmap = var1;
   }

   public void load() {
      // $FF: Couldn't be decompiled
   }

   static {
      RotationManager var0 = new RotationManager();
      INSTANCE = var0;
      boolean var1 = false;
      mixinCache = new ArrayList();
   }
}
