package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface Int2IntBiMap extends Int2IntMap {
   Int2IntBiMap inverse();

   int put(int var1, int var2);

   /** @deprecated */
   @Deprecated
   default void putAll(@NonNull Map m) {
      throw new UnsupportedOperationException();
   }
}
