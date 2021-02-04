package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
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

public class Int2ObjectOpenHashMap extends AbstractInt2ObjectMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient int[] key;
   protected transient Object[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   // $FF: renamed from: n int
   protected transient int field_38;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   // $FF: renamed from: f float
   protected final float field_39;
   protected transient Int2ObjectMap.FastEntrySet entries;
   protected transient IntSet keys;
   protected transient ObjectCollection values;

   public Int2ObjectOpenHashMap(int expected, float f) {
      if (!(f <= 0.0F) && !(f > 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.field_39 = f;
            this.minN = this.field_38 = HashCommon.arraySize(expected, f);
            this.mask = this.field_38 - 1;
            this.maxFill = HashCommon.maxFill(this.field_38, f);
            this.key = new int[this.field_38 + 1];
            this.value = new Object[this.field_38 + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
      }
   }

   public Int2ObjectOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Int2ObjectOpenHashMap() {
      this(16, 0.75F);
   }

   public Int2ObjectOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2ObjectOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Int2ObjectOpenHashMap(Int2ObjectMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Int2ObjectOpenHashMap(Int2ObjectMap m) {
      this(m, 0.75F);
   }

   public Int2ObjectOpenHashMap(int[] k, Object[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Int2ObjectOpenHashMap(int[] k, Object[] v) {
      this(k, v, 0.75F);
   }

   private int realSize() {
      return this.containsNullKey ? this.size - 1 : this.size;
   }

   private void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.field_39);
      if (needed > this.field_38) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)capacity / this.field_39))));
      if (needed > this.field_38) {
         this.rehash(needed);
      }

   }

   private Object removeEntry(int pos) {
      Object oldValue = this.value[pos];
      this.value[pos] = null;
      --this.size;
      this.shiftKeys(pos);
      if (this.field_38 > this.minN && this.size < this.maxFill / 4 && this.field_38 > 16) {
         this.rehash(this.field_38 / 2);
      }

      return oldValue;
   }

   private Object removeNullEntry() {
      this.containsNullKey = false;
      Object oldValue = this.value[this.field_38];
      this.value[this.field_38] = null;
      --this.size;
      if (this.field_38 > this.minN && this.size < this.maxFill / 4 && this.field_38 > 16) {
         this.rehash(this.field_38 / 2);
      }

      return oldValue;
   }

   public void putAll(Map m) {
      if ((double)this.field_39 <= 0.5D) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity(this.size() + m.size());
      }

      super.putAll(m);
   }

   private int find(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.field_38 : -(this.field_38 + 1);
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

   private void insert(int pos, int k, Object v) {
      if (pos == this.field_38) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_39));
      }

   }

   public Object put(int k, Object v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      } else {
         Object oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
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
               this.value[last] = null;
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

   public Object remove(int k) {
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

   public Object get(int k) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.field_38] : this.defRetValue;
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

   public boolean containsValue(Object v) {
      Object[] value = this.value;
      int[] key = this.key;
      if (this.containsNullKey && Objects.equals(value[this.field_38], v)) {
         return true;
      } else {
         int i = this.field_38;

         do {
            if (i-- == 0) {
               return false;
            }
         } while(key[i] == 0 || !Objects.equals(value[i], v));

         return true;
      }
   }

   public Object getOrDefault(int k, Object defaultValue) {
      if (k == 0) {
         return this.containsNullKey ? this.value[this.field_38] : defaultValue;
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

   public Object putIfAbsent(int k, Object v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   public boolean remove(int k, Object v) {
      if (k == 0) {
         if (this.containsNullKey && Objects.equals(v, this.value[this.field_38])) {
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
         } else if (k == curr && Objects.equals(v, this.value[pos])) {
            this.removeEntry(pos);
            return true;
         } else {
            do {
               if ((curr = key[pos = pos + 1 & this.mask]) == 0) {
                  return false;
               }
            } while(k != curr || !Objects.equals(v, this.value[pos]));

            this.removeEntry(pos);
            return true;
         }
      }
   }

   public boolean replace(int k, Object oldValue, Object v) {
      int pos = this.find(k);
      if (pos >= 0 && Objects.equals(oldValue, this.value[pos])) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   public Object replace(int k, Object v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Object oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   public Object computeIfAbsent(int k, IntFunction mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         Object newValue = mappingFunction.apply(k);
         this.insert(-pos - 1, k, newValue);
         return newValue;
      }
   }

   public Object computeIfPresent(int k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Object newValue = remappingFunction.apply(k, this.value[pos]);
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

   public Object compute(int k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Object newValue = remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == 0) {
               this.removeNullEntry();
            } else {
               this.removeEntry(pos);
            }
         }

         return this.defRetValue;
      } else if (pos < 0) {
         this.insert(-pos - 1, k, newValue);
         return newValue;
      } else {
         return this.value[pos] = newValue;
      }
   }

   public Object merge(int k, Object v, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos >= 0 && this.value[pos] != null) {
         Object newValue = remappingFunction.apply(this.value[pos], v);
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
      } else if (v == null) {
         return this.defRetValue;
      } else {
         this.insert(-pos - 1, k, v);
         return v;
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         this.containsNullKey = false;
         Arrays.fill(this.key, 0);
         Arrays.fill(this.value, null);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Int2ObjectMap.FastEntrySet int2ObjectEntrySet() {
      if (this.entries == null) {
         this.entries = new Int2ObjectOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   public IntSet keySet() {
      if (this.keys == null) {
         this.keys = new Int2ObjectOpenHashMap.KeySet();
      }

      return this.keys;
   }

   public ObjectCollection values() {
      if (this.values == null) {
         this.values = new AbstractObjectCollection() {
            public ObjectIterator iterator() {
               return Int2ObjectOpenHashMap.this.new ValueIterator();
            }

            public int size() {
               return Int2ObjectOpenHashMap.this.size;
            }

            public boolean contains(Object v) {
               return Int2ObjectOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Int2ObjectOpenHashMap.this.clear();
            }

            public void forEach(Consumer consumer) {
               if (Int2ObjectOpenHashMap.this.containsNullKey) {
                  consumer.accept(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38]);
               }

               int pos = Int2ObjectOpenHashMap.this.field_38;

               while(pos-- != 0) {
                  if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
                     consumer.accept(Int2ObjectOpenHashMap.this.value[pos]);
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
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((float)n / this.field_39));
      if (l < this.field_38 && this.size <= HashCommon.maxFill(l, this.field_39)) {
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
      Object[] value = this.value;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      Object[] newValue = new Object[newN + 1];
      int i = this.field_38;

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

      newValue[newN] = value[this.field_38];
      this.field_38 = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.field_38, this.field_39);
      this.key = newKey;
      this.value = newValue;
   }

   public Int2ObjectOpenHashMap clone() {
      Int2ObjectOpenHashMap c;
      try {
         c = (Int2ObjectOpenHashMap)super.clone();
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
         if (this != this.value[i]) {
            t ^= this.value[i] == null ? 0 : this.value[i].hashCode();
         }

         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.field_38] == null ? 0 : this.value[this.field_38].hashCode();
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      int[] key = this.key;
      Object[] value = this.value;
      Int2ObjectOpenHashMap.MapIterator i = new Int2ObjectOpenHashMap.MapIterator();
      s.defaultWriteObject();
      int var5 = this.size;

      while(var5-- != 0) {
         int e = i.nextEntry();
         s.writeInt(key[e]);
         s.writeObject(value[e]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_38 = HashCommon.arraySize(this.size, this.field_39);
      this.maxFill = HashCommon.maxFill(this.field_38, this.field_39);
      this.mask = this.field_38 - 1;
      int[] key = this.key = new int[this.field_38 + 1];
      Object[] value = this.value = new Object[this.field_38 + 1];

      Object v;
      int pos;
      for(int var6 = this.size; var6-- != 0; value[pos] = v) {
         int k = s.readInt();
         v = s.readObject();
         if (k == 0) {
            pos = this.field_38;
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

   private final class MapEntrySet extends AbstractObjectSet implements Int2ObjectMap.FastEntrySet {
      private MapEntrySet() {
      }

      public ObjectIterator iterator() {
         return Int2ObjectOpenHashMap.this.new EntryIterator();
      }

      public ObjectIterator fastIterator() {
         return Int2ObjectOpenHashMap.this.new FastEntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            if (e.getKey() != null && e.getKey() instanceof Integer) {
               int k = (Integer)e.getKey();
               Object v = e.getValue();
               if (k == 0) {
                  return Int2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38], v);
               } else {
                  int[] key = Int2ObjectOpenHashMap.this.key;
                  int curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k) & Int2ObjectOpenHashMap.this.mask]) == 0) {
                     return false;
                  } else if (k == curr) {
                     return Objects.equals(Int2ObjectOpenHashMap.this.value[pos], v);
                  } else {
                     while((curr = key[pos = pos + 1 & Int2ObjectOpenHashMap.this.mask]) != 0) {
                        if (k == curr) {
                           return Objects.equals(Int2ObjectOpenHashMap.this.value[pos], v);
                        }
                     }

                     return false;
                  }
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
               int k = (Integer)e.getKey();
               Object v = e.getValue();
               if (k == 0) {
                  if (Int2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38], v)) {
                     Int2ObjectOpenHashMap.this.removeNullEntry();
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  int[] key = Int2ObjectOpenHashMap.this.key;
                  int curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k) & Int2ObjectOpenHashMap.this.mask]) == 0) {
                     return false;
                  } else if (curr == k) {
                     if (Objects.equals(Int2ObjectOpenHashMap.this.value[pos], v)) {
                        Int2ObjectOpenHashMap.this.removeEntry(pos);
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     do {
                        if ((curr = key[pos = pos + 1 & Int2ObjectOpenHashMap.this.mask]) == 0) {
                           return false;
                        }
                     } while(curr != k || !Objects.equals(Int2ObjectOpenHashMap.this.value[pos], v));

                     Int2ObjectOpenHashMap.this.removeEntry(pos);
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }

      public int size() {
         return Int2ObjectOpenHashMap.this.size;
      }

      public void clear() {
         Int2ObjectOpenHashMap.this.clear();
      }

      public void forEach(Consumer consumer) {
         if (Int2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.field_38], Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38]));
         }

         int pos = Int2ObjectOpenHashMap.this.field_38;

         while(pos-- != 0) {
            if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
               consumer.accept(new AbstractInt2ObjectMap.BasicEntry(Int2ObjectOpenHashMap.this.key[pos], Int2ObjectOpenHashMap.this.value[pos]));
            }
         }

      }

      public void fastForEach(Consumer consumer) {
         AbstractInt2ObjectMap.BasicEntry entry = new AbstractInt2ObjectMap.BasicEntry();
         if (Int2ObjectOpenHashMap.this.containsNullKey) {
            entry.key = Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.field_38];
            entry.value = Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38];
            consumer.accept(entry);
         }

         int pos = Int2ObjectOpenHashMap.this.field_38;

         while(pos-- != 0) {
            if (Int2ObjectOpenHashMap.this.key[pos] != 0) {
               entry.key = Int2ObjectOpenHashMap.this.key[pos];
               entry.value = Int2ObjectOpenHashMap.this.value[pos];
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
         return new com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap.KeyIterator(Int2ObjectOpenHashMap.this);
      }

      public void forEach(IntConsumer consumer) {
         if (Int2ObjectOpenHashMap.this.containsNullKey) {
            consumer.accept(Int2ObjectOpenHashMap.this.key[Int2ObjectOpenHashMap.this.field_38]);
         }

         int pos = Int2ObjectOpenHashMap.this.field_38;

         while(pos-- != 0) {
            int k = Int2ObjectOpenHashMap.this.key[pos];
            if (k != 0) {
               consumer.accept(k);
            }
         }

      }

      public int size() {
         return Int2ObjectOpenHashMap.this.size;
      }

      public boolean contains(int k) {
         return Int2ObjectOpenHashMap.this.containsKey(k);
      }

      public boolean remove(int k) {
         int oldSize = Int2ObjectOpenHashMap.this.size;
         Int2ObjectOpenHashMap.this.remove(k);
         return Int2ObjectOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Int2ObjectOpenHashMap.this.clear();
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
      int field_58;
      boolean mustReturnNullKey;
      IntArrayList wrapped;

      private MapIterator() {
         this.pos = Int2ObjectOpenHashMap.this.field_38;
         this.last = -1;
         this.field_58 = Int2ObjectOpenHashMap.this.size;
         this.mustReturnNullKey = Int2ObjectOpenHashMap.this.containsNullKey;
      }

      public boolean hasNext() {
         return this.field_58 != 0;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.field_58;
            if (this.mustReturnNullKey) {
               this.mustReturnNullKey = false;
               return this.last = Int2ObjectOpenHashMap.this.field_38;
            } else {
               int[] key = Int2ObjectOpenHashMap.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != 0) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               int k = this.wrapped.getInt(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(k) & Int2ObjectOpenHashMap.this.mask; k != key[p]; p = p + 1 & Int2ObjectOpenHashMap.this.mask) {
               }

               return p;
            }
         }
      }

      private void shiftKeys(int pos) {
         int[] key = Int2ObjectOpenHashMap.this.key;

         while(true) {
            int last = pos;
            pos = pos + 1 & Int2ObjectOpenHashMap.this.mask;

            int curr;
            while(true) {
               if ((curr = key[pos]) == 0) {
                  key[last] = 0;
                  Int2ObjectOpenHashMap.this.value[last] = null;
                  return;
               }

               int slot = HashCommon.mix(curr) & Int2ObjectOpenHashMap.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & Int2ObjectOpenHashMap.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new IntArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
            Int2ObjectOpenHashMap.this.value[last] = Int2ObjectOpenHashMap.this.value[pos];
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Int2ObjectOpenHashMap.this.field_38) {
               Int2ObjectOpenHashMap.this.containsNullKey = false;
               Int2ObjectOpenHashMap.this.value[Int2ObjectOpenHashMap.this.field_38] = null;
            } else {
               if (this.pos < 0) {
                  Int2ObjectOpenHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --Int2ObjectOpenHashMap.this.size;
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

   private final class ValueIterator extends Int2ObjectOpenHashMap.MapIterator implements ObjectIterator {
      public ValueIterator() {
         super(null);
      }

      public Object next() {
         return Int2ObjectOpenHashMap.this.value[this.nextEntry()];
      }
   }

   private class FastEntryIterator extends Int2ObjectOpenHashMap.MapIterator implements ObjectIterator {
      private final Int2ObjectOpenHashMap.MapEntry entry;

      private FastEntryIterator() {
         super(null);
         this.entry = Int2ObjectOpenHashMap.this.new MapEntry();
      }

      public Int2ObjectOpenHashMap.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      // $FF: synthetic method
      FastEntryIterator(Object x1) {
         this();
      }
   }

   private class EntryIterator extends Int2ObjectOpenHashMap.MapIterator implements ObjectIterator {
      private Int2ObjectOpenHashMap.MapEntry entry;

      private EntryIterator() {
         super(null);
      }

      public Int2ObjectOpenHashMap.MapEntry next() {
         return this.entry = Int2ObjectOpenHashMap.this.new MapEntry(this.nextEntry());
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

   final class MapEntry implements Int2ObjectMap.Entry, java.util.Map.Entry {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      public int getIntKey() {
         return Int2ObjectOpenHashMap.this.key[this.index];
      }

      public Object getValue() {
         return Int2ObjectOpenHashMap.this.value[this.index];
      }

      public Object setValue(Object v) {
         Object oldValue = Int2ObjectOpenHashMap.this.value[this.index];
         Int2ObjectOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      /** @deprecated */
      @Deprecated
      public Integer getKey() {
         return Int2ObjectOpenHashMap.this.key[this.index];
      }

      public boolean equals(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            return Int2ObjectOpenHashMap.this.key[this.index] == (Integer)e.getKey() && Objects.equals(Int2ObjectOpenHashMap.this.value[this.index], e.getValue());
         }
      }

      public int hashCode() {
         return Int2ObjectOpenHashMap.this.key[this.index] ^ (Int2ObjectOpenHashMap.this.value[this.index] == null ? 0 : Int2ObjectOpenHashMap.this.value[this.index].hashCode());
      }

      public String toString() {
         return Int2ObjectOpenHashMap.this.key[this.index] + "=>" + Int2ObjectOpenHashMap.this.value[this.index];
      }
   }
}
