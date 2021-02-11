package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface Int2ObjectMap extends Int2ObjectFunction, Map {
   int size();

   default void clear() {
      throw new UnsupportedOperationException();
   }

   void defaultReturnValue(Object var1);

   Object defaultReturnValue();

   ObjectSet int2ObjectEntrySet();

   /** @deprecated */
   @Deprecated
   default ObjectSet entrySet() {
      return this.int2ObjectEntrySet();
   }

   /** @deprecated */
   @Deprecated
   default Object put(Integer key, Object value) {
      return Int2ObjectFunction.super.put(key, value);
   }

   /** @deprecated */
   @Deprecated
   default Object get(Object key) {
      return Int2ObjectFunction.super.get(key);
   }

   /** @deprecated */
   @Deprecated
   default Object remove(Object key) {
      return Int2ObjectFunction.super.remove(key);
   }

   IntSet keySet();

   ObjectCollection values();

   boolean containsKey(int var1);

   /** @deprecated */
   @Deprecated
   default boolean containsKey(Object key) {
      return Int2ObjectFunction.super.containsKey(key);
   }

   default Object getOrDefault(int key, Object defaultValue) {
      Object v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default Object putIfAbsent(int key, Object value) {
      Object v = this.get(key);
      Object drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(int key, Object value) {
      Object curValue = this.get(key);
      if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(int key, Object oldValue, Object newValue) {
      Object curValue = this.get(key);
      if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default Object replace(int key, Object value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default Object computeIfAbsent(int key, IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      Object v = this.get(key);
      if (v == this.defaultReturnValue() && !this.containsKey(key)) {
         Object newValue = mappingFunction.apply(key);
         this.put(key, newValue);
         return newValue;
      } else {
         return v;
      }
   }

   default Object computeIfAbsentPartial(int key, Int2ObjectFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      Object v = this.get(key);
      Object drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         if (!mappingFunction.containsKey(key)) {
            return drv;
         } else {
            Object newValue = mappingFunction.get(key);
            this.put(key, newValue);
            return newValue;
         }
      } else {
         return v;
      }
   }

   default Object computeIfPresent(int key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Object oldValue = this.get(key);
      Object drv = this.defaultReturnValue();
      if (oldValue == drv && !this.containsKey(key)) {
         return drv;
      } else {
         Object newValue = remappingFunction.apply(key, oldValue);
         if (newValue == null) {
            this.remove(key);
            return drv;
         } else {
            this.put(key, newValue);
            return newValue;
         }
      }
   }

   default Object compute(int key, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Object oldValue = this.get(key);
      Object drv = this.defaultReturnValue();
      boolean contained = oldValue != drv || this.containsKey(key);
      Object newValue = remappingFunction.apply(key, contained ? oldValue : null);
      if (newValue == null) {
         if (contained) {
            this.remove(key);
         }

         return drv;
      } else {
         this.put(key, newValue);
         return newValue;
      }
   }

   default Object merge(int key, Object value, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Objects.requireNonNull(value);
      Object oldValue = this.get(key);
      Object drv = this.defaultReturnValue();
      Object newValue;
      if (oldValue == drv && !this.containsKey(key)) {
         newValue = value;
      } else {
         Object mergedValue = remappingFunction.apply(oldValue, value);
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
   default Object getOrDefault(Object key, Object defaultValue) {
      return super.getOrDefault(key, defaultValue);
   }

   /** @deprecated */
   @Deprecated
   default Object putIfAbsent(Integer key, Object value) {
      return super.putIfAbsent(key, value);
   }

   /** @deprecated */
   @Deprecated
   default boolean remove(Object key, Object value) {
      return super.remove(key, value);
   }

   /** @deprecated */
   @Deprecated
   default boolean replace(Integer key, Object oldValue, Object newValue) {
      return super.replace(key, oldValue, newValue);
   }

   /** @deprecated */
   @Deprecated
   default Object replace(Integer key, Object value) {
      return super.replace(key, value);
   }

   /** @deprecated */
   @Deprecated
   default Object computeIfAbsent(Integer key, Function mappingFunction) {
      return super.computeIfAbsent(key, mappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Object computeIfPresent(Integer key, BiFunction remappingFunction) {
      return super.computeIfPresent(key, remappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Object compute(Integer key, BiFunction remappingFunction) {
      return super.compute(key, remappingFunction);
   }

   /** @deprecated */
   @Deprecated
   default Object merge(Integer key, Object value, BiFunction remappingFunction) {
      return super.merge(key, value, remappingFunction);
   }

   interface Entry extends java.util.Map.Entry {
      int getIntKey();

      /** @deprecated */
      @Deprecated
      default Integer getKey() {
         return this.getIntKey();
      }
   }

   interface FastEntrySet extends ObjectSet {
      ObjectIterator fastIterator();

      default void fastForEach(Consumer consumer) {
         this.forEach(consumer);
      }
   }
}
