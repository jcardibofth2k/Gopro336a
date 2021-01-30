package com.konasclient.client;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

// $FF: renamed from: com.konasclient.client.h
public class class_104 {
   // $FF: renamed from: c kotlin.Lazy
   @NotNull
   public static Lazy field_345;

   // $FF: renamed from: c () sun.misc.Unsafe
   @NotNull
   public static Unsafe method_403() {
      Lazy var0 = field_345;
      Object var1 = null;
      Object var2 = null;
      boolean var3 = false;
      return (Unsafe)var0.getValue();
   }

   static {
      field_345 = LazyKt.lazy((Function0)class_103.field_344);
   }
}
