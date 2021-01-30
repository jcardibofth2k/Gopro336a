package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.BigArrays;
import com.viaversion.viaversion.libs.fastutil.bytes.ByteIterator;
import com.viaversion.viaversion.libs.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;

public final class IntIterators {
   public static final IntIterators.EmptyIterator EMPTY_ITERATOR = new IntIterators.EmptyIterator();

   private IntIterators() {
   }

   public static IntListIterator singleton(int element) {
      return new IntIterators.SingletonIterator(element);
   }

   public static IntListIterator wrap(int[] array, int offset, int length) {
      IntArrays.ensureOffsetLength(array, offset, length);
      return new IntIterators.ArrayIterator(array, offset, length);
   }

   public static IntListIterator wrap(int[] array) {
      return new IntIterators.ArrayIterator(array, 0, array.length);
   }

   public static int unwrap(IntIterator i, int[] array, int offset, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0 && offset + max <= array.length) {
         int j;
         for(j = max; j-- != 0 && i.hasNext(); array[offset++] = i.nextInt()) {
         }

         return max - j - 1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static int unwrap(IntIterator i, int[] array) {
      return unwrap(i, array, 0, array.length);
   }

   public static int[] unwrap(IntIterator i, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int[] array = new int[16];

         int j;
         for(j = 0; max-- != 0 && i.hasNext(); array[j++] = i.nextInt()) {
            if (j == array.length) {
               array = IntArrays.grow(array, j + 1);
            }
         }

         return IntArrays.trim(array, j);
      }
   }

   public static int[] unwrap(IntIterator i) {
      return unwrap(i, Integer.MAX_VALUE);
   }

   public static long unwrap(IntIterator i, int[][] array, long offset, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else if (offset >= 0L && offset + max <= BigArrays.length(array)) {
         long j = max;

         while(j-- != 0L && i.hasNext()) {
            BigArrays.set(array, offset++, i.nextInt());
         }

         return max - j - 1L;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static long unwrap(IntIterator i, int[][] array) {
      return unwrap(i, array, 0L, BigArrays.length(array));
   }

   public static int unwrap(IntIterator i, IntCollection c, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while(j-- != 0 && i.hasNext()) {
            c.add(i.nextInt());
         }

         return max - j - 1;
      }
   }

   public static int[][] unwrapBig(IntIterator i, long max) {
      if (max < 0L) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int[][] array = IntBigArrays.newBigArray(16L);

         long j;
         for(j = 0L; max-- != 0L && i.hasNext(); BigArrays.set(array, j++, i.nextInt())) {
            if (j == BigArrays.length(array)) {
               array = BigArrays.grow(array, j + 1L);
            }
         }

         return BigArrays.trim(array, j);
      }
   }

   public static int[][] unwrapBig(IntIterator i) {
      return unwrapBig(i, Long.MAX_VALUE);
   }

   public static long unwrap(IntIterator i, IntCollection c) {
      long n;
      for(n = 0L; i.hasNext(); ++n) {
         c.add(i.nextInt());
      }

      return n;
   }

