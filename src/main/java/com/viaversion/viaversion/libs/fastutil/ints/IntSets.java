package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class IntSets {
   public static final IntSets.EmptySet EMPTY_SET = new IntSets.EmptySet();

   private IntSets() {
   }

   public static IntSet singleton(int element) {
      return new IntSets.Singleton(element);
   }

   public static IntSet singleton(Integer element) {
      return new IntSets.Singleton(element);
   }

   public static IntSet synchronize(IntSet s) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntSets.SynchronizedSet(s);
   }

   public static IntSet synchronize(IntSet s, Object sync) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntSets.SynchronizedSet(s, sync);
   }

   public static IntSet unmodifiable(IntSet s) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntSets.UnmodifiableSet(s);
   }

   public static class Singleton extends AbstractIntSet implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int element;

      protected Singleton(int element) {
         this.element = element;
      }

      public boolean contains(int k) {
         return k == this.element;
      }

      public boolean remove(int k) {
         throw new UnsupportedOperationException();
      }

      public IntListIterator iterator() {
         return IntIterators.singleton(this.element);
      }

      public int size() {
         return 1;
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

      public Object clone() {
         return this;
      }
   }

   public static class EmptySet extends IntCollections$EmptyCollection implements IntSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      public boolean remove(int ok) {
         throw new UnsupportedOperationException();
      }

      public Object clone() {
         return IntSets.EMPTY_SET;
      }

      public boolean equals(Object o) {
         return o instanceof Set && ((Set)o).isEmpty();
      }

      /** @deprecated */
      @Deprecated
      public boolean rem(int k) {
         return super.rem(k);
      }

      private Object readResolve() {
         return IntSets.EMPTY_SET;
      }
   }
}
