package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Int2ObjectFunction extends Function, IntFunction {
   default Object apply(int operand) {
      return this.get(operand);
   }

   default Object put(int key, Object value) {
      throw new UnsupportedOperationException();
   }

   Object get(int var1);

   default Object remove(int key) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   default Object put(Integer key, Object value) {
      int k = key;
      boolean containsKey = this.containsKey(k);
      Object v = this.put(k, value);
      return containsKey ? v : null;
   }

   /** @deprecated */
   @Deprecated
   default Object get(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         Object v = this.get(k);
         return v == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
      }
   }

   /** @deprecated */
   @Deprecated
   default Object remove(Object key) {
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
      return key != null && this.containsKey(key);
   }

   default void defaultReturnValue(Object rv) {
      throw new UnsupportedOperationException();
   }

   default Object defaultReturnValue() {
      return null;
   }
}
