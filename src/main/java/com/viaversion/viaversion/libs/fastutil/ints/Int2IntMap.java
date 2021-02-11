package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public interface Int2IntMap extends Int2IntFunction, Map {
   int size();

   default void clear() {
      throw new UnsupportedOperationException();
   }

   void defaultReturnValue(int var1);

   int defaultReturnValue();

   ObjectSet int2IntEntrySet();

   /** @deprecated */
   @Deprecated
   default ObjectSet entrySet() {
      return this.int2IntEntrySet();
   }

   /** @deprecated */
   @Deprecated
   default Integer put(Integer key, Integer value) {
      return Int2IntFunction.super.put(key, value);
   }

   /** @deprecated */
   @Deprecated
   default Integer get(Object key) {
      return Int2IntFunction.super.get(key);
   }

   /** @deprecated */
   @Deprecated
   default Integer remove(Object key) {
      return Int2IntFunction.super.remove(key);
   }

   IntSet keySet();

   IntCollection values();

   boolean containsKey(int var1);

   /** @deprecated */
   @Deprecated
   default boolean containsKey(Object key) {
      return Int2IntFunction.super.containsKey(key);
   }

   boolean containsValue(int var1);

   /** @deprecated */
   @Deprecated
   default boolean containsValue(Object value) {
      return value != null && this.containsValue(value);
   }

   default int getOrDefault(int key, int defaultValue) {
      int v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default int putIfAbsent(int key, int value) {
      int v = this.get(key);
      int drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(int key, int value) {
      int curValue = this.get(key);
      if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(int key, int oldValue, int newValue) {
      int curValue = this.get(key);
      if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default int replace(int key, int value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default int computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int v = this.get(key);
      if (v == this.defaultReturnValue() && !this.containsKey(key)) {
         int newValue = mappingFunction.applyAsInt(key);
         this.put(key, newValue);
         return newValue;
      } else {
         return v;
      }
   }

   default int computeIfAbsentNullable(int key, IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int v = this.get(key);
      int drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         Integer mappedValue = (Integer)mappingFunction.apply(key);
         if (mappedValue == null) {
            return drv;
         } else {
            int newValue = mappedValue;
            this.put(key, newValue);
            return newValue;
         }
      } else {
         return v;
      }
   }

   default int computeIfAbsentPartial(int key, Int2IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int v = this.get(key);
      int drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         if (!mappingFunction.containsKey(key)) {
            return drv;
         } else {
            int newValue = mappingFunction.get(key);
            this.put(key, newValue);
            return newValue;
         }
      } else {
         return v;
      }
   }

   default int computeIfPresent(int key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.get(key);
      int drv = this.defaultReturnValue();
      if (oldValue == drv && !this.containsKey(key)) {
         return drv;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(key, oldValue);
         if (newValue == null) {
            this.remove(key);
            return drv;
         } else {
            int newVal = newValue;
            this.put(key, newVal);
            return newVal;
         }
      }
   }

   default int compute(int key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.get(key);
      int drv = this.defaultReturnValue();
      boolean contained = oldValue != drv || this.containsKey(key);
      Integer newValue = (Integer)remappingFunction.apply(key, contained ? oldValue : null);
      if (newValue == null) {
         if (contained) {
            this.remove(key);
         }

         return drv;
      } else {
         int newVal = newValue;
         this.put(key, newVal);
         return newVal;
      }
   }

   default int merge(int key, int value, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.get(key);
      int drv = this.defaultReturnValue();
      int newValue;
      if (oldValue == drv && !this.containsKey(key)) {
         newValue = value;
      } else {
         Integer mergedValue = (Integer)remappingFunction.apply(oldValue, value);
         if (mergedValue == null) {
            this.remove(key);
            return drv;
         }

         newValue = mergedValue;
      }

      this.put(key, newValue);
      return newValue;
   }

   /** @deprecated */
   @Deprecated
   default Integer getOrDefault(Object key, Integer defaultValue) {
      return (Integer)super.getOrDefault(key, defaultValue);
   }

   /** @deprecated */
   @Deprecated
   default Integer putIfAbsent(Integer key, Integer value) {
      return (Integer)super.putIfAbsent(key, value);
   }

   /** @deprecated */
   @Deprecated
   default boolean remove(Object key, Object value) {
      return super.remove(key, value);
   }

   /** @deprecated */
   @Deprecated
   default boolean replace(Integer key, Integer oldValue, Integer newValue) {
      return super.replace(key, oldValue, newValue);
   }

   /** @deprecated */
   @Deprecated
   default Integer replace(Integer key, Integer value) {
      return (Integer)super.replace(key, value);
   }

   /** @deprecated */
   @Deprecated
   default Integer computeIfAbsent(Integer key, Function mappingFunction) {
      return (Integer)super.computeIfAbsent(key, mappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Integer computeIfPresent(Integer key, BiFunction remappingFunction) {
      return (Integer)super.computeIfPresent(key, remappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Integer compute(Integer key, BiFunction remappingFunction) {
      return (Integer)super.compute(key, remappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Integer merge(Integer key, Integer value, BiFunction remappingFunction) {
      return (Integer)super.merge(key, value, remappingFunction);
   }

   interface Entry extends java.util.Map.Entry {
      int getIntKey();

      /** @deprecated */
      @Deprecated
      default Integer getKey() {
         return this.getIntKey();
      }

      int getIntValue();

      int setValue(int var1);

      /** @deprecated */
      @Deprecated
      default Integer getValue() {
         return this.getIntValue();
      }

      /** @deprecated */
      @Deprecated
      default Integer setValue(Integer value) {
         return this.setValue(value);
      }
   }

   interface FastEntrySet extends ObjectSet {
      ObjectIterator fastIterator();

      default void fastForEach(Consumer consumer) {
         this.forEach(consumer);
      }
   }
}
