package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Collection;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface IntCollection extends Collection, IntIterable {
   IntIterator iterator();

   boolean add(int var1);

   boolean contains(int var1);

   boolean rem(int var1);

   /** @deprecated */
   @Deprecated
   default boolean add(Integer key) {
      return this.add(key);
   }

   /** @deprecated */
   @Deprecated
   default boolean contains(Object key) {
      return key == null ? false : this.contains((Integer)key);
   }

   /** @deprecated */
   @Deprecated
   default boolean remove(Object key) {
      return key == null ? false : this.rem((Integer)key);
   }

   int[] toIntArray();

   /** @deprecated */
   @Deprecated
   int[] toIntArray(int[] var1);

   int[] toArray(int[] var1);

   boolean addAll(IntCollection var1);

   boolean containsAll(IntCollection var1);

   boolean removeAll(IntCollection var1);

   /** @deprecated */
   @Deprecated
   default boolean removeIf(Predicate filter) {
      return this.removeIf((key) -> {
         return filter.test(key);
      });
   }

   default boolean removeIf(IntPredicate filter) {
      Objects.requireNonNull(filter);
      boolean removed = false;
      IntIterator each = this.iterator();

      while(each.hasNext()) {
         if (filter.test(each.nextInt())) {
            each.remove();
            removed = true;
         }
      }

      return removed;
   }

   boolean retainAll(IntCollection var1);
}
