package com.konasclient.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

// $FF: renamed from: com.konasclient.client.a
public class class_105 extends Thread {
   // $FF: renamed from: c java.net.Socket
   public Socket field_346;
   // $FF: renamed from: c java.io.DataInputStream
   public DataInputStream field_347;
   // $FF: renamed from: c java.io.DataOutputStream
   public DataOutputStream field_348;
   // $FF: renamed from: c java.lang.String
   @NotNull
   public String field_349 = "";
   // $FF: renamed from: c com.konasclient.client.a
   @NotNull
   public static class_105 field_350 = new class_105();
   // $FF: renamed from: c com.konasclient.client.9
   public static class_108 field_351 = new class_108(null);

   // $FF: renamed from: c () java.lang.String
   @NotNull
   public String method_404() {
      return this.field_349;
   }

   // $FF: renamed from: c (java.lang.String) void
   public void method_405(@NotNull String var1) {
      this.field_349 = var1;
   }

   public void run() {
      try {
         this.field_346 = new Socket("auth.konasclient.com", 42067);
         DataInputStream var10001 = new DataInputStream;
         Socket var10003 = this.field_346;
         if (var10003 == null) {
            Intrinsics.throwNpe();
         }

         var10001.<init>(var10003.getInputStream());
         this.field_347 = var10001;
         DataOutputStream var20 = new DataOutputStream;
         var10003 = this.field_346;
         if (var10003 == null) {
            Intrinsics.throwNpe();
         }

         var20.<init>(var10003.getOutputStream());
         this.field_348 = var20;
         DataOutputStream var10000 = this.field_348;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         var10000.writeUTF(this.method_411("preLoaderConnect"));
         DataInputStream var19 = this.field_347;
         String var1 = var19 != null ? var19.readUTF() : null;
         var19 = this.field_347;
         String var2 = var19 != null ? var19.readUTF() : null;
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         this.field_349 = this.method_409(var2, var1);
         String var3 = URLDecoder.decode(a.class.getProtectionDomain().getCodeSource().getLocation().toURI().toString(), "UTF-8");
         String var4 = "!/" + StringsKt.replace$default(a.class.getName(), ".", "/", false, 4, (Object)null) + ".class";
         if (!StringsKt.contains$default((CharSequence)System.getProperty("os.name"), (CharSequence)"Windows", false, 2, (Object)null)) {
            var3 = '/' + var3;
         }

         var3 = StringsKt.replace$default(var3, var4, "", false, 4, (Object)null);
         var3 = StringsKt.replace$default(var3, "jar:file:/", "", false, 4, (Object)null);
         InputStream var5 = new FileInputStream(new File(var3));
         byte[] var6 = new byte[1024];
         MessageDigest var7 = MessageDigest.getInstance("MD5");
         boolean var8 = false;

         int var17;
         do {
            var17 = var5.read(var6);
            if (var17 > 0) {
               var7.update(var6, 0, var17);
            }
         } while(var17 != -1);

         var5.close();
         byte[] var9 = var7.digest();
         var10000 = this.field_348;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         boolean var12;
         boolean var13;
         boolean var15;
         String var21;
         label738: {
            var10000.writeUTF(Base64.getEncoder().encodeToString(this.method_410(var9, this.field_349)));
            var19 = this.field_347;
            if (var19 != null) {
               var21 = var19.readUTF();
               if (var21 != null) {
                  String var11 = var21;
                  var12 = false;
                  var13 = false;
                  var15 = false;
                  var21 = this.method_409(var11, this.field_349);
                  break label738;
               }
            }

            var21 = null;
         }

         String var10 = var21;
         if (Intrinsics.areEqual(var10, "integritycheckpassed") ^ true) {
            Socket var18 = this.field_346;
            var12 = false;
            var13 = false;
            var15 = false;
            if (var18 != null) {
               var18.close();
            }

            this.method_406().putAddress(0L, 0L);
         }
      } catch (Exception var16) {
         var16.printStackTrace();
      }

   }

   // $FF: renamed from: c () sun.misc.Unsafe
   public Unsafe method_406() {
      Field var1 = Unsafe.class.getDeclaredField("theUnsafe");
      var1.setAccessible(true);
      Object var10000 = var1.get(null);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type sun.misc.Unsafe");
      } else {
         return (Unsafe)var10000;
      }
   }

   // $FF: renamed from: c () void
   public void method_407() {
      DataOutputStream var10000 = this.field_348;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      var10000.writeUTF(this.method_409("downloadLoader", this.field_349));
      var10000 = this.field_348;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      var10000.flush();
   }

   // $FF: renamed from: c () byte[]
   @Nullable
   public byte[] method_408() {
      try {
         DataInputStream var10000 = this.field_347;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         int var1 = var10000.readInt();
         byte[] var2 = new byte[var1];
         int var3 = 0;

         for(int var4 = var1; var3 < var4; ++var3) {
            DataInputStream var10002 = this.field_347;
            if (var10002 == null) {
               Intrinsics.throwNpe();
            }

            var2[var3] = var10002.readByte();
         }

         Socket var6 = this.field_346;
         if (var6 != null) {
            var6.close();
         }

         return this.method_410(var2, this.field_349);
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }

   // $FF: renamed from: c (java.lang.String, java.lang.String) java.lang.String
   public String method_409(String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;

      for(int var5 = ((CharSequence)var1).length(); var4 < var5; ++var4) {
         var3.append((char)(var1.charAt(var4) ^ var2.charAt(var4 % var2.length())));
      }

      return var3.toString();
   }

   // $FF: renamed from: c (byte[], java.lang.String) byte[]
   public byte[] method_410(byte[] var1, String var2) {
      byte[] var3 = new byte[var1.length];
      int var4 = 0;

      for(int var5 = var1.length; var4 < var5; ++var4) {
         byte var6 = var1[var4];
         byte var7 = (byte)var2.charAt(var4 % var2.length());
         boolean var8 = false;
         byte var11 = (byte)(var6 ^ var7);
         var3[var4] = var11;
      }

      return var3;
   }

   // $FF: renamed from: c (java.lang.String) java.lang.String
   @NotNull
   public String method_411(@NotNull String var1) {
      Encoder var10000 = Base64.getEncoder();
      MessageDigest var6 = MessageDigest.getInstance("MD5");
      Encoder var5 = var10000;
      Charset var3 = Charsets.UTF_8;
      boolean var4 = false;
      byte[] var7 = var1.getBytes(var3);
      return var5.encodeToString(var6.digest(var7));
   }

   // $FF: renamed from: c () com.konasclient.client.a
   public static class_105 method_412() {
      return field_350;
   }

   // $FF: renamed from: c (com.konasclient.client.a) void
   public static void method_413(class_105 var0) {
      field_350 = var0;
   }
}
