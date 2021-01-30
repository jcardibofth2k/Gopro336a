package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class ShadyPines {
   private ShadyPines() {
   }

   /** @deprecated */
   @Deprecated
   @SafeVarargs
   @NotNull
   public static Set enumSet(final Class type, @NotNull final Enum... constants) {
      return MonkeyBars.enumSet(type, constants);
   }

   public static boolean equals(final double a, final double b) {
      return Double.doubleToLongBits(a) == Double.doubleToLongBits(b);
   }

   public static boolean equals(final float a, final float b) {
      return Float.floatToIntBits(a) == Float.floatToIntBits(b);
   }
}
