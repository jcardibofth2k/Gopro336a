package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList.1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.RandomAccess;

public class ObjectArrayList extends AbstractObjectList implements RandomAccess, Cloneable, Serializable {
   private static final long serialVersionUID = -7046029254386353131L;
   public static final int DEFAULT_INITIAL_CAPACITY = 10;
   protected final boolean wrapped;
   // $FF: renamed from: a java.lang.Object[]
   protected transient Object[] field_77;
   protected int size;

   protected ObjectArrayList(Object[] a, boolean dummy) {
      this.field_77 = a;
      this.wrapped = true;
   }

   public ObjectArrayList(int capacity) {
      if (capacity < 0) {
         throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
      } else {
         if (capacity == 0) {
            this.field_77 = ObjectArrays.EMPTY_ARRAY;
         } else {
            this.field_77 = new Object[capacity];
         }

         this.wrapped = false;
      }
   }

   public ObjectArrayList() {
      this.field_77 = ObjectArrays.DEFAULT_EMPTY_ARRAY;
      this.wrapped = false;
   }

   public ObjectArrayList(Collection c) {
      this(c.size());
      this.size = ObjectIterators.unwrap(c.iterator(), this.field_77);
   }

   public ObjectArrayList(ObjectCollection c) {
      this(c.size());
      this.size = ObjectIterators.unwrap(c.iterator(), this.field_77);
   }

   public ObjectArrayList(ObjectList l) {
      this(l.size());
      l.getElements(0, this.field_77, 0, this.size = l.size());
   }

   public ObjectArrayList(Object[] a) {
      this(a, 0, a.length);
   }

   public ObjectArrayList(Object[] a, int offset, int length) {
      this(length);
      System.arraycopy(a, offset, this.field_77, 0, length);
      this.size = length;
   }

   public ObjectArrayList(Iterator i) {
      this();

      while(i.hasNext()) {
         this.add(i.next());
      }

   }

   public ObjectArrayList(ObjectIterator i) {
      this();

      while(i.hasNext()) {
         this.add(i.next());
      }

   }

   public Object[] elements() {
      return this.field_77;
   }

   public static ObjectArrayList wrap(Object[] a, int length) {
      if (length > a.length) {
         throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
      } else {
         ObjectArrayList l = new ObjectArrayList(a, false);
         l.size = length;
         return l;
      }
   }

