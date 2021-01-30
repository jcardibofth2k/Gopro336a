package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.util.function.IntUnaryOperator;

@FunctionalInterface
public interface Int2IntFunction extends Function, IntUnaryOperator {
   default int applyAsInt(int operand) {
      return this.get(operand);
   }

   default int put(int key, int value) {
      throw new UnsupportedOperationException();
   }

   int get(int var1);

   default int remove(int key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default Integer put(Integer key, Integer value) {
      int k = key;
      boolean containsKey = this.containsKey(k);
      int v = this.put(k, value);
      return containsKey ? v : null;
   }

   /** @deprecated */
   @Deprecated
   default Integer get(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         int v = this.get(k);
         return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
      }
   }

   /** @deprecated */
   @Deprecated
   default Integer remove(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         return this.containsKey(k) ? this.remove(k) : null;
      }
   }

   default boolean containsKey(int key) {
      return true;
   }

   /** @deprecated */
   @Deprecated
   default boolean containsKey(Object key) {
      return key == null ? false : this.containsKey((Integer)key);
   }

   default void defaultReturnValue(int rv) {
      throw new UnsupportedOperationException();
   }

   default int defaultReturnValue() {
      return 0;
   }
}
