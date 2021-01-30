package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.1;
import java.util.function.Consumer;

public final class Object2IntMaps {
   public static final com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.EmptyMap EMPTY_MAP = new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.EmptyMap();

   private Object2IntMaps() {
   }

   public static ObjectIterator fastIterator(Object2IntMap map) {
      ObjectSet entries = map.object2IntEntrySet();
      return entries instanceof Object2IntMap.FastEntrySet ? ((Object2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
   }

   public static void fastForEach(Object2IntMap map, Consumer consumer) {
      ObjectSet entries = map.object2IntEntrySet();
      if (entries instanceof Object2IntMap.FastEntrySet) {
         ((Object2IntMap.FastEntrySet)entries).fastForEach(consumer);
      } else {
         entries.forEach(consumer);
      }

   }

   public static ObjectIterable fastIterable(Object2IntMap map) {
      ObjectSet entries = map.object2IntEntrySet();
      return (ObjectIterable)(entries instanceof Object2IntMap.FastEntrySet ? new 1(entries) : entries);
   }

   public static Object2IntMap emptyMap() {
      return EMPTY_MAP;
   }

   public static Object2IntMap singleton(Object key, int value) {
      return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.Singleton(key, value);
   }

   public static Object2IntMap singleton(Object key, Integer value) {
      return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.Singleton(key, value);
   }

   public static Object2IntMap synchronize(Object2IntMap m) {
      return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.SynchronizedMap(m);
   }

   public static Object2IntMap synchronize(Object2IntMap m, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.SynchronizedMap(m, sync);
   }

   public static Object2IntMap unmodifiable(Object2IntMap m) {
      return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps.UnmodifiableMap(m);
   }
}
