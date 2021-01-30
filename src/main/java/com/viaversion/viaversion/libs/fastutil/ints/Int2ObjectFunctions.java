package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Int2ObjectFunctions {
   public static final Int2ObjectFunctions.EmptyFunction EMPTY_FUNCTION = new Int2ObjectFunctions.EmptyFunction();

   private Int2ObjectFunctions() {
   }

   public static Int2ObjectFunction singleton(int key, Object value) {
      return new Int2ObjectFunctions.Singleton(key, value);
   }

   public static Int2ObjectFunction singleton(Integer key, Object value) {
      return new Int2ObjectFunctions.Singleton(key, value);
   }

   public static Int2ObjectFunction synchronize(Int2ObjectFunction f) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction(f);
   }

   public static Int2ObjectFunction synchronize(Int2ObjectFunction f, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction(f, sync);
   }

   public static Int2ObjectFunction unmodifiable(Int2ObjectFunction f) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.UnmodifiableFunction(f);
   }

   public static Int2ObjectFunction primitive(Function f) {
      Objects.requireNonNull(f);
      if (f instanceof Int2ObjectFunction) {
         return (Int2ObjectFunction)f;
      } else if (f instanceof IntFunction) {
         IntFunction var10000 = (IntFunction)f;
         Objects.requireNonNull((IntFunction)f);
         return var10000::apply;
      } else {
         return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.PrimitiveFunction(f);
      }
   }

   public static class Singleton extends AbstractInt2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int key;
      protected final Object value;

      protected Singleton(int key, Object value) {
         this.key = key;
         this.value = value;
      }

      public boolean containsKey(int k) {
         return this.key == k;
      }

      public Object get(int k) {
         return this.key == k ? this.value : this.defRetValue;
      }

      public int size() {
         return 1;
      }

      public Object clone() {
         return this;
      }
   }

   public static class EmptyFunction extends AbstractInt2ObjectFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      public Object get(int k) {
         return null;
      }

      public boolean containsKey(int k) {
         return false;
      }

      public Object defaultReturnValue() {
         return null;
      }

      public void defaultReturnValue(Object defRetValue) {
         throw new UnsupportedOperationException();
      }

      public int size() {
         return 0;
      }

      public void clear() {
      }

      public Object clone() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         if (!(o instanceof com.viaversion.viaversion.libs.fastutil.Function)) {
            return false;
         } else {
            return ((com.viaversion.viaversion.libs.fastutil.Function)o).size() == 0;
         }
      }

      public String toString() {
         return "{}";
      }

      private Object readResolve() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }
   }
}
