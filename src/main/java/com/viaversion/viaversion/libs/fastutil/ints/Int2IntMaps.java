package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.1;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.function.Consumer;

public final class Int2IntMaps {
   public static final com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.EmptyMap EMPTY_MAP = new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.EmptyMap();

   private Int2IntMaps() {
   }

   public static ObjectIterator fastIterator(Int2IntMap map) {
      ObjectSet entries = map.int2IntEntrySet();
      return entries instanceof Int2IntMap.FastEntrySet ? ((Int2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Int2IntMap map, Consumer consumer) {
      ObjectSet entries = map.int2IntEntrySet();
      if (entries instanceof Int2IntMap.FastEntrySet) {
         ((Int2IntMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Int2IntMap map) {
      ObjectSet entries = map.int2IntEntrySet();
      return (ObjectIterable)(entries instanceof Int2IntMap.FastEntrySet ? new 1(entries) : entries)
   }

   public static Int2IntMap singleton(int key, int value) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.Singleton(key, value);
   }

   public static Int2IntMap singleton(Integer key, Integer value) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.Singleton(key, value);
   }

   public static Int2IntMap synchronize(Int2IntMap m) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.SynchronizedMap(m);
   }

   public static Int2IntMap synchronize(Int2IntMap m, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.SynchronizedMap(m, sync);
   }

   public static Int2IntMap unmodifiable(Int2IntMap m) {
      return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntMaps.UnmodifiableMap(m);
   }
}
