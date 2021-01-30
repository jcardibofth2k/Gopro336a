package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.HashCommon;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
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
import java.util.function.ToIntFunction;

public class Object2IntOpenHashMap extends AbstractObject2IntMap implements Serializable, Cloneable, Hash {
   private static final long serialVersionUID = 0L;
   private static final boolean ASSERTS = false;
   protected transient Object[] key;
   protected transient int[] value;
   protected transient int mask;
   protected transient boolean containsNullKey;
   // $FF: renamed from: n int
   protected transient int field_140;
   protected transient int maxFill;
   protected final transient int minN;
   protected int size;
   // $FF: renamed from: f float
   protected final float field_141;
   protected transient Object2IntMap.FastEntrySet entries;
   protected transient ObjectSet keys;
   protected transient IntCollection values;

   public Object2IntOpenHashMap(int expected, float f) {
      if (!(f <= 0.0F) && !(f > 1.0F)) {
         if (expected < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
         } else {
            this.field_141 = f;
            this.minN = this.field_140 = HashCommon.arraySize(expected, f);
            this.mask = this.field_140 - 1;
            this.maxFill = HashCommon.maxFill(this.field_140, f);
            this.key = new Object[this.field_140 + 1];
            this.value = new int[this.field_140 + 1];
         }
      } else {
         throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
      }
   }

   public Object2IntOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public Object2IntOpenHashMap() {
      this(16, 0.75F);
   }

