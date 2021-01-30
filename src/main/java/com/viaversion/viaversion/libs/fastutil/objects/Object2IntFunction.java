package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Object2IntFunction extends Function, ToIntFunction {
   default int applyAsInt(Object operand) {
      return this.getInt(operand);
   }

   default int put(Object key, int value) {
      throw new UnsupportedOperationException();
   }

   int getInt(Object var1);

   default int removeInt(Object key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default Integer put(Object key, Integer value) {
      boolean containsKey = this.containsKey(key);
      int v = this.put(key, value);
      return containsKey ? v : null;
   }

   /** @deprecated */
   @Deprecated
   default Integer get(Object key) {
      int v = this.getInt(key);
      return v == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
   }

   /** @deprecated */
   @Deprecated
   default Integer remove(Object key) {
      return this.containsKey(key) ? this.removeInt(key) : null;
   }

   default void defaultReturnValue(int rv) {
      throw new UnsupportedOperationException();
   }

   default int defaultReturnValue() {
      return 0;
   }
}
