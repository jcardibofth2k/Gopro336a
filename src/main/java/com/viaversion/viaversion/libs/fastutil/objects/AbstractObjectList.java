package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Stack;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList.1;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public abstract class AbstractObjectList extends AbstractObjectCollection implements ObjectList, Stack {
   protected AbstractObjectList() {
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

   public void add(int index, Object k) {
      throw new UnsupportedOperationException();
   }

   public boolean add(Object k) {
      this.add(this.size(), k);
      return true;
   }

   public Object remove(int i) {
      throw new UnsupportedOperationException();
   }

   public Object set(int index, Object k) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      this.ensureIndex(index);
      Iterator i = c.iterator();
      boolean retVal = i.hasNext();

      while(i.hasNext()) {
         this.add(index++, i.next());
      }

      return retVal;
   }

   public boolean addAll(Collection c) {
      return this.addAll(this.size(), c);
   }

   public ObjectListIterator iterator() {
      return this.listIterator();
   }

   public ObjectListIterator listIterator() {
      return this.listIterator(0);
   }

   public ObjectListIterator listIterator(int index) {
      this.ensureIndex(index);
      return new 1(this, index);
   }

   public boolean contains(Object k) {
      return this.indexOf(k) >= 0;
   }

   public int indexOf(Object k) {
      ObjectListIterator i = this.listIterator();

      Object e;
      do {
         if (!i.hasNext()) {
            return -1;
         }

         e = i.next();
      } while(!Objects.equals(k, e));

      return i.previousIndex();
   }

   public int lastIndexOf(Object k) {
      ObjectListIterator i = this.listIterator(this.size());

      Object e;
      do {
         if (!i.hasPrevious()) {
            return -1;
         }

         e = i.previous();
      } while(!Objects.equals(k, e));

      return i.nextIndex();
   }

   public void size(int size) {
      int i = this.size();
      if (size > i) {
         while(i++ < size) {
            this.add((Object)null);
         }
      } else {
         while(i-- != size) {
            this.remove(i);
         }
      }

   }

   public ObjectList subList(int from, int to) {
      this.ensureIndex(from);
      this.ensureIndex(to);
      if (from > to) {
         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         return new com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList.ObjectSubList(this, from, to);
      }
   }

   public void removeElements(int from, int to) {
      this.ensureIndex(to);
      ObjectListIterator i = this.listIterator(from);
      int n = to - from;
      if (n < 0) {
         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else {
         while(n-- != 0) {
            i.next();
            i.remove();
         }

      }
   }

   public void addElements(int index, Object[] a, int offset, int length) {
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

   public void addElements(int index, Object[] a) {
      this.addElements(index, a, 0, a.length);
   }

   public void getElements(int from, Object[] a, int offset, int length) {
      ObjectListIterator i = this.listIterator(from);
      if (offset < 0) {
         throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
      } else if (offset + length > a.length) {
         throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
      } else if (from + length > this.size()) {
         throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
      } else {
         while(length-- != 0) {
            a[offset++] = i.next();
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
      ObjectIterator i = this.iterator();
      int h = 1;

      Object k;
      for(int var3 = this.size(); var3-- != 0; h = 31 * h + (k == null ? 0 : k.hashCode())) {
         k = i.next();
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
            ListIterator i1 = this.listIterator();
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

   public int compareTo(List l) {
      if (l == this) {
         return 0;
      } else {
         ObjectListIterator i1;
         int r;
         if (l instanceof ObjectList) {
            i1 = this.listIterator();
            ObjectListIterator i2 = ((ObjectList)l).listIterator();

            while(i1.hasNext() && i2.hasNext()) {
               Object e1 = i1.next();
               Object e2 = i2.next();
               if ((r = ((Comparable)e1).compareTo(e2)) != 0) {
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

   public void push(Object o) {
      this.add(o);
   }

   public Object pop() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.remove(this.size() - 1);
      }
   }

   public Object top() {
      if (this.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return this.get(this.size() - 1);
      }
   }

   public Object peek(int i) {
      return this.get(this.size() - 1 - i);
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator i = this.iterator();
      int n = this.size();
      boolean first = true;
      s.append("[");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Object k = i.next();
         if (this == k) {
            s.append("(this list)");
         } else {
            s.append(String.valueOf(k));
         }
      }

      s.append("]");
      return s.toString();
   }
}
