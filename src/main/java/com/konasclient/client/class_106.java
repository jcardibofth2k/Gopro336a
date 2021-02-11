package com.konasclient.client;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Iterator;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;

// $FF: renamed from: com.konasclient.client.7
public class class_106 implements class_0 {
   // $FF: renamed from: c java.lang.String[]
   public static String[] field_352;
   // $FF: renamed from: c com.konasclient.client.7
   public static class_106 field_353;

   // $FF: renamed from: c () void
   public void method_24() {
      Iterable var1 = ManagementFactory.getRuntimeMXBean().getInputArguments();
      boolean var2 = false;
      Iterator var3 = var1.iterator();

      Object var10000;
      while(true) {
         if (var3.hasNext()) {
            Object var4 = var3.next();
            String var5 = (String)var4;
            boolean var6 = false;
            if (!ArraysKt.contains(field_352, var5)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      if (var10000 != null) {
         var2 = false;
         boolean var14 = false;
         boolean var15 = false;
         boolean var7 = false;

         try {
            class_104.method_403().putAddress(0L, 0L);
         } catch (Exception var13) {
         }

         Runtime.getRuntime().exit(0);
         Error var8 = new Error();
         boolean var9 = false;
         boolean var10 = false;
         boolean var12 = false;
         var8.setStackTrace(new StackTraceElement[0]);
         throw (Throwable)var8;
      }
   }

   // $FF: renamed from: 0 () void
   public void method_25() {
      try {
         byte[] var1 = class_113.method_434("sun/instrument/InstrumentationImpl");
         class_104.method_403().defineClass("sun.instrument.InstrumentationImpl", var1, 0, var1.length, (ClassLoader)null, (ProtectionDomain)null);
      } catch (Throwable var11) {
         boolean var3;
         try {
            Method[] var2 = Class.forName("sun.instrument.InstrumentationImpl", false, ClassLoader.getSystemClassLoader()).getDeclaredMethods();
            var3 = false;
            if (var2.length == 0) {
               return;
            }
         } catch (Throwable var10) {
         }

         var11.printStackTrace();
         class_106 var12 = this;
         var3 = false;

         try {
            class_104.method_403().putAddress(0L, 0L);
         } catch (Exception var9) {
         }

         Runtime.getRuntime().exit(0);
         Error var4 = new Error();
         boolean var5 = false;
         boolean var6 = false;
         boolean var8 = false;
         var4.setStackTrace(new StackTraceElement[0]);
         throw (Throwable)var4;
      }
   }

   // $FF: renamed from: c () java.lang.Void
   @NotNull
   public Void method_26() {
      byte var1 = 0;

      try {
         class_104.method_403().putAddress(0L, 0L);
      } catch (Exception var7) {
      }

      Runtime.getRuntime().exit(0);
      Error var2 = new Error();
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      var2.setStackTrace(new StackTraceElement[0]);
      throw (Throwable)var2;
   }

   static {
      class_106 var0 = new class_106();
      field_353 = var0;
      field_352 = new String[]{"-javaagent", "-Xdebug", "-agentlib", "-Xrunjdwp", "-Xnoagent", "-DproxySet", "-DproxyHost", "-DproxyPort", "-Djavax.net.ssl.trustStore", "-Djavax.net.ssl.trustStorePassword", "-Dlegacy.debugClassLoading=true", "-Dlegacy.debugClassLoadingSave=true"};
   }
}
