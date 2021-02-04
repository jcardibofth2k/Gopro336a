package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap.1.1;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractInt2IntMap extends AbstractInt2IntFunction implements Int2IntMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractInt2IntMap() {
   }

   public boolean containsValue(int v) {
      return this.values().contains(v);
   }

   public boolean containsKey(int k) {
      ObjectIterator i = this.int2IntEntrySet().iterator();

      do {
         if (!i.hasNext()) {
            return false;
         }
      } while(((Int2IntMap.Entry)i.next()).getIntKey() != k);

      return true;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public IntSet keySet() {
      return new AbstractIntSet() {
         public boolean contains(int k) {
            return AbstractInt2IntMap.this.containsKey(k);
         }

         public int size() {
            return AbstractInt2IntMap.this.size();
         }

         public void clear() {
            AbstractInt2IntMap.this.clear();
         }

         public IntIterator iterator() {
            return new 1(this);
         }
      };
   }

   public IntCollection values() {
      return new AbstractIntCollection() {
         public boolean contains(int k) {
            return AbstractInt2IntMap.this.containsValue(k);
         }

         public int size() {
            return AbstractInt2IntMap.this.size();
         }

         public void clear() {
            AbstractInt2IntMap.this.clear();
         }

         public IntIterator iterator() {
            return new com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap.2.1(this);
         }
      };
   }

   public void putAll(Map m) {
      if (m instanceof Int2IntMap) {
         ObjectIterator i = Int2IntMaps.fastIterator((Int2IntMap)m);

         while(i.hasNext()) {
            Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
            this.put(e.getIntKey(), e.getIntValue());
         }
      } else {
         int n = m.size();
         Iterator i = m.entrySet().iterator();

         while(n-- != 0) {
            java.util.Map.Entry e = (java.util.Map.Entry)i.next();
            this.put((Integer)e.getKey(), (Integer)e.getValue());
         }
      }

   }

   public int hashCode() {
      int h = 0;
      int n = this.size();

      for(ObjectIterator i = Int2IntMaps.fastIterator(this); n-- != 0; h += i.next().hashCode()) {
      }

      return h;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Map)) {
         return false;
      } else {
         Map m = (Map)o;
         return m.size() == this.size() && this.int2IntEntrySet().containsAll(m.entrySet());
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator i = Int2IntMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
         s.append(e.getIntKey());
         s.append("=>");
         s.append(e.getIntValue());
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry implements Int2IntMap.Entry {
      protected int key;
      protected int value;

      public BasicEntry() {
      }

      public BasicEntry(Integer key, Integer value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(int key, int value) {
         this.key = key;
         this.value = value;
      }

      public int getIntKey() {
         return this.key;
      }

      public int getIntValue() {
         return this.value;
      }

      public int setValue(int value) {
         throw new UnsupportedOperationException();
      }

      public boolean equals(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else if (o instanceof Int2IntMap.Entry) {
            Int2IntMap.Entry e = (Int2IntMap.Entry)o;
            return this.key == e.getIntKey() && this.value == e.getIntValue();
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               Object value = e.getValue();
               if (value != null && value instanceof Integer) {
                  return this.key == (Integer)key && this.value == (Integer)value;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.key ^ this.value;
      }

      public String toString() {
         return this.key + "->" + this.value;
      }
   }
}
