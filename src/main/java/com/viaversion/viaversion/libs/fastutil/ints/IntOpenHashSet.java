package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet.1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntOpenHashSet extends AbstractIntSet implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient int mask;
   protected transient boolean containsNull;
   // $FF: renamed from: n int
   protected transient int field_75;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   // $FF: renamed from: f float
   protected final float field_76;

   public IntOpenHashSet(int expected, float f) {
      if (!(f <= 0.0F) && !(f > 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.field_76 = f;
            this.minN = this.field_75 = HashCommon.arraySize(expected, f);
            this.mask = this.field_75 - 1;
            this.maxFill = HashCommon.maxFill(this.field_75, f);
            this.key = new int[this.field_75 + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
      }
   }

   public IntOpenHashSet(int expected) {
      this(expected, 0.75F);
   }

   public IntOpenHashSet() {
      this(16, 0.75F);
   }

   public IntOpenHashSet(Collection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntOpenHashSet(Collection c) {
      this(c, 0.75F);
   }

   public IntOpenHashSet(IntCollection c, float f) {
      this(c.size(), f);
      this.addAll(c);
   }

   public IntOpenHashSet(IntCollection c) {
      this(c, 0.75F);
   }

   public IntOpenHashSet(IntIterator i, float f) {
      this(16, f);

      while(i.hasNext()) {
         this.add(i.nextInt());
      }

   }

   public IntOpenHashSet(IntIterator i) {
      this(i, 0.75F);
   }

   public IntOpenHashSet(Iterator i, float f) {
      this(IntIterators.asIntIterator(i), f);
   }

   public IntOpenHashSet(Iterator i) {
      this(IntIterators.asIntIterator(i));
   }

   public IntOpenHashSet(int[] a, int offset, int length, float f) {
      this(length < 0 ? 0 : length, f);
      IntArrays.ensureOffsetLength(a, offset, length);

      for(int i = 0; i < length; ++i) {
         this.add(a[offset + i]);
      }

   }

   public IntOpenHashSet(int[] a, int offset, int length) {
      this(a, offset, length, 0.75F);
   }

   public IntOpenHashSet(int[] a, float f) {
      this(a, 0, a.length, f);
   }

   public IntOpenHashSet(int[] a) {
      this(a, 0.75F);
   }

   private int realSize() {
      return this.containsNull ? this.size - 1 : this.size;
   }

   private void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.field_76);
      if (needed > this.field_75) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)capacity / this.field_76))));
      if (needed > this.field_75) {
         this.rehash(needed);
      }

   }

   public boolean addAll(IntCollection c) {
      if ((double)this.field_76 <= 0.5D) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity(this.size() + c.size());
      }

      return super.addAll(c);
   }

   public boolean addAll(Collection c) {
      if ((double)this.field_76 <= 0.5D) {
         this.ensureCapacity(c.size());
      } else {
         this.tryCapacity(this.size() + c.size());
      }

      return super.addAll(c);
   }

   public boolean add(int k) {
      if (k == 0) {
         if (this.containsNull) {
            return false;
         }

         this.containsNull = true;
      } else {
         int[] key = this.key;
         int pos;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               return false;
            }

            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
                  return false;
               }
            }
         }

         key[pos] = k;
      }

      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_76));
      }

      return true;
   }

   protected final void shiftKeys(int pos) {
      int[] key = this.key;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         int curr;
         while(true) {
            if ((curr = key[pos]) == 0) {
               key[last] = 0;
               return;
            }

            int slot = HashCommon.mix(curr) & this.mask;
            if (last <= pos) {
               if (last >= slot || slot > pos) {
                  break;
               }
            } else if (last >= slot && slot > pos) {
               break;
            }

            pos = pos + 1 & this.mask;
         }

         key[last] = curr;
      }
   }

   private boolean removeEntry(int pos) {
      --this.size;
      this.shiftKeys(pos);
      if (this.field_75 > this.minN && this.size < this.maxFill / 4 && this.field_75 > 16) {
         this.rehash(this.field_75 / 2);
      }

      return true;
   }

   private boolean removeNullEntry() {
      this.containsNull = false;
      this.key[this.field_75] = 0;
      --this.size;
      if (this.field_75 > this.minN && this.size < this.maxFill / 4 && this.field_75 > 16) {
         this.rehash(this.field_75 / 2);
      }

      return true;
   }

   public boolean remove(int k) {
      if (k == 0) {
         return this.containsNull && this.removeNullEntry();
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return false;
         }
      }
   }

   public boolean contains(int k) {
      if (k == 0) {
         return this.containsNull;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr) {
            return true;
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNull = false;
         Arrays.fill(this.key, 0);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public IntIterator iterator() {
      return new IntOpenHashSet.SetIterator((1)null)
   }

   public boolean trim() {
      return this.trim(this.size);
   }

   public boolean trim(int n) {
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.field_76));
      if (l < this.field_75 && this.size <= HashCommon.maxFill(l, this.field_76)) {
         try {
            this.rehash(l);
            return true;
         } catch (OutOfMemoryError var4) {
            return false;
         }
      } else {
         return true;
      }
   }

   protected void rehash(int newN) {
      int[] key = this.key;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      int i = this.field_75;

      int pos;
      for(int var7 = this.realSize(); var7-- != 0; newKey[pos] = key[i]) {
         do {
            --i;
         } while(key[i] == 0);

         if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) {
            while(newKey[pos = pos + 1 & mask] != 0) {
            }
         }
      }

      this.field_75 = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.field_75, this.field_76);
      this.key = newKey;
   }

   public IntOpenHashSet clone() {
      IntOpenHashSet c;
      try {
         c = (IntOpenHashSet)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.key = this.key.clone();
      c.containsNull = this.containsNull;
      return c;
   }

   public int hashCode() {
      int h = 0;
      int j = this.realSize();

      for(int i = 0; j-- != 0; ++i) {
         while(this.key[i] == 0) {
            ++i;
         }

         h += this.key[i];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      IntIterator i = this.iterator();
      s.defaultWriteObject();
      int var3 = this.size;

      while(var3-- != 0) {
         s.writeInt(i.nextInt());
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_75 = HashCommon.arraySize(this.size, this.field_76);
      this.maxFill = HashCommon.maxFill(this.field_75, this.field_76);
      this.mask = this.field_75 - 1;
      int[] key = this.key = new int[this.field_75 + 1];

      int k;
      int pos;
      for(int var4 = this.size; var4-- != 0; key[pos] = k) {
         k = s.readInt();
         if (k == 0) {
            pos = this.field_75;
            this.containsNull = true;
         } else if (key[pos = HashCommon.mix(k) & this.mask] != 0) {
            while(key[pos = pos + 1 & this.mask] != 0) {
            }
         }
      }

   }

   private void checkTable() {
   }

   private class SetIterator implements IntIterator {
      int pos;
      int last;
      // $FF: renamed from: c int
      int field_388;
      boolean mustReturnNull;
      IntArrayList wrapped;

      private SetIterator() {
         this.pos = IntOpenHashSet.this.field_75;
         this.last = -1;
         this.field_388 = IntOpenHashSet.this.size;
         this.mustReturnNull = IntOpenHashSet.this.containsNull;
      }

      public boolean hasNext() {
         return this.field_388 != 0;
      }

      public int nextInt() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.field_388;
            if (this.mustReturnNull) {
               this.mustReturnNull = false;
               this.last = IntOpenHashSet.this.field_75;
               return IntOpenHashSet.this.key[IntOpenHashSet.this.field_75];
            } else {
               int[] key = IntOpenHashSet.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != 0) {
                     return key[this.last = this.pos];
                  }
               }

               this.last = Integer.MIN_VALUE;
               return this.wrapped.getInt(-this.pos - 1);
            }
         }
      }

      private final void shiftKeys(int pos) {
         int[] key = IntOpenHashSet.this.key;

         while(true) {
            int last = pos;
            pos = pos + 1 & IntOpenHashSet.this.mask;

            int curr;
            while(true) {
               if ((curr = key[pos]) == 0) {
                  key[last] = 0;
                  return;
               }

               int slot = HashCommon.mix(curr) & IntOpenHashSet.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & IntOpenHashSet.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new IntArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == IntOpenHashSet.this.field_75) {
               IntOpenHashSet.this.containsNull = false;
               IntOpenHashSet.this.key[IntOpenHashSet.this.field_75] = 0;
            } else {
               if (this.pos < 0) {
                  IntOpenHashSet.this.remove(this.wrapped.getInt(-this.pos - 1));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --IntOpenHashSet.this.size;
            this.last = -1;
         }
      }

      // $FF: synthetic method
      SetIterator(1 x1) {
         this();
      }
   }
}
