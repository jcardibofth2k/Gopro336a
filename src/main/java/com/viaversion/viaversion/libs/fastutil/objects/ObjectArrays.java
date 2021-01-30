package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.Hash.Strategy;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.1;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public final class ObjectArrays {
   public static final Object[] EMPTY_ARRAY = new Object[0];
   public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
   private static final int QUICKSORT_NO_REC = 16;
   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
   private static final int MERGESORT_NO_REC = 16;
   public static final Strategy HASH_STRATEGY = new com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.ArrayHashStrategy((1)null);

   private ObjectArrays() {
   }

   private static Object[] newArray(Object[] prototype, int length) {
      Class klass = prototype.getClass();
      if (klass == Object[].class) {
         return length == 0 ? EMPTY_ARRAY : new Object[length];
      } else {
         return (Object[])Array.newInstance(klass.getComponentType(), length);
      }
   }

   public static Object[] forceCapacity(Object[] array, int length, int preserve) {
      Object[] t = newArray(array, length);
      System.arraycopy(array, 0, t, 0, preserve);
      return t;
   }

   public static Object[] ensureCapacity(Object[] array, int length) {
      return ensureCapacity(array, length, array.length);
   }

   public static Object[] ensureCapacity(Object[] array, int length, int preserve) {
      return length > array.length ? forceCapacity(array, length, preserve) : array;
   }

   public static Object[] grow(Object[] array, int length) {
      return grow(array, length, array.length);
   }

   public static Object[] grow(Object[] array, int length, int preserve) {
      if (length > array.length) {
         int newLength = (int)Math.max(Math.min((long)array.length + (long)(array.length >> 1), 2147483639L), (long)length);
         Object[] t = newArray(array, newLength);
         System.arraycopy(array, 0, t, 0, preserve);
         return t;
      } else {
         return array;
      }
   }

   public static Object[] trim(Object[] array, int length) {
      if (length >= array.length) {
         return array;
      } else {
         Object[] t = newArray(array, length);
         System.arraycopy(array, 0, t, 0, length);
         return t;
      }
   }

   public static Object[] setLength(Object[] array, int length) {
      if (length == array.length) {
         return array;
      } else {
         return length < array.length ? trim(array, length) : ensureCapacity(array, length);
      }
   }

   public static Object[] copy(Object[] array, int offset, int length) {
      ensureOffsetLength(array, offset, length);
      Object[] a = newArray(array, length);
      System.arraycopy(array, offset, a, 0, length);
      return a;
   }

   public static Object[] copy(Object[] array) {
      return (Object[])array.clone();
   }

   /** @deprecated */
   @Deprecated
   public static void fill(Object[] array, Object value) {
      for(int i = array.length; i-- != 0; array[i] = value) {
      }

   }

   /** @deprecated */
   @Deprecated
   public static void fill(Object[] array, int from, int to, Object value) {
      ensureFromTo(array, from, to);
      if (from == 0) {
         while(to-- != 0) {
            array[to] = value;
         }
      } else {
         for(int i = from; i < to; ++i) {
            array[i] = value;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static boolean equals(Object[] a1, Object[] a2) {
      int i = a1.length;
      if (i != a2.length) {
         return false;
      } else {
         do {
            if (i-- == 0) {
               return true;
            }
         } while(Objects.equals(a1[i], a2[i]));

         return false;
      }
   }

   public static void ensureFromTo(Object[] a, int from, int to) {
      Arrays.ensureFromTo(a.length, from, to);
   }

   public static void ensureOffsetLength(Object[] a, int offset, int length) {
      Arrays.ensureOffsetLength(a.length, offset, length);
   }

   public static void ensureSameLength(Object[] a, Object[] b) {
      if (a.length != b.length) {
         throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
      }
   }

   public static void swap(Object[] x, int a, int b) {
      Object t = x[a];
      x[a] = x[b];
      x[b] = t;
   }

   public static void swap(Object[] x, int a, int b, int n) {
      for(int i = 0; i < n; ++b) {
         swap(x, a, b);
         ++i;
         ++a;
      }

   }

   private static int med3(Object[] x, int a, int b, int c, Comparator comp) {
      int ab = comp.compare(x[a], x[b]);
      int ac = comp.compare(x[a], x[c]);
      int bc = comp.compare(x[b], x[c]);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static void selectionSort(Object[] a, int from, int to, Comparator comp) {
      for(int i = from; i < to - 1; ++i) {
         int m = i;

         for(int j = i + 1; j < to; ++j) {
            if (comp.compare(a[j], a[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            Object u = a[i];
            a[i] = a[m];
            a[m] = u;
         }
      }

   }

   private static void insertionSort(Object[] a, int from, int to, Comparator comp) {
      int i = from;

      while(true) {
         ++i;
         if (i >= to) {
            return;
         }

         Object t = a[i];
         int j = i;

         for(Object u = a[i - 1]; comp.compare(t, u) < 0; u = a[j - 1]) {
            a[j] = u;
            if (from == j - 1) {
               --j;
               break;
            }

            --j;
         }

         a[j] = t;
      }
   }

   public static void quickSort(Object[] x, int from, int to, Comparator comp) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, from, to, comp);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, from, from + s, from + 2 * s, comp);
            m = med3(x, m - s, m, m + s, comp);
            n = med3(x, n - 2 * s, n - s, n, comp);
         }

         m = med3(x, l, m, n, comp);
         Object v = x[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while(true) {
            int s;
            while(b > c || (s = comp.compare(x[b], v)) > 0) {
               for(; c >= b && (s = comp.compare(x[c], v)) >= 0; --c) {
                  if (s == 0) {
                     swap(x, c, d--);
                  }
               }

               if (b > c) {
                  s = Math.min(a - from, b - a);
                  swap(x, from, b - s, s);
                  s = Math.min(d - c, to - d - 1);
                  swap(x, b, to - s, s);
                  if ((s = b - a) > 1) {
                     quickSort(x, from, from + s, comp);
                  }

                  if ((s = d - c) > 1) {
                     quickSort(x, to - s, to, comp);
                  }

                  return;
               }

               swap(x, b++, c--);
            }

            if (s == 0) {
               swap(x, a++, b);
            }

            ++b;
         }
      }
   }

   public static void quickSort(Object[] x, Comparator comp) {
      quickSort(x, 0, x.length, comp);
   }

   public static void parallelQuickSort(Object[] x, int from, int to, Comparator comp) {
      if (to - from < 8192) {
         quickSort(x, from, to, comp);
      } else {
         ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
         pool.invoke(new com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.ForkJoinQuickSortComp(x, from, to, comp));
         pool.shutdown();
      }

   }

   public static void parallelQuickSort(Object[] x, Comparator comp) {
      parallelQuickSort(x, 0, x.length, comp);
   }

   private static int med3(Object[] x, int a, int b, int c) {
      int ab = ((Comparable)x[a]).compareTo(x[b]);
      int ac = ((Comparable)x[a]).compareTo(x[c]);
      int bc = ((Comparable)x[b]).compareTo(x[c]);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static void selectionSort(Object[] a, int from, int to) {
      for(int i = from; i < to - 1; ++i) {
         int m = i;

         for(int j = i + 1; j < to; ++j) {
            if (((Comparable)a[j]).compareTo(a[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            Object u = a[i];
            a[i] = a[m];
            a[m] = u;
         }
      }

   }

   private static void insertionSort(Object[] a, int from, int to) {
      int i = from;

      while(true) {
         ++i;
         if (i >= to) {
            return;
         }

         Object t = a[i];
         int j = i;

         for(Object u = a[i - 1]; ((Comparable)t).compareTo(u) < 0; u = a[j - 1]) {
            a[j] = u;
            if (from == j - 1) {
               --j;
               break;
            }

            --j;
         }

         a[j] = t;
      }
   }

   public static void quickSort(Object[] x, int from, int to) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, from, from + s, from + 2 * s);
            m = med3(x, m - s, m, m + s);
            n = med3(x, n - 2 * s, n - s, n);
         }

         m = med3(x, l, m, n);
         Object v = x[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while(true) {
            int s;
            while(b > c || (s = ((Comparable)x[b]).compareTo(v)) > 0) {
               for(; c >= b && (s = ((Comparable)x[c]).compareTo(v)) >= 0; --c) {
                  if (s == 0) {
                     swap(x, c, d--);
                  }
               }

               if (b > c) {
                  s = Math.min(a - from, b - a);
                  swap(x, from, b - s, s);
                  s = Math.min(d - c, to - d - 1);
                  swap(x, b, to - s, s);
                  if ((s = b - a) > 1) {
                     quickSort(x, from, from + s);
                  }

                  if ((s = d - c) > 1) {
                     quickSort(x, to - s, to);
                  }

                  return;
               }

               swap(x, b++, c--);
            }

            if (s == 0) {
               swap(x, a++, b);
            }

            ++b;
         }
      }
   }

   public static void quickSort(Object[] x) {
      quickSort(x, 0, x.length);
   }

   public static void parallelQuickSort(Object[] x, int from, int to) {
      if (to - from < 8192) {
         quickSort(x, from, to);
      } else {
         ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
         pool.invoke(new com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.ForkJoinQuickSort(x, from, to));
         pool.shutdown();
      }

   }

   public static void parallelQuickSort(Object[] x) {
      parallelQuickSort(x, 0, x.length);
   }

   private static int med3Indirect(int[] perm, Object[] x, int a, int b, int c) {
      Object aa = x[perm[a]];
      Object bb = x[perm[b]];
      Object cc = x[perm[c]];
      int ab = ((Comparable)aa).compareTo(bb);
      int ac = ((Comparable)aa).compareTo(cc);
      int bc = ((Comparable)bb).compareTo(cc);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static void insertionSortIndirect(int[] perm, Object[] a, int from, int to) {
      int i = from;

      while(true) {
         ++i;
         if (i >= to) {
            return;
         }

         int t = perm[i];
         int j = i;

         for(int u = perm[i - 1]; ((Comparable)a[t]).compareTo(a[u]) < 0; u = perm[j - 1]) {
            perm[j] = u;
            if (from == j - 1) {
               --j;
               break;
            }

            --j;
         }

         perm[j] = t;
      }
   }

   public static void quickSortIndirect(int[] perm, Object[] x, int from, int to) {
      int len = to - from;
      if (len < 16) {
         insertionSortIndirect(perm, x, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3Indirect(perm, x, from, from + s, from + 2 * s);
            m = med3Indirect(perm, x, m - s, m, m + s);
            n = med3Indirect(perm, x, n - 2 * s, n - s, n);
         }

         m = med3Indirect(perm, x, l, m, n);
         Object v = x[perm[m]];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while(true) {
            int s;
            while(b > c || (s = ((Comparable)x[perm[b]]).compareTo(v)) > 0) {
               for(; c >= b && (s = ((Comparable)x[perm[c]]).compareTo(v)) >= 0; --c) {
                  if (s == 0) {
                     IntArrays.swap(perm, c, d--);
                  }
               }

               if (b > c) {
                  s = Math.min(a - from, b - a);
                  IntArrays.swap(perm, from, b - s, s);
                  s = Math.min(d - c, to - d - 1);
                  IntArrays.swap(perm, b, to - s, s);
                  if ((s = b - a) > 1) {
                     quickSortIndirect(perm, x, from, from + s);
                  }

                  if ((s = d - c) > 1) {
                     quickSortIndirect(perm, x, to - s, to);
                  }

                  return;
               }

               IntArrays.swap(perm, b++, c--);
            }

            if (s == 0) {
               IntArrays.swap(perm, a++, b);
            }

            ++b;
         }
      }
   }

   public static void quickSortIndirect(int[] perm, Object[] x) {
      quickSortIndirect(perm, x, 0, x.length);
   }

   public static void parallelQuickSortIndirect(int[] perm, Object[] x, int from, int to) {
      if (to - from < 8192) {
         quickSortIndirect(perm, x, from, to);
      } else {
         ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
         pool.invoke(new com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.ForkJoinQuickSortIndirect(perm, x, from, to));
         pool.shutdown();
      }

   }

   public static void parallelQuickSortIndirect(int[] perm, Object[] x) {
      parallelQuickSortIndirect(perm, x, 0, x.length);
   }

   public static void stabilize(int[] perm, Object[] x, int from, int to) {
      int curr = from;

      for(int i = from + 1; i < to; ++i) {
         if (x[perm[i]] != x[perm[curr]]) {
            if (i - curr > 1) {
               IntArrays.parallelQuickSort(perm, curr, i);
            }

            curr = i;
         }
      }

      if (to - curr > 1) {
         IntArrays.parallelQuickSort(perm, curr, to);
      }

   }

   public static void stabilize(int[] perm, Object[] x) {
      stabilize(perm, x, 0, perm.length);
   }

   private static int med3(Object[] x, Object[] y, int a, int b, int c) {
      int t;
      int ab = (t = ((Comparable)x[a]).compareTo(x[b])) == 0 ? ((Comparable)y[a]).compareTo(y[b]) : t;
      int ac = (t = ((Comparable)x[a]).compareTo(x[c])) == 0 ? ((Comparable)y[a]).compareTo(y[c]) : t;
      int bc = (t = ((Comparable)x[b]).compareTo(x[c])) == 0 ? ((Comparable)y[b]).compareTo(y[c]) : t;
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static void swap(Object[] x, Object[] y, int a, int b) {
      Object t = x[a];
      Object u = y[a];
      x[a] = x[b];
      y[a] = y[b];
      x[b] = t;
      y[b] = u;
   }

   private static void swap(Object[] x, Object[] y, int a, int b, int n) {
      for(int i = 0; i < n; ++b) {
         swap(x, y, a, b);
         ++i;
         ++a;
      }

   }

   private static void selectionSort(Object[] a, Object[] b, int from, int to) {
      for(int i = from; i < to - 1; ++i) {
         int m = i;

         for(int j = i + 1; j < to; ++j) {
            int u;
            if ((u = ((Comparable)a[j]).compareTo(a[m])) < 0 || u == 0 && ((Comparable)b[j]).compareTo(b[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            Object t = a[i];
            a[i] = a[m];
            a[m] = t;
            t = b[i];
            b[i] = b[m];
            b[m] = t;
         }
      }

   }

   public static void quickSort(Object[] x, Object[] y, int from, int to) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, y, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, y, from, from + s, from + 2 * s);
            m = med3(x, y, m - s, m, m + s);
            n = med3(x, y, n - 2 * s, n - s, n);
         }

         m = med3(x, y, l, m, n);
         Object v = x[m];
         Object w = y[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while(true) {
            int s;
            int t;
            while(b > c || (s = (t = ((Comparable)x[b]).compareTo(v)) == 0 ? ((Comparable)y[b]).compareTo(w) : t) > 0) {
               for(; c >= b && (s = (t = ((Comparable)x[c]).compareTo(v)) == 0 ? ((Comparable)y[c]).compareTo(w) : t) >= 0; --c) {
                  if (s == 0) {
                     swap(x, y, c, d--);
                  }
               }

               if (b > c) {
                  s = Math.min(a - from, b - a);
                  swap(x, y, from, b - s, s);
                  s = Math.min(d - c, to - d - 1);
                  swap(x, y, b, to - s, s);
                  if ((s = b - a) > 1) {
                     quickSort(x, y, from, from + s);
                  }

                  if ((s = d - c) > 1) {
                     quickSort(x, y, to - s, to);
                  }

                  return;
               }

               swap(x, y, b++, c--);
            }

            if (s == 0) {
               swap(x, y, a++, b);
            }

            ++b;
         }
      }
   }

   public static void quickSort(Object[] x, Object[] y) {
      ensureSameLength(x, y);
      quickSort(x, y, 0, x.length);
   }

   public static void parallelQuickSort(Object[] x, Object[] y, int from, int to) {
      if (to - from < 8192) {
         quickSort(x, y, from, to);
      }

      ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
      pool.invoke(new com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays.ForkJoinQuickSort2(x, y, from, to));
      pool.shutdown();
   }

   public static void parallelQuickSort(Object[] x, Object[] y) {
      ensureSameLength(x, y);
      parallelQuickSort(x, y, 0, x.length);
   }

   public static void unstableSort(Object[] a, int from, int to) {
      quickSort(a, from, to);
   }

   public static void unstableSort(Object[] a) {
      unstableSort(a, 0, a.length);
   }

   public static void unstableSort(Object[] a, int from, int to, Comparator comp) {
      quickSort(a, from, to, comp);
   }

   public static void unstableSort(Object[] a, Comparator comp) {
      unstableSort(a, 0, a.length, comp);
   }

   public static void mergeSort(Object[] a, int from, int to, Object[] supp) {
      int len = to - from;
      if (len < 16) {
         insertionSort(a, from, to);
      } else {
         int mid = from + to >>> 1;
         mergeSort(supp, from, mid, a);
         mergeSort(supp, mid, to, a);
         if (((Comparable)supp[mid - 1]).compareTo(supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
         } else {
            int i = from;
            int p = from;

            for(int q = mid; i < to; ++i) {
               if (q < to && (p >= mid || ((Comparable)supp[p]).compareTo(supp[q]) > 0)) {
                  a[i] = supp[q++];
               } else {
                  a[i] = supp[p++];
               }
            }

         }
      }
   }

   public static void mergeSort(Object[] a, int from, int to) {
      mergeSort(a, from, to, (Object[])a.clone());
   }

   public static void mergeSort(Object[] a) {
      mergeSort(a, 0, a.length);
   }

   public static void mergeSort(Object[] a, int from, int to, Comparator comp, Object[] supp) {
      int len = to - from;
      if (len < 16) {
         insertionSort(a, from, to, comp);
      } else {
         int mid = from + to >>> 1;
         mergeSort(supp, from, mid, comp, a);
         mergeSort(supp, mid, to, comp, a);
         if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
         } else {
            int i = from;
            int p = from;

            for(int q = mid; i < to; ++i) {
               if (q < to && (p >= mid || comp.compare(supp[p], supp[q]) > 0)) {
                  a[i] = supp[q++];
               } else {
                  a[i] = supp[p++];
               }
            }

         }
      }
   }

   public static void mergeSort(Object[] a, int from, int to, Comparator comp) {
      mergeSort(a, from, to, comp, (Object[])a.clone());
   }

   public static void mergeSort(Object[] a, Comparator comp) {
      mergeSort(a, 0, a.length, (Comparator)comp);
   }

   public static void stableSort(Object[] a, int from, int to) {
      java.util.Arrays.sort(a, from, to);
   }

   public static void stableSort(Object[] a) {
      stableSort(a, 0, a.length);
   }

   public static void stableSort(Object[] a, int from, int to, Comparator comp) {
      java.util.Arrays.sort(a, from, to, comp);
   }

   public static void stableSort(Object[] a, Comparator comp) {
      stableSort(a, 0, a.length, comp);
   }

   public static int binarySearch(Object[] a, int from, int to, Object key) {
      --to;

      while(from <= to) {
         int mid = from + to >>> 1;
         Object midVal = a[mid];
         int cmp = ((Comparable)midVal).compareTo(key);
         if (cmp < 0) {
            from = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            to = mid - 1;
         }
      }

      return -(from + 1);
   }

   public static int binarySearch(Object[] a, Object key) {
      return binarySearch(a, 0, a.length, key);
   }

   public static int binarySearch(Object[] a, int from, int to, Object key, Comparator c) {
      --to;

      while(from <= to) {
         int mid = from + to >>> 1;
         Object midVal = a[mid];
         int cmp = c.compare(midVal, key);
         if (cmp < 0) {
            from = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            to = mid - 1;
         }
      }

      return -(from + 1);
   }

   public static int binarySearch(Object[] a, Object key, Comparator c) {
      return binarySearch(a, 0, a.length, key, c);
   }

   public static Object[] shuffle(Object[] a, int from, int to, Random random) {
      int p;
      Object t;
      for(int i = to - from; i-- != 0; a[from + p] = t) {
         p = random.nextInt(i + 1);
         t = a[from + i];
         a[from + i] = a[from + p];
      }

      return a;
   }

   public static Object[] shuffle(Object[] a, Random random) {
      int p;
      Object t;
      for(int i = a.length; i-- != 0; a[p] = t) {
         p = random.nextInt(i + 1);
         t = a[i];
         a[i] = a[p];
      }

      return a;
   }

   public static Object[] reverse(Object[] a) {
      int length = a.length;

      Object t;
      for(int i = length / 2; i-- != 0; a[i] = t) {
         t = a[length - i - 1];
         a[length - i - 1] = a[i];
      }

      return a;
   }

   public static Object[] reverse(Object[] a, int from, int to) {
      int length = to - from;

      Object t;
      for(int i = length / 2; i-- != 0; a[from + i] = t) {
         t = a[from + length - i - 1];
         a[from + length - i - 1] = a[from + i];
      }

      return a;
   }

   // $FF: synthetic method
   static int access$000(Object[] x0, int x1, int x2, int x3, Comparator x4) {
      return med3(x0, x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static int access$100(Object[] x0, int x1, int x2, int x3) {
      return med3(x0, x1, x2, x3);
   }

   // $FF: synthetic method
   static int access$200(int[] x0, Object[] x1, int x2, int x3, int x4) {
      return med3Indirect(x0, x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static int access$300(Object[] x0, Object[] x1, int x2, int x3, int x4) {
      return med3(x0, x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static void access$400(Object[] x0, Object[] x1, int x2, int x3) {
      swap(x0, x1, x2, x3);
   }

   // $FF: synthetic method
   static void access$500(Object[] x0, Object[] x1, int x2, int x3, int x4) {
      swap(x0, x1, x2, x3, x4);
   }
}
