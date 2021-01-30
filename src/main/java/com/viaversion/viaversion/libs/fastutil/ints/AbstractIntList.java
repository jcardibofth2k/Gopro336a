package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.1;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack {
   protected AbstractIntList() {
   }

   protected void ensureIndex(int index) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      }
   }

   protected void ensureRestrictedIndex(int index) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index >= this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
      }
   }

   public void add(int index, int k) {
      throw new UnsupportedOperationException();
   }

   public boolean add(int k) {
      this.add(this.size(), k);
      return true;
   }

   public int removeInt(int i) {
      throw new UnsupportedOperationException();
   }

   public int set(int index, int k) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      this.ensureIndex(index);
      Iterator i = c.iterator();
      boolean retVal = i.hasNext();

      while(i.hasNext()) {
         this.add(index++, (Integer)i.next());
      }

      return retVal;
   }

   public boolean addAll(Collection c) {
      return this.addAll(this.size(), c);
   }

   public IntListIterator iterator() {
      return this.listIterator();
   }

   public IntListIterator listIterator() {
      return this.listIterator(0);
   }

   public IntListIterator listIterator(int index) {
      this.ensureIndex(index);
      return new 1(this, index);
   }

   public boolean contains(int k) {
      return this.indexOf(k) >= 0;
   }

   public int indexOf(int k) {
      IntListIterator i = this.listIterator();

      int e;
      do {
         if (!i.hasNext()) {
            return -1;
         }

         e = i.nextInt();
      } while(k != e);

      return i.previousIndex();
   }

   public int lastIndexOf(int k) {
      IntListIterator i = this.listIterator(this.size());

      int e;
      do {
         if (!i.hasPrevious()) {
            return -1;
         }

         e = i.previousInt();
      } while(k != e);

      return i.nextIndex();
   }

   public void size(int size) {
      int i = this.size();
      if (size > i) {
         while(i++ < size) {
            this.add(0);
         }
      } else {
         while(i-- != size) {
            this.removeInt(i);
         }
      }

   }

   public IntList subList(int from, int to) {
      this.ensureIndex(from);
      this.ensureIndex(to);
      if (from > to) {
         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         return new com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList.IntSubList(this, from, to);
      }
   }

   public void removeElements(int from, int to) {
      this.ensureIndex(to);
      IntListIterator i = this.listIterator(from);
      int n = to - from;
      if (n < 0) {
         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         while(n-- != 0) {
            i.nextInt();
            i.remove();
         }

      }
   }

   public void addElements(int index, int[] a, int offset, int length) {
      this.ensureIndex(index);
      if (offset < 0) {
         throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
      } else if (offset + length > a.length) {
         throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
      } else {
         while(length-- != 0) {
            this.add(index++, a[offset++]);
         }

      }
   }

   public void addElements(int index, int[] a) {
      this.addElements(index, a, 0, a.length);
   }

   public void getElements(int from, int[] a, int offset, int length) {
      IntListIterator i = this.listIterator(from);
      if (offset < 0) {
         throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
      } else if (offset + length > a.length) {
         throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
      } else if (from + length > this.size()) {
         throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
      } else {
         while(length-- != 0) {
            a[offset++] = i.nextInt();
         }

      }
   }

   public void clear() {
      this.removeElements(0, this.size());
   }

   private boolean valEquals(Object a, Object b) {
      return a == null ? b == null : a.equals(b);
   }

   public int hashCode() {
      IntIterator i = this.iterator();
      int h = 1;

      int k;
      for(int var3 = this.size(); var3-- != 0; h = 31 * h + k) {
         k = i.nextInt();
      }

      return h;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof List)) {
         return false;
      } else {
         List l = (List)o;
         int s = this.size();
         if (s != l.size()) {
            return false;
         } else {
            IntListIterator i1;
            if (l instanceof IntList) {
               i1 = this.listIterator();
               IntListIterator i2 = ((IntList)l).listIterator();

               do {
                  if (s-- == 0) {
                     return true;
                  }
               } while(i1.nextInt() == i2.nextInt());

               return false;
            } else {
               i1 = this.listIterator();
               ListIterator i2 = l.listIterator();

               do {
                  if (s-- == 0) {
                     return true;
                  }
               } while(this.valEquals(i1.next(), i2.next()));

               return false;
            }
         }
      }
   }

   public int compareTo(List l) {
      if (l == this) {
         return 0;
      } else {
         IntListIterator i1;
         int r;
         if (l instanceof IntList) {
            i1 = this.listIterator();
            IntListIterator i2 = ((IntList)l).listIterator();

            while(i1.hasNext() && i2.hasNext()) {
               int e1 = i1.nextInt();
               int e2 = i2.nextInt();
               if ((r = Integer.compare(e1, e2)) != 0) {
                  return r;
               }
            }

            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
         } else {
            i1 = this.listIterator();
            ListIterator i2 = l.listIterator();

            while(i1.hasNext() && i2.hasNext()) {
               if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) {
                  return r;
               }
            }

            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
         }
      }
   }

   public void push(int o) {
      this.add(o);
   }

   public int popInt() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.removeInt(this.size() - 1);
      }
   }

   public int topInt() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.getInt(this.size() - 1);
      }
   }

   public int peekInt(int i) {
      return this.getInt(this.size() - 1 - i);
   }

   public boolean rem(int k) {
      int index = this.indexOf(k);
      if (index == -1) {
         return false;
      } else {
         this.removeInt(index);
         return true;
      }
   }

   public boolean addAll(int index, IntCollection c) {
      this.ensureIndex(index);
      IntIterator i = c.iterator();
      boolean retVal = i.hasNext();

      while(i.hasNext()) {
         this.add(index++, i.nextInt());
      }

      return retVal;
   }

   public boolean addAll(int index, IntList l) {
      return this.addAll(index, (IntCollection)l);
   }

   public boolean addAll(IntCollection c) {
      return this.addAll(this.size(), c);
   }

   public boolean addAll(IntList l) {
      return this.addAll(this.size(), l);
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      IntIterator i = this.iterator();
      int n = this.size();
      boolean first = true;
      s.append("[");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         int k = i.nextInt();
         s.append(String.valueOf(k));
      }

      s.append("]");
      return s.toString();
   }
}
