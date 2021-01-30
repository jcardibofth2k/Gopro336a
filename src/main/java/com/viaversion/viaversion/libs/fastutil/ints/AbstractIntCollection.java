package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.AbstractCollection;

public abstract class AbstractIntCollection extends AbstractCollection implements IntCollection {
   protected AbstractIntCollection() {
   }

   public abstract IntIterator iterator();

   public boolean add(int k) {
      throw new UnsupportedOperationException();
   }

   public boolean contains(int k) {
      IntIterator iterator = this.iterator();

      do {
         if (!iterator.hasNext()) {
            return false;
         }
      } while(k != iterator.nextInt());

      return true;
   }

   public boolean rem(int k) {
      IntIterator iterator = this.iterator();

      do {
         if (!iterator.hasNext()) {
            return false;
         }
      } while(k != iterator.nextInt());

      iterator.remove();
      return true;
   }

   /** @deprecated */
   @Deprecated
   public boolean add(Integer key) {
      return IntCollection.super.add(key);
   }

   /** @deprecated */
   @Deprecated
   public boolean contains(Object key) {
      return IntCollection.super.contains(key);
   }

   /** @deprecated */
   @Deprecated
   public boolean remove(Object key) {
      return IntCollection.super.remove(key);
   }

   public int[] toArray(int[] a) {
      if (a == null || a.length < this.size()) {
         a = new int[this.size()];
      }

      IntIterators.unwrap(this.iterator(), a);
      return a;
   }

   public int[] toIntArray() {
      return this.toArray((int[])null);
   }

   /** @deprecated */
   @Deprecated
   public int[] toIntArray(int[] a) {
      return this.toArray(a);
   }

   public boolean addAll(IntCollection c) {
      boolean retVal = false;
      IntIterator i = c.iterator();

      while(i.hasNext()) {
         if (this.add(i.nextInt())) {
            retVal = true;
         }
      }

      return retVal;
   }

   public boolean containsAll(IntCollection c) {
      IntIterator i = c.iterator();

      do {
         if (!i.hasNext()) {
            return true;
         }
      } while(this.contains(i.nextInt()));

      return false;
   }

   public boolean removeAll(IntCollection c) {
      boolean retVal = false;
      IntIterator i = c.iterator();

      while(i.hasNext()) {
         if (this.rem(i.nextInt())) {
            retVal = true;
         }
      }

      return retVal;
   }

   public boolean retainAll(IntCollection c) {
      boolean retVal = false;
      IntIterator i = this.iterator();

      while(i.hasNext()) {
         if (!c.contains(i.nextInt())) {
            i.remove();
            retVal = true;
         }
      }

      return retVal;
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      IntIterator i = this.iterator();
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         int k = i.nextInt();
         s.append(String.valueOf(k));
      }

      s.append("}");
      return s.toString();
   }
}
