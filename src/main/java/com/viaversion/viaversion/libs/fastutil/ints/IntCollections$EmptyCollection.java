package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import java.util.Collection;

public abstract class IntCollections$EmptyCollection extends AbstractIntCollection {
   protected IntCollections$EmptyCollection() {
   }

   public boolean contains(int k) {
      return false;
   }

   public Object[] toArray() {
      return ObjectArrays.EMPTY_ARRAY;
   }

   public IntBidirectionalIterator iterator() {
      return IntIterators.EMPTY_ITERATOR;
   }

   public int size() {
      return 0;
   }

   public void clear() {
   }

   public int hashCode() {
      return 0;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof Collection) ? false : ((Collection)o).isEmpty();
      }
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(IntCollection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(IntCollection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(IntCollection c) {
      throw new UnsupportedOperationException();
   }
}
