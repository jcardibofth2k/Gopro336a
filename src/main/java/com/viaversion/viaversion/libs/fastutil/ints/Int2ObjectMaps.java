package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Consumer;

public final class Int2ObjectMaps {
   public static final Int2ObjectMaps.EmptyMap EMPTY_MAP = new Int2ObjectMaps.EmptyMap();

   private Int2ObjectMaps() {
   }

   public static ObjectIterator fastIterator(Int2ObjectMap map) {
      ObjectSet entries = map.int2ObjectEntrySet();
      return entries instanceof Int2ObjectMap.FastEntrySet ? ((Int2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Int2ObjectMap map, Consumer consumer) {
      ObjectSet entries = map.int2ObjectEntrySet();
      if (entries instanceof Int2ObjectMap.FastEntrySet) {
         ((Int2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Int2ObjectMap map) {
      final ObjectSet entries = map.int2ObjectEntrySet();
      return entries instanceof Int2ObjectMap.FastEntrySet ? new ObjectIterable() {
         public ObjectIterator iterator() {
            return ((Int2ObjectMap.FastEntrySet)entries).fastIterator();
         }

         public void forEach(Consumer consumer) {
            ((Int2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
         }
      } : entries;
   }

   public static Int2ObjectMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Int2ObjectMap singleton(int key, Object value) {
      return new Int2ObjectMaps.Singleton(key, value);
   }

   public static Int2ObjectMap singleton(Integer key, Object value) {
      return new Int2ObjectMaps.Singleton(key, value);
   }

   public static Int2ObjectMap synchronize(Int2ObjectMap m) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.SynchronizedMap(m);
   }

   public static Int2ObjectMap synchronize(Int2ObjectMap m, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.SynchronizedMap(m, sync);
   }

   public static Int2ObjectMap unmodifiable(Int2ObjectMap m) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps.UnmodifiableMap(m);
   }

   public static class EmptyMap extends Int2ObjectFunctions.EmptyFunction implements Int2ObjectMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyMap() {
      }

      public boolean containsValue(Object v) {
         return false;
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet int2ObjectEntrySet() {
         return ObjectSets.EMPTY_SET;
      }

      public IntSet keySet() {
         return IntSets.EMPTY_SET;
      }

      public ObjectCollection values() {
         return ObjectSets.EMPTY_SET;
      }

      public Object clone() {
         return Int2ObjectMaps.EMPTY_MAP;
      }

      public boolean isEmpty() {
         return true;
      }

      public int hashCode() {
         return 0;
      }

      public boolean equals(Object o) {
         return o instanceof Map && ((Map) o).isEmpty();
      }

      public String toString() {
         return "{}";
      }
   }

   public static class Singleton extends Int2ObjectFunctions.Singleton implements Int2ObjectMap, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected transient ObjectSet entries;
      protected transient IntSet keys;
      protected transient ObjectCollection values;

      protected Singleton(int key, Object value) {
         super(key, value);
      }

      public boolean containsValue(Object v) {
         return Objects.equals(this.value, v);
      }

      public void putAll(Map m) {
         throw new UnsupportedOperationException();
      }

      public ObjectSet int2ObjectEntrySet() {
         if (this.entries == null) {
            this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value));
         }

         return this.entries;
      }

      /** @deprecated */
      @Deprecated
      public ObjectSet entrySet() {
         return this.int2ObjectEntrySet();
      }

      public IntSet keySet() {
         if (this.keys == null) {
            this.keys = IntSets.singleton(this.key);
         }

         return this.keys;
      }

      public ObjectCollection values() {
         if (this.values == null) {
            this.values = ObjectSets.singleton(this.value);
         }

         return this.values;
      }

      public boolean isEmpty() {
         return false;
      }

      public int hashCode() {
         return this.key ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Map)) {
            return false;
         } else {
            Map m = (Map)o;
            return m.size() == 1 && m.entrySet().iterator().next().equals(this.entrySet().iterator().next());
         }
      }

      public String toString() {
         return "{" + this.key + "=>" + this.value + "}";
      }
   }
}