   public static int pour(IntIterator i, IntCollection s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
      } else {
         int j = max;

         while(j-- != 0 && i.hasNext()) {
            s.add(i.nextInt());
         }

         return max - j - 1;
      }
   }

   public static int pour(IntIterator i, IntCollection s) {
      return pour(i, s, Integer.MAX_VALUE);
   }

   public static IntList pour(IntIterator i, int max) {
      IntArrayList l = new IntArrayList();
      pour(i, l, max);
      l.trim();
      return l;
   }

   public static IntList pour(IntIterator i) {
      return pour(i, Integer.MAX_VALUE);
   }

   public static IntIterator asIntIterator(Iterator i) {
      return (IntIterator)(i instanceof IntIterator ? (IntIterator)i : new IntIterators.IteratorWrapper(i));
   }

   public static IntListIterator asIntIterator(ListIterator i) {
      return (IntListIterator)(i instanceof IntListIterator ? (IntListIterator)i : new IntIterators.ListIteratorWrapper(i));
   }

   public static boolean any(IntIterator iterator, IntPredicate predicate) {
      return indexOf(iterator, predicate) != -1;
   }

   public static boolean all(IntIterator iterator, IntPredicate predicate) {
      Objects.requireNonNull(predicate);

      while(iterator.hasNext()) {
         if (!predicate.test(iterator.nextInt())) {
            return false;
         }
      }

      return true;
   }

   public static int indexOf(IntIterator iterator, IntPredicate predicate) {
      Objects.requireNonNull(predicate);

      for(int i = 0; iterator.hasNext(); ++i) {
         if (predicate.test(iterator.nextInt())) {
            return i;
         }
      }

      return -1;
   }

   public static IntListIterator fromTo(int from, int to) {
      return new IntIterators.IntervalIterator(from, to);
   }

   public static IntIterator concat(IntIterator[] a) {
      return concat(a, 0, a.length);
   }

   public static IntIterator concat(IntIterator[] a, int offset, int length) {
      return new IntIterators.IteratorConcatenator(a, offset, length);
   }

   public static IntIterator unmodifiable(IntIterator i) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntIterators.UnmodifiableIterator(i);
   }

   public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator i) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntIterators.UnmodifiableBidirectionalIterator(i);
   }

   public static IntListIterator unmodifiable(IntListIterator i) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntIterators.UnmodifiableListIterator(i);
   }

   public static IntIterator wrap(ByteIterator iterator) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntIterators.ByteIteratorWrapper(iterator);
   }

   public static IntIterator wrap(ShortIterator iterator) {
      return new com.viaversion.viaversion.libs.fastutil.ints.IntIterators.ShortIteratorWrapper(iterator);
   }

   private static class SingletonIterator implements IntListIterator {
      private final int element;
      private int curr;

      public SingletonIterator(int element) {
         this.element = element;
      }

      public boolean hasNext() {
         return this.curr == 0;
      }

      public boolean hasPrevious() {
         return this.curr == 1;
      }

      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 1;
            return this.element;
         }
      }

      public int previousInt() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            this.curr = 0;
            return this.element;
         }
      }

      public int nextIndex() {
         return this.curr;
      }

      public int previousIndex() {
         return this.curr - 1;
      }
   }

   private static class ArrayIterator implements IntListIterator {
      private final int[] array;
      private final int offset;
      private final int length;
      private int curr;

      public ArrayIterator(int[] array, int offset, int length) {
         this.array = array;
         this.offset = offset;
         this.length = length;
      }

      public boolean hasNext() {
         return this.curr < this.length;
      }

      public boolean hasPrevious() {
         return this.curr > 0;
      }

      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + this.curr++];
         }
      }

      public int previousInt() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return this.array[this.offset + --this.curr];
         }
      }

      public int skip(int n) {
         if (n <= this.length - this.curr) {
            this.curr += n;
            return n;
         } else {
            n = this.length - this.curr;
            this.curr = this.length;
            return n;
         }
      }

      public int back(int n) {
         if (n <= this.curr) {
            this.curr -= n;
            return n;
         } else {
            n = this.curr;
            this.curr = 0;
            return n;
         }
      }

      public int nextIndex() {
         return this.curr;
      }

      public int previousIndex() {
         return this.curr - 1;
      }
   }

   private static class IteratorWrapper implements IntIterator {
      // $FF: renamed from: i java.util.Iterator
      final Iterator field_398;

      public IteratorWrapper(Iterator i) {
         this.field_398 = i;
      }

      public boolean hasNext() {
         return this.field_398.hasNext();
      }

      public void remove() {
         this.field_398.remove();
      }

      public int nextInt() {
         return (Integer)this.field_398.next();
      }
   }

   private static class ListIteratorWrapper implements IntListIterator {
      // $FF: renamed from: i java.util.ListIterator
      final ListIterator field_123;

      public ListIteratorWrapper(ListIterator i) {
         this.field_123 = i;
      }

      public boolean hasNext() {
         return this.field_123.hasNext();
      }

      public boolean hasPrevious() {
         return this.field_123.hasPrevious();
      }

      public int nextIndex() {
         return this.field_123.nextIndex();
      }

      public int previousIndex() {
         return this.field_123.previousIndex();
      }

      public void set(int k) {
         this.field_123.set(k);
      }

      public void add(int k) {
         this.field_123.add(k);
      }

      public void remove() {
         this.field_123.remove();
      }

      public int nextInt() {
         return (Integer)this.field_123.next();
      }

      public int previousInt() {
         return (Integer)this.field_123.previous();
      }
   }

   private static class IntervalIterator implements IntListIterator {
      private final int from;
      // $FF: renamed from: to int
      private final int field_132;
      int curr;

      public IntervalIterator(int from, int to) {
         this.from = this.curr = from;
         this.field_132 = to;
      }

      public boolean hasNext() {
         return this.curr < this.field_132;
      }

      public boolean hasPrevious() {
         return this.curr > this.from;
      }

      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return this.curr++;
         }
      }

      public int previousInt() {
         if (!this.hasPrevious()) {
            throw new NoSuchElementException();
         } else {
            return --this.curr;
         }
      }

      public int nextIndex() {
         return this.curr - this.from;
      }

      public int previousIndex() {
         return this.curr - this.from - 1;
      }

      public int skip(int n) {
         if (this.curr + n <= this.field_132) {
            this.curr += n;
            return n;
         } else {
            n = this.field_132 - this.curr;
            this.curr = this.field_132;
            return n;
         }
      }

      public int back(int n) {
         if (this.curr - n >= this.from) {
            this.curr -= n;
            return n;
         } else {
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
         }
      }
   }

   private static class IteratorConcatenator implements IntIterator {
      // $FF: renamed from: a com.viaversion.viaversion.libs.fastutil.ints.IntIterator[]
      final IntIterator[] field_3209;
      int offset;
      int length;
      int lastOffset = -1;

      public IteratorConcatenator(IntIterator[] a, int offset, int length) {
         this.field_3209 = a;
         this.offset = offset;
         this.length = length;
         this.advance();
      }

      private void advance() {
         while(this.length != 0 && !this.field_3209[this.offset].hasNext()) {
            --this.length;
            ++this.offset;
         }

      }

      public boolean hasNext() {
         return this.length > 0;
      }

      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            int next = this.field_3209[this.lastOffset = this.offset].nextInt();
            this.advance();
            return next;
         }
      }

      public void remove() {
         if (this.lastOffset == -1) {
            throw new IllegalStateException();
         } else {
            this.field_3209[this.lastOffset].remove();
         }
      }

      public int skip(int n) {
         this.lastOffset = -1;

         int skipped;
         for(skipped = 0; skipped < n && this.length != 0; ++this.offset) {
            skipped += this.field_3209[this.offset].skip(n - skipped);
            if (this.field_3209[this.offset].hasNext()) {
               break;
            }

            --this.length;
         }

         return skipped;
      }
   }

   public static class EmptyIterator implements IntListIterator, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyIterator() {
      }

      public boolean hasNext() {
         return false;
      }

      public boolean hasPrevious() {
         return false;
      }

      public int nextInt() {
         throw new NoSuchElementException();
      }

      public int previousInt() {
         throw new NoSuchElementException();
      }

      public int nextIndex() {
         return 0;
      }

      public int previousIndex() {
         return -1;
      }

      public int skip(int n) {
         return 0;
      }

      public int back(int n) {
         return 0;
      }

      public Object clone() {
         return IntIterators.EMPTY_ITERATOR;
      }

      private Object readResolve() {
         return IntIterators.EMPTY_ITERATOR;
      }
   }
}
