package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Comparator;
import java.util.List;

public interface IntList extends List, Comparable, IntCollection {
   IntListIterator iterator();

   IntListIterator listIterator();

   IntListIterator listIterator(int var1);

   IntList subList(int var1, int var2);

   void size(int var1);

   void getElements(int var1, int[] var2, int var3, int var4);

   void removeElements(int var1, int var2);

   void addElements(int var1, int[] var2);

   void addElements(int var1, int[] var2, int var3, int var4);

   default void setElements(int[] a) {
      this.setElements(0, a);
   }

   default void setElements(int index, int[] a) {
      this.setElements(index, a, 0, a.length);
   }

   default void setElements(int index, int[] a, int offset, int length) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      } else {
         IntArrays.ensureOffsetLength(a, offset, length);
         if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
         } else {
            IntListIterator iter = this.listIterator(index);
            int i = 0;

            while(i < length) {
               iter.nextInt();
               iter.set(a[offset + i++]);
            }

         }
      }
   }

   boolean add(int var1);

   void add(int var1, int var2);

   /** @deprecated */
   @Deprecated
   default void add(int index, Integer key) {
      this.add(index, key);
   }

   boolean addAll(int var1, IntCollection var2);

   boolean addAll(int var1, IntList var2);

   boolean addAll(IntList var1);

   int set(int var1, int var2);

   int getInt(int var1);

   int indexOf(int var1);

   int lastIndexOf(int var1);

   /** @deprecated */
   @Deprecated
   default boolean contains(Object key) {
      return IntCollection.super.contains(key);
   }

   /** @deprecated */
   @Deprecated
   default Integer get(int index) {
      return this.getInt(index);
   }

   /** @deprecated */
   @Deprecated
   default int indexOf(Object o) {
      return this.indexOf(o);
   }

   /** @deprecated */
   @Deprecated
   default int lastIndexOf(Object o) {
      return this.lastIndexOf(o);
   }

   /** @deprecated */
   @Deprecated
   default boolean add(Integer k) {
      return this.add(k);
   }

   int removeInt(int var1);

   /** @deprecated */
   @Deprecated
   default boolean remove(Object key) {
      return IntCollection.super.remove(key);
   }

   /** @deprecated */
   @Deprecated
   default Integer remove(int index) {
      return this.removeInt(index);
   }

   /** @deprecated */
   @Deprecated
   default Integer set(int index, Integer k) {
      return this.set(index, k);
   }

   /** @deprecated */
   @Deprecated
   default void sort(Comparator comparator) {
      this.sort(IntComparators.asIntComparator(comparator));
   }

   default void sort(IntComparator comparator) {
      if (comparator == null) {
         this.unstableSort(comparator);
      } else {
         int[] elements = this.toIntArray();
         IntArrays.stableSort(elements, comparator);
         this.setElements(elements);
      }

   }

   /** @deprecated */
   @Deprecated
   default void unstableSort(Comparator comparator) {
      this.unstableSort(IntComparators.asIntComparator(comparator));
   }

   default void unstableSort(IntComparator comparator) {
      int[] elements = this.toIntArray();
      if (comparator == null) {
         IntArrays.unstableSort(elements);
      } else {
         IntArrays.unstableSort(elements, comparator);
      }

      this.setElements(elements);
   }
}