   public static ObjectArrayList wrap(Object[] a) {
      return wrap(a, a.length);
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.field_77.length && (this.field_77 != ObjectArrays.DEFAULT_EMPTY_ARRAY || capacity > 10)) {
         if (this.wrapped) {
            this.field_77 = ObjectArrays.ensureCapacity(this.field_77, capacity, this.size);
         } else if (capacity > this.field_77.length) {
            Object[] t = new Object[capacity];
            System.arraycopy(this.field_77, 0, t, 0, this.size);
            this.field_77 = t;
         }

         assert this.size <= this.field_77.length;

      }
   }

   private void grow(int capacity) {
      if (capacity > this.field_77.length) {
         if (this.field_77 != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            capacity = (int)Math.max(Math.min((long)this.field_77.length + (long)(this.field_77.length >> 1), 2147483639L), (long)capacity);
         } else if (capacity < 10) {
            capacity = 10;
         }

         if (this.wrapped) {
            this.field_77 = ObjectArrays.forceCapacity(this.field_77, capacity, this.size);
         } else {
            Object[] t = new Object[capacity];
            System.arraycopy(this.field_77, 0, t, 0, this.size);
            this.field_77 = t;
         }

         assert this.size <= this.field_77.length;

      }
   }

   public void add(int index, Object k) {
      this.ensureIndex(index);
      this.grow(this.size + 1);
      if (index != this.size) {
         System.arraycopy(this.field_77, index, this.field_77, index + 1, this.size - index);
      }

      this.field_77[index] = k;
      ++this.size;

      assert this.size <= this.field_77.length;

   }

   public boolean add(Object k) {
      this.grow(this.size + 1);
      this.field_77[this.size++] = k;

      assert this.size <= this.field_77.length;

      return true;
   }

   public Object get(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         return this.field_77[index];
      }
   }

   public int indexOf(Object k) {
      for(int i = 0; i < this.size; ++i) {
         if (Objects.equals(k, this.field_77[i])) {
            return i;
         }
      }

      return -1;
   }

   public int lastIndexOf(Object k) {
      int i = this.size;

      do {
         if (i-- == 0) {
            return -1;
         }
      } while(!Objects.equals(k, this.field_77[i]));

      return i;
   }

   public Object remove(int index) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         Object old = this.field_77[index];
         --this.size;
         if (index != this.size) {
            System.arraycopy(this.field_77, index + 1, this.field_77, index, this.size - index);
         }

         this.field_77[this.size] = null;

         assert this.size <= this.field_77.length;

         return old;
      }
   }

   public boolean remove(Object k) {
      int index = this.indexOf(k);
      if (index == -1) {
         return false;
      } else {
         this.remove(index);

         assert this.size <= this.field_77.length;

         return true;
      }
   }

   public Object set(int index, Object k) {
      if (index >= this.size) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
      } else {
         Object old = this.field_77[index];
         this.field_77[index] = k;
         return old;
      }
   }

   public void clear() {
      Arrays.fill(this.field_77, 0, this.size, (Object)null);
      this.size = 0;

      assert this.size <= this.field_77.length;

   }

   public int size() {
      return this.size;
   }

   public void size(int size) {
      if (size > this.field_77.length) {
         this.field_77 = ObjectArrays.forceCapacity(this.field_77, size, this.size);
      }

      if (size > this.size) {
         Arrays.fill(this.field_77, this.size, size, (Object)null);
      } else {
         Arrays.fill(this.field_77, size, this.size, (Object)null);
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
      if (n < this.field_77.length && this.size != this.field_77.length) {
         Object[] t = new Object[Math.max(n, this.size)];
         System.arraycopy(this.field_77, 0, t, 0, this.size);
         this.field_77 = t;

         assert this.size <= this.field_77.length;

      }
   }

   public void getElements(int from, Object[] a, int offset, int length) {
      ObjectArrays.ensureOffsetLength(a, offset, length);
      System.arraycopy(this.field_77, from, a, offset, length);
   }

   public void removeElements(int from, int to) {
      com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, from, to);
      System.arraycopy(this.field_77, to, this.field_77, from, this.size - to);
      this.size -= to - from;

      for(int i = to - from; i-- != 0; this.field_77[this.size + i] = null) {
      }

   }

   public void addElements(int index, Object[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      this.grow(this.size + length);
      System.arraycopy(this.field_77, index, this.field_77, index + length, this.size - index);
      System.arraycopy(a, offset, this.field_77, index, length);
      this.size += length;
   }

   public void setElements(int index, Object[] a, int offset, int length) {
      this.ensureIndex(index);
      ObjectArrays.ensureOffsetLength(a, offset, length);
      if (index + length > this.size) {
         throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
      } else {
         System.arraycopy(a, offset, this.field_77, index, length);
      }
   }

   public boolean removeAll(Collection c) {
      Object[] a = this.field_77;
      int j = 0;

      for(int i = 0; i < this.size; ++i) {
         if (!c.contains(a[i])) {
            a[j++] = a[i];
         }
      }

      Arrays.fill(a, j, this.size, (Object)null);
      boolean modified = this.size != j;
      this.size = j;
      return modified;
   }

   public ObjectListIterator listIterator(int index) {
      this.ensureIndex(index);
      return new 1(this, index);
   }

   public void sort(Comparator comp) {
      if (comp == null) {
         ObjectArrays.stableSort(this.field_77, 0, this.size);
      } else {
         ObjectArrays.stableSort(this.field_77, 0, this.size, comp);
      }

   }

   public void unstableSort(Comparator comp) {
      if (comp == null) {
         ObjectArrays.unstableSort(this.field_77, 0, this.size);
      } else {
         ObjectArrays.unstableSort(this.field_77, 0, this.size, comp);
      }

   }

   public ObjectArrayList clone() {
      ObjectArrayList c = new ObjectArrayList(this.size);
      System.arraycopy(this.field_77, 0, c.field_77, 0, this.size);
      c.size = this.size;
      return c;
   }

   private boolean valEquals(Object a, Object b) {
      return a == null ? b == null : a.equals(b);
   }

   public boolean equals(ObjectArrayList l) {
      if (l == this) {
         return true;
      } else {
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            Object[] a1 = this.field_77;
            Object[] a2 = l.field_77;

            do {
               if (s-- == 0) {
                  return true;
               }
            } while(this.valEquals(a1[s], a2[s]));

            return false;
         }
      }
   }

   public int compareTo(ObjectArrayList l) {
      int s1 = this.size();
      int s2 = l.size();
      Object[] a1 = this.field_77;
      Object[] a2 = l.field_77;

      int i;
      for(i = 0; i < s1 && i < s2; ++i) {
         Object e1 = a1[i];
         Object e2 = a2[i];
         int r;
         if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
            return r;
         }
      }

      return i < s2 ? -1 : (i < s1 ? 1 : 0);
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();

      for(int i = 0; i < this.size; ++i) {
         s.writeObject(this.field_77[i]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_77 = new Object[this.size];

      for(int i = 0; i < this.size; ++i) {
         this.field_77[i] = s.readObject();
      }

   }
}
