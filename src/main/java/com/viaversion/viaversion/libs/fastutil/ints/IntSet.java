package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Set;

public interface IntSet extends IntCollection, Set {
   IntIterator iterator();

   boolean remove(int var1);

   /** @deprecated */
   @Deprecated
   default boolean remove(Object o) {
      return IntCollection.super.remove(o);
   }

   /** @deprecated */
   @Deprecated
   default boolean add(Integer o) {
      return IntCollection.super.add(o);
   }

   /** @deprecated */
   @Deprecated
   default boolean contains(Object o) {
      return IntCollection.super.contains(o);
   }

   /** @deprecated */
   @Deprecated
   default boolean rem(int k) {
      return this.remove(k);
   }
}
