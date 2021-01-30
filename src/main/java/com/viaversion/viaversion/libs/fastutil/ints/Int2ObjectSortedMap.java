package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSortedSet;
import java.util.SortedMap;

public interface Int2ObjectSortedMap extends Int2ObjectMap, SortedMap {
   Int2ObjectSortedMap subMap(int var1, int var2);

   Int2ObjectSortedMap headMap(int var1);

   Int2ObjectSortedMap tailMap(int var1);

   int firstIntKey();

   int lastIntKey();

   /** @deprecated */
   @Deprecated
   default Int2ObjectSortedMap subMap(Integer from, Integer to) {
      return this.subMap(from, to);
   }

   /** @deprecated */
   @Deprecated
   default Int2ObjectSortedMap headMap(Integer to) {
      return this.headMap(to);
   }

   /** @deprecated */
   @Deprecated
   default Int2ObjectSortedMap tailMap(Integer from) {
      return this.tailMap(from);
   }

   /** @deprecated */
   @Deprecated
   default Integer firstKey() {
      return this.firstIntKey();
   }

   /** @deprecated */
   @Deprecated
   default Integer lastKey() {
      return this.lastIntKey();
   }

   /** @deprecated */
   @Deprecated
   default ObjectSortedSet entrySet() {
      return this.int2ObjectEntrySet();
   }

   ObjectSortedSet int2ObjectEntrySet();

   IntSortedSet keySet();

   ObjectCollection values();

   IntComparator comparator();

   public interface FastSortedEntrySet extends ObjectSortedSet, Int2ObjectMap.FastEntrySet {
      ObjectBidirectionalIterator fastIterator();

      ObjectBidirectionalIterator fastIterator(Int2ObjectMap.Entry var1);
   }
}