   public Object2IntOpenHashMap(Map m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Map m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(Object2IntMap m, float f) {
      this(m.size(), f);
      this.putAll(m);
   }

   public Object2IntOpenHashMap(Object2IntMap m) {
      this(m, 0.75F);
   }

   public Object2IntOpenHashMap(Object[] k, int[] v, float f) {
      this(k.length, f);
      if (k.length != v.length) {
         throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
      } else {
         for(int i = 0; i < k.length; ++i) {
            this.put(k[i], v[i]);
         }

      }
   }

   public Object2IntOpenHashMap(Object[] k, int[] v) {
      this(k, v, 0.75F);
   }

   private int realSize() {
      return this.containsNullKey ? this.size - 1 : this.size;
   }

   private void ensureCapacity(int capacity) {
      int needed = HashCommon.arraySize(capacity, this.field_141);
      if (needed > this.field_140) {
         this.rehash(needed);
      }

   }

   private void tryCapacity(long capacity) {
      int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((double)((float)capacity / this.field_141)))));
      if (needed > this.field_140) {
         this.rehash(needed);
      }

   }

   private int removeEntry(int pos) {
      int oldValue = this.value[pos];
      --this.size;
      this.shiftKeys(pos);
      if (this.field_140 > this.minN && this.size < this.maxFill / 4 && this.field_140 > 16) {
         this.rehash(this.field_140 / 2);
      }

      return oldValue;
   }

   private int removeNullEntry() {
      this.containsNullKey = false;
      this.key[this.field_140] = null;
      int oldValue = this.value[this.field_140];
      --this.size;
      if (this.field_140 > this.minN && this.size < this.maxFill / 4 && this.field_140 > 16) {
         this.rehash(this.field_140 / 2);
      }

      return oldValue;
   }

   public void putAll(Map m) {
      if ((double)this.field_141 <= 0.5D) {
         this.ensureCapacity(m.size());
      } else {
         this.tryCapacity((long)(this.size() + m.size()));
      }

      super.putAll(m);
   }

   private int find(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.field_140 : -(this.field_140 + 1);
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return -(pos + 1);
         } else if (k.equals(curr)) {
            return pos;
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return pos;
               }
            }

            return -(pos + 1);
         }
      }
   }

   private void insert(int pos, Object k, int v) {
      if (pos == this.field_140) {
         this.containsNullKey = true;
      }

      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_141));
      }

   }

   public int put(Object k, int v) {
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

   public int addTo(Object k, int incr) {
      int pos;
      if (k == null) {
         if (this.containsNullKey) {
            return this.addToValue(this.field_140, incr);
         }

         pos = this.field_140;
         this.containsNullKey = true;
      } else {
         Object[] key = this.key;
         Object curr;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
            if (curr.equals(k)) {
               return this.addToValue(pos, incr);
            }

            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (curr.equals(k)) {
                  return this.addToValue(pos, incr);
               }
            }
         }
      }

      this.key[pos] = k;
      this.value[pos] = this.defRetValue + incr;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashCommon.arraySize(this.size + 1, this.field_141));
      }

      return this.defRetValue;
   }

   protected final void shiftKeys(int pos) {
      Object[] key = this.key;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         Object curr;
         while(true) {
            if ((curr = key[pos]) == null) {
               key[last] = null;
               return;
            }

            int slot = HashCommon.mix(curr.hashCode()) & this.mask;
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

   public int removeInt(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.removeNullEntry() : this.defRetValue;
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.removeEntry(pos);
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.removeEntry(pos);
               }
            }

            return this.defRetValue;
         }
      }
   }

   public int getInt(Object k) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.field_140] : this.defRetValue;
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return this.defRetValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return this.defRetValue;
         }
      }
   }

   public boolean containsKey(Object k) {
      if (k == null) {
         return this.containsNullKey;
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr)) {
            return true;
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean containsValue(int v) {
      int[] value = this.value;
      Object[] key = this.key;
      if (this.containsNullKey && value[this.field_140] == v) {
         return true;
      } else {
         int i = this.field_140;

         do {
            if (i-- == 0) {
               return false;
            }
         } while(key[i] == null || value[i] != v);

         return true;
      }
   }

   public int getOrDefault(Object k, int defaultValue) {
      if (k == null) {
         return this.containsNullKey ? this.value[this.field_140] : defaultValue;
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return defaultValue;
         } else if (k.equals(curr)) {
            return this.value[pos];
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  return this.value[pos];
               }
            }

            return defaultValue;
         }
      }
   }

   public int putIfAbsent(Object k, int v) {
      int pos = this.find(k);
      if (pos >= 0) {
         return this.value[pos];
      } else {
         this.insert(-pos - 1, k, v);
         return this.defRetValue;
      }
   }

   public boolean remove(Object k, int v) {
      if (k == null) {
         if (this.containsNullKey && v == this.value[this.field_140]) {
            this.removeNullEntry();
            return true;
         } else {
            return false;
         }
      } else {
         Object[] key = this.key;
         Object curr;
         int pos;
         if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) {
            return false;
         } else if (k.equals(curr) && v == this.value[pos]) {
            this.removeEntry(pos);
            return true;
         } else {
            do {
               if ((curr = key[pos = pos + 1 & this.mask]) == null) {
                  return false;
               }
            } while(!k.equals(curr) || v != this.value[pos]);

            this.removeEntry(pos);
            return true;
         }
      }
   }

   public boolean replace(Object k, int oldValue, int v) {
      int pos = this.find(k);
      if (pos >= 0 && oldValue == this.value[pos]) {
         this.value[pos] = v;
         return true;
      } else {
         return false;
      }
   }

   public int replace(Object k, int v) {
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         int oldValue = this.value[pos];
         this.value[pos] = v;
         return oldValue;
      }
   }

   public int computeIntIfAbsent(Object k, ToIntFunction mappingFunction) {
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

   public int computeIntIfPresent(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         return this.defRetValue;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(k, this.value[pos]);
         if (newValue == null) {
            if (k == null) {
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

   public int computeInt(Object k, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      Integer newValue = (Integer)remappingFunction.apply(k, pos >= 0 ? this.value[pos] : null);
      if (newValue == null) {
         if (pos >= 0) {
            if (k == null) {
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

   public int mergeInt(Object k, int v, BiFunction remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
         return v;
      } else {
         Integer newValue = (Integer)remappingFunction.apply(this.value[pos], v);
         if (newValue == null) {
            if (k == null) {
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
         Arrays.fill(this.key, (Object)null);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public Object2IntMap.FastEntrySet object2IntEntrySet() {
      if (this.entries == null) {
         this.entries = new Object2IntOpenHashMap.MapEntrySet();
      }

      return this.entries;
   }

   public ObjectSet keySet() {
      if (this.keys == null) {
         this.keys = new Object2IntOpenHashMap.KeySet();
      }

      return this.keys;
   }

   public IntCollection values() {
      if (this.values == null) {
         this.values = new AbstractIntCollection() {
            public IntIterator iterator() {
               return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.ValueIterator(Object2IntOpenHashMap.this);
            }

            public int size() {
               return Object2IntOpenHashMap.this.size;
            }

            public boolean contains(int v) {
               return Object2IntOpenHashMap.this.containsValue(v);
            }

            public void clear() {
               Object2IntOpenHashMap.this.clear();
            }

            public void forEach(IntConsumer consumer) {
               if (Object2IntOpenHashMap.this.containsNullKey) {
                  consumer.accept(Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.field_140]);
               }

               int pos = Object2IntOpenHashMap.this.field_140;

               while(pos-- != 0) {
                  if (Object2IntOpenHashMap.this.key[pos] != null) {
                     consumer.accept(Object2IntOpenHashMap.this.value[pos]);
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
      int l = HashCommon.nextPowerOfTwo((int)Math.ceil((double)((float)n / this.field_141)));
      if (l < this.field_140 && this.size <= HashCommon.maxFill(l, this.field_141)) {
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
      Object[] key = this.key;
      int[] value = this.value;
      int mask = newN - 1;
      Object[] newKey = new Object[newN + 1];
      int[] newValue = new int[newN + 1];
      int i = this.field_140;

      int pos;
      for(int var9 = this.realSize(); var9-- != 0; newValue[pos] = value[i]) {
         do {
            --i;
         } while(key[i] == null);

         if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null) {
            while(newKey[pos = pos + 1 & mask] != null) {
            }
         }

         newKey[pos] = key[i];
      }

      newValue[newN] = value[this.field_140];
      this.field_140 = newN;
      this.mask = mask;
      this.maxFill = HashCommon.maxFill(this.field_140, this.field_141);
      this.key = newKey;
      this.value = newValue;
   }

   public Object2IntOpenHashMap clone() {
      Object2IntOpenHashMap c;
      try {
         c = (Object2IntOpenHashMap)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      c.keys = null;
      c.values = null;
      c.entries = null;
      c.containsNullKey = this.containsNullKey;
      c.key = (Object[])this.key.clone();
      c.value = (int[])this.value.clone();
      return c;
   }

   public int hashCode() {
      int h = 0;
      int j = this.realSize();
      int i = 0;

      for(int t = 0; j-- != 0; ++i) {
         while(this.key[i] == null) {
            ++i;
         }

         if (this != this.key[i]) {
            t = this.key[i].hashCode();
         }

         t ^= this.value[i];
         h += t;
      }

      if (this.containsNullKey) {
         h += this.value[this.field_140];
      }

      return h;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      Object[] key = this.key;
      int[] value = this.value;
      Object2IntOpenHashMap.MapIterator i = new Object2IntOpenHashMap.MapIterator();
      s.defaultWriteObject();
      int var5 = this.size;

      while(var5-- != 0) {
         int e = i.nextEntry();
         s.writeObject(key[e]);
         s.writeInt(value[e]);
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.field_140 = HashCommon.arraySize(this.size, this.field_141);
      this.maxFill = HashCommon.maxFill(this.field_140, this.field_141);
      this.mask = this.field_140 - 1;
      Object[] key = this.key = new Object[this.field_140 + 1];
      int[] value = this.value = new int[this.field_140 + 1];

      int v;
      int pos;
      for(int var6 = this.size; var6-- != 0; value[pos] = v) {
         Object k = s.readObject();
         v = s.readInt();
         if (k == null) {
            pos = this.field_140;
            this.containsNullKey = true;
         } else {
            for(pos = HashCommon.mix(k.hashCode()) & this.mask; key[pos] != null; pos = pos + 1 & this.mask) {
            }
         }

         key[pos] = k;
      }

   }

   private void checkTable() {
   }

   private final class MapEntrySet extends AbstractObjectSet implements Object2IntMap.FastEntrySet {
      private MapEntrySet() {
      }

      public ObjectIterator iterator() {
         return Object2IntOpenHashMap.this.new EntryIterator();
      }

      public ObjectIterator fastIterator() {
         return Object2IntOpenHashMap.this.new FastEntryIterator();
      }

      public boolean contains(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               Object k = e.getKey();
               int v = (Integer)e.getValue();
               if (k == null) {
                  return Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.field_140] == v;
               } else {
                  Object[] key = Object2IntOpenHashMap.this.key;
                  Object curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (k.equals(curr)) {
                     return Object2IntOpenHashMap.this.value[pos] == v;
                  } else {
                     while((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) != null) {
                        if (k.equals(curr)) {
                           return Object2IntOpenHashMap.this.value[pos] == v;
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
            if (e.getValue() != null && e.getValue() instanceof Integer) {
               Object k = e.getKey();
               int v = (Integer)e.getValue();
               if (k == null) {
                  if (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.field_140] == v) {
                     Object2IntOpenHashMap.this.removeNullEntry();
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  Object[] key = Object2IntOpenHashMap.this.key;
                  Object curr;
                  int pos;
                  if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) {
                     return false;
                  } else if (curr.equals(k)) {
                     if (Object2IntOpenHashMap.this.value[pos] == v) {
                        Object2IntOpenHashMap.this.removeEntry(pos);
                        return true;
                     } else {
                        return false;
                     }
                  } else {
                     do {
                        if ((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) == null) {
                           return false;
                        }
                     } while(!curr.equals(k) || Object2IntOpenHashMap.this.value[pos] != v);

                     Object2IntOpenHashMap.this.removeEntry(pos);
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }

      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      public void clear() {
         Object2IntOpenHashMap.this.clear();
      }

      public void forEach(Consumer consumer) {
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(new AbstractObject2IntMap.BasicEntry(Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.field_140], Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.field_140]));
         }

         int pos = Object2IntOpenHashMap.this.field_140;

         while(pos-- != 0) {
            if (Object2IntOpenHashMap.this.key[pos] != null) {
               consumer.accept(new AbstractObject2IntMap.BasicEntry(Object2IntOpenHashMap.this.key[pos], Object2IntOpenHashMap.this.value[pos]));
            }
         }

      }

      public void fastForEach(Consumer consumer) {
         AbstractObject2IntMap.BasicEntry entry = new AbstractObject2IntMap.BasicEntry();
         if (Object2IntOpenHashMap.this.containsNullKey) {
            entry.key = Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.field_140];
            entry.value = Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.field_140];
            consumer.accept(entry);
         }

         int pos = Object2IntOpenHashMap.this.field_140;

         while(pos-- != 0) {
            if (Object2IntOpenHashMap.this.key[pos] != null) {
               entry.key = Object2IntOpenHashMap.this.key[pos];
               entry.value = Object2IntOpenHashMap.this.value[pos];
               consumer.accept(entry);
            }
         }

      }

      // $FF: synthetic method
      MapEntrySet(Object x1) {
         this();
      }
   }

   private final class KeySet extends AbstractObjectSet {
      private KeySet() {
      }

      public ObjectIterator iterator() {
         return new com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap.KeyIterator(Object2IntOpenHashMap.this);
      }

      public void forEach(Consumer consumer) {
         if (Object2IntOpenHashMap.this.containsNullKey) {
            consumer.accept(Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.field_140]);
         }

         int pos = Object2IntOpenHashMap.this.field_140;

         while(pos-- != 0) {
            Object k = Object2IntOpenHashMap.this.key[pos];
            if (k != null) {
               consumer.accept(k);
            }
         }

      }

      public int size() {
         return Object2IntOpenHashMap.this.size;
      }

      public boolean contains(Object k) {
         return Object2IntOpenHashMap.this.containsKey(k);
      }

      public boolean remove(Object k) {
         int oldSize = Object2IntOpenHashMap.this.size;
         Object2IntOpenHashMap.this.removeInt(k);
         return Object2IntOpenHashMap.this.size != oldSize;
      }

      public void clear() {
         Object2IntOpenHashMap.this.clear();
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
      int field_139;
      boolean mustReturnNullKey;
      ObjectArrayList wrapped;

      private MapIterator() {
         this.pos = Object2IntOpenHashMap.this.field_140;
         this.last = -1;
         this.field_139 = Object2IntOpenHashMap.this.size;
         this.mustReturnNullKey = Object2IntOpenHashMap.this.containsNullKey;
      }

      public boolean hasNext() {
         return this.field_139 != 0;
      }

      public int nextEntry() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            --this.field_139;
            if (this.mustReturnNullKey) {
               this.mustReturnNullKey = false;
               return this.last = Object2IntOpenHashMap.this.field_140;
            } else {
               Object[] key = Object2IntOpenHashMap.this.key;

               while(--this.pos >= 0) {
                  if (key[this.pos] != null) {
                     return this.last = this.pos;
                  }
               }

               this.last = Integer.MIN_VALUE;
               Object k = this.wrapped.get(-this.pos - 1);

               int p;
               for(p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask; !k.equals(key[p]); p = p + 1 & Object2IntOpenHashMap.this.mask) {
               }

               return p;
            }
         }
      }

      private void shiftKeys(int pos) {
         Object[] key = Object2IntOpenHashMap.this.key;

         while(true) {
            int last = pos;
            pos = pos + 1 & Object2IntOpenHashMap.this.mask;

            Object curr;
            while(true) {
               if ((curr = key[pos]) == null) {
                  key[last] = null;
                  return;
               }

               int slot = HashCommon.mix(curr.hashCode()) & Object2IntOpenHashMap.this.mask;
               if (last <= pos) {
                  if (last >= slot || slot > pos) {
                     break;
                  }
               } else if (last >= slot && slot > pos) {
                  break;
               }

               pos = pos + 1 & Object2IntOpenHashMap.this.mask;
            }

            if (pos < last) {
               if (this.wrapped == null) {
                  this.wrapped = new ObjectArrayList(2);
               }

               this.wrapped.add(key[pos]);
            }

            key[last] = curr;
            Object2IntOpenHashMap.this.value[last] = Object2IntOpenHashMap.this.value[pos];
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            if (this.last == Object2IntOpenHashMap.this.field_140) {
               Object2IntOpenHashMap.this.containsNullKey = false;
               Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.field_140] = null;
            } else {
               if (this.pos < 0) {
                  Object2IntOpenHashMap.this.removeInt(this.wrapped.set(-this.pos - 1, (Object)null));
                  this.last = -1;
                  return;
               }

               this.shiftKeys(this.last);
            }

            --Object2IntOpenHashMap.this.size;
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

   private class FastEntryIterator extends Object2IntOpenHashMap.MapIterator implements ObjectIterator {
      private final Object2IntOpenHashMap.MapEntry entry;

      private FastEntryIterator() {
         super(null);
         this.entry = Object2IntOpenHashMap.this.new MapEntry();
      }

      public Object2IntOpenHashMap.MapEntry next() {
         this.entry.index = this.nextEntry();
         return this.entry;
      }

      // $FF: synthetic method
      FastEntryIterator(Object x1) {
         this();
      }
   }

   private class EntryIterator extends Object2IntOpenHashMap.MapIterator implements ObjectIterator {
      private Object2IntOpenHashMap.MapEntry entry;

      private EntryIterator() {
         super(null);
      }

      public Object2IntOpenHashMap.MapEntry next() {
         return this.entry = Object2IntOpenHashMap.this.new MapEntry(this.nextEntry());
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

   final class MapEntry implements Object2IntMap.Entry, java.util.Map.Entry {
      int index;

      MapEntry(int index) {
         this.index = index;
      }

      MapEntry() {
      }

      public Object getKey() {
         return Object2IntOpenHashMap.this.key[this.index];
      }

      public int getIntValue() {
         return Object2IntOpenHashMap.this.value[this.index];
      }

      public int setValue(int v) {
         int oldValue = Object2IntOpenHashMap.this.value[this.index];
         Object2IntOpenHashMap.this.value[this.index] = v;
         return oldValue;
      }

      /** @deprecated */
      @Deprecated
      public Integer getValue() {
         return Object2IntOpenHashMap.this.value[this.index];
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
            return Objects.equals(Object2IntOpenHashMap.this.key[this.index], e.getKey()) && Object2IntOpenHashMap.this.value[this.index] == (Integer)e.getValue();
         }
      }

      public int hashCode() {
         return (Object2IntOpenHashMap.this.key[this.index] == null ? 0 : Object2IntOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntOpenHashMap.this.value[this.index];
      }

      public String toString() {
         return Object2IntOpenHashMap.this.key[this.index] + "=>" + Object2IntOpenHashMap.this.value[this.index];
      }
   }
}
