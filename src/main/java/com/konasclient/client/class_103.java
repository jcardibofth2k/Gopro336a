package com.konasclient.client;

import java.lang.reflect.Field;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

// $FF: renamed from: com.konasclient.client.g
public class class_103 extends Lambda implements Function0 {
   // $FF: renamed from: c com.konasclient.client.g
   public static class_103 field_344 = new class_103();

   public Object invoke() {
      return this.method_402();
   }

   // $FF: renamed from: c () sun.misc.Unsafe
   @NotNull
   public Unsafe method_402() {
      Field var1 = Unsafe.class.getDeclaredField("theUnsafe");
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      var1.setAccessible(true);
      Object var10000 = var1.get(null);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type sun.misc.Unsafe");
      } else {
         return (Unsafe)var10000;
      }
   }

   public class_103() {
      super(0);
   }
}
