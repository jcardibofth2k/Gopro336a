package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList.1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.RandomAccess;

public class IntArrayList extends AbstractIntList implements RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = -7046029254386353130L;
   public static final int DEFAULT_INITIAL_CAPACITY = 10;
   // $FF: renamed from: a int[]
   protected transient int[] field_74;
   protected int size;

   protected IntArrayList(int[] a, boolean dummy) {
      this.field_74 = a;
   }

   public IntArrayList(int capacity) {
      if (capacity < 0) {
         throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
      } else {
         if (capacity == 0) {
            this.field_74 = IntArrays.EMPTY_ARRAY;
         } else {
            this.field_74 = new int[capacity];
         }

      }
   }

   public IntArrayList() {
      this.field_74 = IntArrays.DEFAULT_EMPTY_ARRAY;
   }

   public IntArrayList(Collection c) {
      this(c.size());
      this.size = IntIterators.unwrap(IntIterators.asIntIterator(c.iterator()), this.field_74);
   }

   public IntArrayList(IntCollection c) {
      this(c.size());
      this.size = IntIterators.unwrap(c.iterator(), this.field_74);
   }

   public IntArrayList(IntList l) {
      this(l.size());
      l.getElements(0, this.field_74, 0, this.size = l.size());
   }

   public IntArrayList(int[] a) {
      this(a, 0, a.length);
   }

   public IntArrayList(int[] a, int offset, int length) {
      this(length);
      System.arraycopy(a, offset, this.field_74, 0, length);
      this.size = length;
   }

   public IntArrayList(Iterator i) {
      this();

      while(i.hasNext()) {
         this.add((Integer)i.next());
      }

   }

   public IntArrayList(IntIterator i) {
      this();

      while(i.hasNext()) {
         this.add(i.nextInt());
      }

   }

   public int[] elements() {
      return this.field_74;
   }

   public static IntArrayList wrap(int[] a, int length) {
      if (length > a.length) {
         throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
      } else {
         IntArrayList l = new IntArrayList(a, false);
         l.size = length;
         return l;
      }
   }

   public static IntArrayList wrap(int[] a) {
      return wrap(a, a.length);
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.field_74.length && (this.field_74 != IntArrays.DEFAULT_EMPTY_ARRAY || capacity > 10)) {
         this.field_74 = IntArrays.ensureCapacity(this.field_74, capacity, this.size);

         assert this.size <= this.field_74.length;

      }
   }

   private void grow(int capacity) {
      if (capacity > this.field_74.length) {
         if (this.field_74 != IntArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.field_74.length + (long)(this.field_74.length >> 1), 2147483639L), capacity);
         } else if (capacity < 10) {
            capacity = 10;
         }

         this.field_74 = IntArrays.forceCapacity(this.field_74, capacity, this.size);

         assert this.size <= this.field_74.length;

      }
   }

   public void add(int index, int k) {
      this.ensureIndex(index);
      this.grow(this.size + 1);
      if (index != this.size) {
         System.arraycopy(this.field_74, index, this.field_74, index + 1, this.size - index);
      }

      this.field_74[index] = k;
      ++this.size;

      assert this.size <= this.field_74.length;

   }

   public boolean add(int k) {
      this.grow(this.size + 1);
      this.field_74[this.size++] = k;

      assert this.size <= this.field_74.length;

      return true;
   }

   public int getInt(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         return this.field_74[index];
      }
   }

   public int indexOf(int k) {
      for(int i = 0; i < this.size; ++i) {
         if (k == this.field_74[i]) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(int k) {
      int i = this.size;

      do {
         if (i-- == 0) {
            return -1;
         }
      } while(k != this.field_74[i]);

      return i;
   }

   public int removeInt(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         int old = this.field_74[index];
         --this.size;
         if (index != this.size) {
            System.arraycopy(this.field_74, index + 1, this.field_74, index, this.size - index);
         }

         assert this.size <= this.field_74.length;

         return old;
      }
   }

   public boolean rem(int k) {
      int index = this.indexOf(k);
      if (index == -1) {
         return false;
      } else {
         this.removeInt(index);

         assert this.size <= this.field_74.length;

         return true;
      }
   }

   public int set(int index, int k) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         int old = this.field_74[index];
         this.field_74[index] = k;
         return old;
      }
   }

   public void clear() {
      this.size = 0;

      assert this.size <= this.field_74.length;

   }

   public int size() {
      return this.size;
   }

   public void size(int size) {
      if (size > this.field_74.length) {
         this.field_74 = IntArrays.forceCapacity(this.field_74, size, this.size);
      }

      if (size > this.size) {
         Arrays.fill(this.field_74, this.size, size, 0);
      }

      this.size = size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public void trim() {
      this.trim(0);
   }

   public void trim(int n) {
      if (n < this.field_74.length && this.size != this.field_74.length) {
         int[] t = new int[Math.max(n, this.size)];
         System.arraycopy(this.field_74, 0, t, 0, this.size);
         this.field_74 = t;

         assert this.size <= this.field_74.length;

      }
   }

   public void getElements(int from, int[] a, int offset, int length) {
      IntArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.field_74, from, a, offset, length);
   }

   public void removeElements(int from, int to) {
      com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, from, to);
      System.arraycopy(this.field_74, to, this.field_74, from, this.size - to);
      this.size -= to - from;
   }

   public void addElements(int index, int[] a, int offset, int length) {
      this.ensureIndex(index);
      IntArrays.ensureOffsetLength(a, offset, length);
      this.grow(this.size + length);
      System.arraycopy(this.field_74, index, this.field_74, index + length, this.size - index);
      System.arraycopy(a, offset, this.field_74, index, length);
      this.size += length;
   }

   public void setElements(int index, int[] a, int offset, int length) {
      this.ensureIndex(index);
      IntArrays.ensureOffsetLength(a, offset, length);
      if (index + length > this.size) {
         throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
      } else {
         System.arraycopy(a, offset, this.field_74, index, length);
      }
   }

   public int[] toArray(int[] a) {
      if (a == null || a.length < this.size) {
         a = new int[this.size];
      }

      System.arraycopy(this.field_74, 0, a, 0, this.size);
      return a;
   }

   public boolean addAll(int index, IntCollection c) {
      this.ensureIndex(index);
      int n = c.size();
      if (n == 0) {
         return false;
      } else {
         this.grow(this.size + n);
         if (index != this.size) {
            System.arraycopy(this.field_74, index, this.field_74, index + n, this.size - index);
         }

         IntIterator i = c.iterator();

         for(this.size += n; n-- != 0; this.field_74[index++] = i.nextInt()) {
         }

         assert this.size <= this.field_74.length;

         return true;
      }
   }

   public boolean addAll(int index, IntList l) {
      this.ensureIndex(index);
      int n = l.size();
      if (n == 0) {
         return false;
      } else {
         this.grow(this.size + n);
         if (index != this.size) {
            System.arraycopy(this.field_74, index, this.field_74, index + n, this.size - index);
         }

         l.getElements(0, this.field_74, index, n);
         this.size += n;

         assert this.size <= this.field_74.length;

         return true;
      }
   }

   public boolean removeAll(IntCollection c) {
      int[] a = this.field_74;
      int j = 0;

      for(int i = 0; i < this.size; ++i) {
         if (!c.contains(a[i])) {
            a[j++] = a[i];
         }
      }

      boolean modified = this.size != j;
      this.size = j;
      return modified;
   }

   public boolean removeAll(Collection c) {
      int[] a = this.field_74;
      int j = 0;

      for(int i = 0; i < this.size; ++i) {
         if (!c.contains(a[i])) {
            a[j++] = a[i];
         }
      }

      boolean modified = this.size != j;
      this.size = j;
      return modified;
   }

   public IntListIterator listIterator(int index) {
      this.ensureIndex(index);
      return new 1(this, index)
   }

   public void sort(IntComparator comp) {
      if (comp == null) {
         IntArrays.stableSort(this.field_74, 0, this.size);
      } else {
         IntArrays.stableSort(this.field_74, 0, this.size, comp);
      }

   }

   public void unstableSort(IntComparator comp) {
      if (comp == null) {
         IntArrays.unstableSort(this.field_74, 0, this.size);
      } else {
         IntArrays.unstableSort(this.field_74, 0, this.size, comp);
      }

   }

   public IntArrayList clone() {
      IntArrayList c = new IntArrayList(this.size);
      System.arraycopy(this.field_74, 0, c.field_74, 0, this.size);
      c.size = this.size;
      return c;
   }

   public boolean equals(IntArrayList l) {
      if (l == this) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            int[] a1 = this.field_74;
            int[] a2 = l.field_74;

            do {
               if (s-- == 0) {
                  return true;
               }
            } while(a1[s] == a2[s]);

            return false;
         }
      }
   }

   public int compareTo(IntArrayList l) {
      int s1 = this.size();
      int s2 = l.size();
      int[] a1 = this.field_74;
      int[] a2 = l.field_74;

      int i;
      for(i = 0; i < s1 && i < s2; ++i) {
         int e1 = a1[i];
         int e2 = a2[i];
         int r;
         if ((r = Integer.compare(e1, e2)) != 0) {
            return r;
         }
      }

      return i < s2 ? -1 : (i < s1 ? 1 : 0);
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();

      for(int i = 0; i < this.size; ++i) {
         s.writeInt(this.field_74[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_74 = new int[this.size];

      for(int i = 0; i < this.size; ++i) {
         this.field_74[i] = s.readInt();
      }

   }
}
