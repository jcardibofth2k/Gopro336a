package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.1.1;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractInt2ObjectMap extends AbstractInt2ObjectFunction implements Int2ObjectMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractInt2ObjectMap() {
   }

   public boolean containsValue(Object v) {
      return this.values().contains(v);
   }

   public boolean containsKey(int k) {
      ObjectIterator i = this.int2ObjectEntrySet().iterator();

      do {
         if (!i.hasNext()) {
            return false;
         }
      } while(((Int2ObjectMap.Entry)i.next()).getIntKey() != k);

      return true;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public IntSet keySet() {
      return new AbstractIntSet() {
         public boolean contains(int k) {
            return AbstractInt2ObjectMap.this.containsKey(k);
         }

         public int size() {
            return AbstractInt2ObjectMap.this.size();
         }

         public void clear() {
            AbstractInt2ObjectMap.this.clear();
         }

         public IntIterator iterator() {
            return new 1(this);
         }
      };
   }

   public ObjectCollection values() {
      return new AbstractObjectCollection() {
         public boolean contains(Object k) {
            return AbstractInt2ObjectMap.this.containsValue(k);
         }

         public int size() {
            return AbstractInt2ObjectMap.this.size();
         }

         public void clear() {
            AbstractInt2ObjectMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectMap.2.1(this);
         }
      };
   }

   public void putAll(Map m) {
      if (m instanceof Int2ObjectMap) {
         ObjectIterator i = Int2ObjectMaps.fastIterator((Int2ObjectMap)m);

         while(i.hasNext()) {
            Int2ObjectMap.Entry e = (Int2ObjectMap.Entry)i.next();
            this.put(e.getIntKey(), e.getValue());
         }
      } else {
         int n = m.size();
         Iterator i = m.entrySet().iterator();

         while(n-- != 0) {
            java.util.Map.Entry e = (java.util.Map.Entry)i.next();
            this.put((Integer)e.getKey(), e.getValue());
         }
      }

   }

   public int hashCode() {
      int h = 0;
      int n = this.size();

      for(ObjectIterator i = Int2ObjectMaps.fastIterator(this); n-- != 0; h += ((Int2ObjectMap.Entry)i.next()).hashCode()) {
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
         return m.size() != this.size() ? false : this.int2ObjectEntrySet().containsAll(m.entrySet());
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator i = Int2ObjectMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Int2ObjectMap.Entry e = (Int2ObjectMap.Entry)i.next();
         s.append(String.valueOf(e.getIntKey()));
         s.append("=>");
         if (this == e.getValue()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getValue()));
         }
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry implements Int2ObjectMap.Entry {
      protected int key;
      protected Object value;

      public BasicEntry() {
      }

      public BasicEntry(Integer key, Object value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(int key, Object value) {
         this.key = key;
         this.value = value;
      }

      public int getIntKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public boolean equals(Object o) {
         if (!(o instanceof java.util.Map.Entry)) {
            return false;
         } else if (o instanceof Int2ObjectMap.Entry) {
            Int2ObjectMap.Entry e = (Int2ObjectMap.Entry)o;
            return this.key == e.getIntKey() && Objects.equals(this.value, e.getValue());
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            Object key = e.getKey();
            if (key != null && key instanceof Integer) {
               Object value = e.getValue();
               return this.key == (Integer)key && Objects.equals(this.value, value);
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.key ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public String toString() {
         return this.key + "->" + this.value;
      }
   }
}
