package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Comparator;
import java.util.List;

public interface ObjectList extends List, Comparable, ObjectCollection {
   ObjectListIterator iterator();

   ObjectListIterator listIterator();

   ObjectListIterator listIterator(int var1);

   ObjectList subList(int var1, int var2);

   void size(int var1);

   void getElements(int var1, Object[] var2, int var3, int var4);

   void removeElements(int var1, int var2);

   void addElements(int var1, Object[] var2);

   void addElements(int var1, Object[] var2, int var3, int var4);

   default void setElements(Object[] a) {
      this.setElements(0, a);
   }

   default void setElements(int index, Object[] a) {
      this.setElements(index, a, 0, a.length);
   }

   default void setElements(int index, Object[] a, int offset, int length) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      } else {
         ObjectArrays.ensureOffsetLength(a, offset, length);
         if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
         } else {
            ObjectListIterator iter = this.listIterator(index);
            int i = 0;

            while(i < length) {
               iter.next();
               iter.set(a[offset + i++]);
            }

         }
      }
   }

   default void unstableSort(Comparator comparator) {
      Object[] elements = this.toArray();
      if (comparator == null) {
         ObjectArrays.unstableSort(elements);
      } else {
         ObjectArrays.unstableSort(elements, comparator);
      }

      this.setElements(elements);
   }
}
