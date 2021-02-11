package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public class Int2IntOpenHashMap extends AbstractInt2IntMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient int[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   // $FF: renamed from: n int
   protected transient int field_41;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   // $FF: renamed from: f float
   protected final float field_42;
   protected transient Int2IntMap.FastEntrySet entries;
   protected transient IntSet keys;
   protected transient IntCollection values;

   public Int2IntOpenHashMap(int expected, float f) {
      if (!(f <= 0.0F) && !(f > 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.field_42 = f;
            this.minN = this.field_41 = HashCommon.arraySize(expected, f);
            this.mask = this.field_41 - 1;
            this.maxFill = HashCommon.maxFill(this.field_41, f);
            this.key = new int[this.field_41 + 1];
            this.value = new int[this.field_41 + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
      }
   }

   public Int2IntOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Int2IntOpenHashMap() {
      this(16, 0.75F);
   }

   public Int2IntOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2IntOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Int2IntOpenHashMap(Int2IntMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2IntOpenHashMap(Int2IntMap m) {
      this(m, 0.75F);
   }

   public Int2IntOpenHashMap(int[] k, int[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Int2IntOpenHashMap(int[] k, int[] v) {
      this(k, v, 0.75F);
   }

   private int realSize() {
      return this.containsNullKey ? this.size - 1 : this.size;
   }

   private void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.field_42);
      if (needed > this.field_41) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)capacity / this.field_42))));
      if (needed > this.field_41) {
         this.rehash(needed);
      }

   }

   private int removeEntry(int pos) {
      int oldValue = this.value[pos];
      --this.size;
      this.shiftKeys(pos);
      if (this.field_41 > this.minN && this.size < this.maxFill / 4 && this.field_41 > 16) {
         this.rehash(this.field_41 / 2);
      }

      return oldValue;
   }

   private int removeNullEntry() {
      this.containsNullKey = false;
      int oldValue = this.value[this.field_41];
      --this.size;
      if (this.field_41 > this.minN && this.size < this.maxFill / 4 && this.field_41 > 16) {
         this.rehash(this.field_41 / 2);
      }

      return oldValue;
   }

   public void putAll(Map m) {
      if ((double)this.field_42 <= 0.5D) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity(this.size() + m.size());
      }

      super.putAll(m);
   }

   private int find(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.field_41 : -(this.field_41 + 1);
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return -(pos + 1);
         } else if (k == curr) {
            return pos;
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, int k, int v) {
      if (pos == this.field_41) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_42));
      }

   }

   public int put(int k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   private int addToValue(int pos, int incr) {
      int oldValue = this.value[pos];
      this.value[pos] = oldValue + incr;
      return oldValue;
   }

   public int addTo(int k, int incr) {
      int pos;
      if (k == 0) {
         if (this.containsNullKey) {
            return this.addToValue(this.field_41, incr);
         }

         pos = this.field_41;
         this.containsNullKey = true;
      } else {
         int[] key = this.key;
         int curr;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
            if (curr == k) {
               return this.addToValue(pos, incr);
            }

            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (curr == k) {
                  return this.addToValue(pos, incr);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = this.defRetValue + incr;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_42));
      }

      return this.defRetValue;
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
         this.value[last] = this.value[pos];
      }
   }

   public int remove(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.removeEntry(pos);
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   public int get(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.field_41] : this.defRetValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return this.defRetValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public boolean containsKey(int k) {
      if (k == 0) {
         return this.containsNullKey;
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

   public boolean containsValue(int v) {
      int[] value = this.value;
      int[] key = this.key;
      if (this.containsNullKey && value[this.field_41] == v) {
         return true;
      } else {
         int i = this.field_41;

         do {
            if (i-- == 0) {
               return false;
            }
         } while(key[i] == 0 || value[i] != v);

         return true;
      }
   }

   public int getOrDefault(int k, int defaultValue) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.field_41] : defaultValue;
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return defaultValue;
         } else if (k == curr) {
            return this.value[pos];
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   public int putIfAbsent(int k, int v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   public boolean remove(int k, int v) {
      if (k == 0) {
         if (this.containsNullKey && v == this.value[this.field_41]) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         int[] key = this.key;
         int curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) {
            return false;
         } else if (k == curr && v == this.value[pos]) {
            this.removeEntry(pos);
            return true;
         } else {
            do {
               if ((curr = key[pos = pos + 1 & this.mask]) == 0) {
                  return false;
               }
            } while(k != curr || v != this.value[pos]);

            this.removeEntry(pos);
            return true;
         }
      }
   }

   public boolean replace(int k, int oldValue, int v) {
      int pos = this.find(k);
      if (pos >= 0 && oldValue == this.value[pos]) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   public int replace(int k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   public int computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         int newValue = mappingFunction.applyAsInt(k);
         this.insert(-pos - 1, k, newValue);
         return newValue;
      }
   }

   public int computeIfAbsentNullable(int k, IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         Integer newValue = (Integer)mappingFunction.apply(k);
         if (newValue == null) {
            return this.defRetValue;
         } else {
            int v = newValue;
            this.insert(-pos - 1, k, v);
            return v;
         }
      }
   }

   public int computeIfPresent(int k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(k, this.value[pos]);
         if (newValue == null) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   public int compute(int k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Integer newValue = (Integer)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else {
         int newVal = newValue;
         if (pos < 0) {
            this.insert(-pos - 1, k, newVal);
            return newVal;
         } else {
            return this.value[pos] = newVal;
         }
      }
   }

   public int merge(int k, int v, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return v;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(this.value[pos], v);
         if (newValue == null) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }

            return this.defRetValue;
         } else {
            return this.value[pos] = newValue;
         }
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
         Arrays.fill(this.key, 0);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Int2IntMap.FastEntrySet int2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new Int2IntOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   public IntSet keySet() {
      if (this.keys == null) {
         this.keys = new Int2IntOpenHashMap.KeySet();
      }

      return this.keys;
   }

   public IntCollection values() {
      if (this.values == null) {
         this.values = new AbstractIntCollection() {
            public IntIterator iterator() {
               return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.ValueIterator(Int2IntOpenHashMap.this);
            }

            public int size() {
               return Int2IntOpenHashMap.this.size;
            }

            public boolean contains(int v) {
               return Int2IntOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Int2IntOpenHashMap.this.clear();
            }

            public void forEach(IntConsumer consumer) {
               if (Int2IntOpenHashMap.this.containsNullKey) {
                  consumer.accept(Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.field_41]);
               }

               int pos = Int2IntOpenHashMap.this.field_41;

               while(pos-- != 0) {
                  if (Int2IntOpenHashMap.this.key[pos] != 0) {
                     consumer.accept(Int2IntOpenHashMap.this.value[pos]);
                  }
               }

            }
         };
      }

      return this.values;
   }

   public boolean trim() {
      return this.trim(this.size);
   }

   public boolean trim(int n) {
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.field_42));
      if (l < this.field_41 && this.size <= HashCommon.maxFill(l, this.field_42)) {
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
      int[] value = this.value;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      int[] newValue = new int[newN + 1];
      int i = this.field_41;

      int pos;
      for(int var9 = this.realSize(); var9-- != 0; newValue[pos] = value[i]) {
         do {
            --i;
         } while(key[i] == 0);

         if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) {
            while(newKey[pos = pos + 1 & mask] != 0) {
            }
         }

         newKey[pos] = key[i];
      }

      newValue[newN] = value[this.field_41];
      this.field_41 = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.field_41, this.field_42);
      this.key = newKey;
      this.value = newValue;
   }

   public Int2IntOpenHashMap clone() {
      Int2IntOpenHashMap c;
      try {
         c = (Int2IntOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = this.key.clone();
      c.value = this.value.clone();
      return c;
   }

   public int hashCode() {
      int h = 0;
      int j = this.realSize();
      int i = 0;

      for(boolean var4 = false; j-- != 0; ++i) {
         while(this.key[i] == 0) {
            ++i;
         }

         int t = this.key[i];
         t ^= this.value[i];
         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.field_41];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      int[] key = this.key;
      int[] value = this.value;
      Int2IntOpenHashMap.MapIterator i = new Int2IntOpenHashMap.MapIterator();
      s.defaultWriteObject();
      int var5 = this.size;

      while(var5-- != 0) {
         int e = i.nextEntry();
         s.writeInt(key[e]);
         s.writeInt(value[e]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_41 = HashCommon.arraySize(this.size, this.field_42);
      this.maxFill = HashCommon.maxFill(this.field_41, this.field_42);
      this.mask = this.field_41 - 1;
      int[] key = this.key = new int[this.field_41 + 1];
      int[] value = this.value = new int[this.field_41 + 1];

      int v;
      int pos;
      for(int var6 = this.size; var6-- != 0; value[pos] = v) {
         int k = s.readInt();
         v = s.readInt();
         if (k == 0) {
            pos = this.field_41;
            this.containsNullKey = true;
         } else {
            for(pos = HashCommon.mix(k) & this.mask; key[pos] != 0; pos = pos + 1 & this.mask) {
            }
         }

         key[pos] = k;
      }

   }

   private void checkTable() {
   }

   private final class MapEntrySet extends AbstractObjectSet implements Int2IntMap.FastEntrySet {
      private MapEntrySet() {
      }

      public ObjectIterator iterator() {
         return Int2IntOpenHashMap.this.new EntryIterator();
      }

      public ObjectIterator fastIterator() {
         return Int2IntOpenHashMap.this.new FastEntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               if (e.getValue() != null && e.getValue() instanceof Integer) {
                  int k = (Integer)e.getKey();
                  int v = (Integer)e.getValue();
                  if (k == 0) {
                     return Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.field_41] == v;
                  } else {
                     int[] key = Int2IntOpenHashMap.this.key;
                     int curr;
                     int pos;
                     if ((curr = key[pos = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask]) == 0) {
                        return false;
                     } else if (k == curr) {
                        return Int2IntOpenHashMap.this.value[pos] == v;
                     } else {
                        while((curr = key[pos = pos + 1 & Int2IntOpenHashMap.this.mask]) != 0) {
                           if (k == curr) {
                              return Int2IntOpenHashMap.this.value[pos] == v;
                           }
                        }

                        return false;
                     }
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               if (e.getValue() != null && e.getValue() instanceof Integer) {
                  int k = (Integer)e.getKey();
                  int v = (Integer)e.getValue();
                  if (k == 0) {
                     if (Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.field_41] == v) {
                        Int2IntOpenHashMap.this.removeNullEntry();
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     int[] key = Int2IntOpenHashMap.this.key;
                     int curr;
                     int pos;
                     if ((curr = key[pos = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask]) == 0) {
                        return false;
                     } else if (curr == k) {
                        if (Int2IntOpenHashMap.this.value[pos] == v) {
                           Int2IntOpenHashMap.this.removeEntry(pos);
                           return true;
                        } else {
                           return false;
                        }
                     } else {
                        do {
                           if ((curr = key[pos = pos + 1 & Int2IntOpenHashMap.this.mask]) == 0) {
                              return false;
                           }
                        } while(curr != k || Int2IntOpenHashMap.this.value[pos] != v);

                        Int2IntOpenHashMap.this.removeEntry(pos);
                        return true;
                     }
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public int size() {
         return Int2IntOpenHashMap.this.size;
      }

      public void clear() {
         Int2IntOpenHashMap.this.clear();
      }

      public void forEach(Consumer consumer) {
         if (Int2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.field_41], Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.field_41]));
         }

         int pos = Int2IntOpenHashMap.this.field_41;

         while(pos-- != 0) {
            if (Int2IntOpenHashMap.this.key[pos] != 0) {
               consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[pos], Int2IntOpenHashMap.this.value[pos]));
            }
         }

      }

      public void fastForEach(Consumer consumer) {
         AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
         if (Int2IntOpenHashMap.this.containsNullKey) {
            entry.key = Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.field_41];
            entry.value = Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.field_41];
            consumer.accept(entry);
         }

         int pos = Int2IntOpenHashMap.this.field_41;

         while(pos-- != 0) {
            if (Int2IntOpenHashMap.this.key[pos] != 0) {
               entry.key = Int2IntOpenHashMap.this.key[pos];
               entry.value = Int2IntOpenHashMap.this.value[pos];
               consumer.accept(entry);
            }
         }

      }

      // $FF: synthetic method
      MapEntrySet(Object x1) {
         this();
      }
   }

   private final class KeySet extends AbstractIntSet {
      private KeySet() {
      }

      public IntIterator iterator() {
         return new com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap.KeyIterator(Int2IntOpenHashMap.this);
      }

      public void forEach(IntConsumer consumer) {
         if (Int2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.field_41]);
         }

         int pos = Int2IntOpenHashMap.this.field_41;

         while(pos-- != 0) {
            int k = Int2IntOpenHashMap.this.key[pos];
            if (k != 0) {
               consumer.accept(k);
            }
         }

      }

      public int size() {
         return Int2IntOpenHashMap.this.size;
      }

      public boolean contains(int k) {
         return Int2IntOpenHashMap.this.containsKey(k);
      }

      public boolean remove(int k) {
         int oldSize = Int2IntOpenHashMap.this.size;
         Int2IntOpenHashMap.this.remove(k);
         return Int2IntOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Int2IntOpenHashMap.this.clear();
      }

      // $FF: synthetic method
      KeySet(Object x1) {
         this();
      }
   }

   private class MapIterator {
      int pos;
      int last;
      // $FF: renamed from: c int
      int field_115;
      boolean mustReturnNullKey;
      IntArrayList wrapped;

      private MapIterator() {
         this.pos = Int2IntOpenHashMap.this.field_41;
         this.last = -1;
         this.field_115 = Int2IntOpenHashMap.this.size;
         this.mustReturnNullKey = Int2IntOpenHashMap.this.containsNullKey;
      }

      public boolean hasNext() {
         return this.field_115 != 0;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.field_115;
            if (this.mustReturnNullKey) {
               this.mustReturnNullKey = false;
               return this.last = Int2IntOpenHashMap.this.field_41;
            } else {
               int[] key = Int2IntOpenHashMap.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != 0) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               int k = this.wrapped.getInt(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask; k != key[p]; p = p + 1 & Int2IntOpenHashMap.this.mask) {
               }

               return p;
            }
         }
      }

      private void shiftKeys(int pos) {
         int[] key = Int2IntOpenHashMap.this.key;

         while(true) {
            int last = pos;
            pos = pos + 1 & Int2IntOpenHashMap.this.mask;

            int curr;
            while(true) {
               if ((curr = key[pos]) == 0) {
                  key[last] = 0;
                  return;
               }

               int slot = HashCommon.mix(curr) & Int2IntOpenHashMap.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & Int2IntOpenHashMap.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new IntArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
            Int2IntOpenHashMap.this.value[last] = Int2IntOpenHashMap.this.value[pos];
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Int2IntOpenHashMap.this.field_41) {
               Int2IntOpenHashMap.this.containsNullKey = false;
            } else {
               if (this.pos < 0) {
                  Int2IntOpenHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --Int2IntOpenHashMap.this.size;
            this.last = -1;
         }
      }

      public int skip(int n) {
         int i = n;

         while(i-- != 0 && this.hasNext()) {
            this.nextEntry();
         }

         return n - i - 1;
      }

      // $FF: synthetic method
      MapIterator(Object x1) {
         this();
      }
   }

   private class FastEntryIterator extends Int2IntOpenHashMap.MapIterator implements ObjectIterator {
      private final Int2IntOpenHashMap.MapEntry entry;

      private FastEntryIterator() {
         super(null);
         this.entry = Int2IntOpenHashMap.this.new MapEntry();
      }

      public Int2IntOpenHashMap.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      // $FF: synthetic method
      FastEntryIterator(Object x1) {
         this();
      }
   }

   private class EntryIterator extends Int2IntOpenHashMap.MapIterator implements ObjectIterator {
      private Int2IntOpenHashMap.MapEntry entry;

      private EntryIterator() {
         super(null);
      }

      public Int2IntOpenHashMap.MapEntry next() {
         return this.entry = Int2IntOpenHashMap.this.new MapEntry(this.nextEntry());
      }

      public void remove() {
         super.remove();
         this.entry.index = -1;
      }

      // $FF: synthetic method
      EntryIterator(Object x1) {
         this();
      }
   }

   final class MapEntry implements Int2IntMap.Entry, java.util.Map.Entry {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      public int getIntKey() {
         return Int2IntOpenHashMap.this.key[this.index];
      }

      public int getIntValue() {
         return Int2IntOpenHashMap.this.value[this.index];
      }

      public int setValue(int v) {
         int oldValue = Int2IntOpenHashMap.this.value[this.index];
         Int2IntOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      /** @deprecated */
      @Deprecated
      public Integer getKey() {
         return Int2IntOpenHashMap.this.key[this.index];
      }

      /** @deprecated */
      @Deprecated
      public Integer getValue() {
         return Int2IntOpenHashMap.this.value[this.index];
      }

      /** @deprecated */
      @Deprecated
      public Integer setValue(Integer v) {
         return this.setValue(v);
      }

      public boolean equals(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            return Int2IntOpenHashMap.this.key[this.index] == (Integer)e.getKey() && Int2IntOpenHashMap.this.value[this.index] == (Integer)e.getValue();
         }
      }

      public int hashCode() {
         return Int2IntOpenHashMap.this.key[this.index] ^ Int2IntOpenHashMap.this.value[this.index];
      }

      public String toString() {
         return Int2IntOpenHashMap.this.key[this.index] + "=>" + Int2IntOpenHashMap.this.value[this.index];
      }
   }
}
