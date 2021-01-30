package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.1.1;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObject2IntMap extends AbstractObject2IntFunction implements Object2IntMap, Serializable {
   private static final long serialVersionUID = -4940583368468432370L;

   protected AbstractObject2IntMap() {
   }

   public boolean containsValue(int v) {
      return this.values().contains(v);
   }

   public boolean containsKey(Object k) {
      ObjectIterator i = this.object2IntEntrySet().iterator();

      do {
         if (!i.hasNext()) {
            return false;
         }
      } while(((Object2IntMap.Entry)i.next()).getKey() != k);

      return true;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public ObjectSet keySet() {
      return new AbstractObjectSet() {
         public boolean contains(Object k) {
            return AbstractObject2IntMap.this.containsKey(k);
         }

         public int size() {
            return AbstractObject2IntMap.this.size();
         }

         public void clear() {
            AbstractObject2IntMap.this.clear();
         }

         public ObjectIterator iterator() {
            return new 1(this);
         }
      };
   }

   public IntCollection values() {
      return new AbstractIntCollection() {
         public boolean contains(int k) {
            return AbstractObject2IntMap.this.containsValue(k);
         }

         public int size() {
            return AbstractObject2IntMap.this.size();
         }

         public void clear() {
            AbstractObject2IntMap.this.clear();
         }

         public IntIterator iterator() {
            return new com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntMap.2.1(this);
         }
      };
   }

   public void putAll(Map m) {
      if (m instanceof Object2IntMap) {
         ObjectIterator i = Object2IntMaps.fastIterator((Object2IntMap)m);

         while(i.hasNext()) {
            Object2IntMap.Entry e = (Object2IntMap.Entry)i.next();
            this.put(e.getKey(), e.getIntValue());
         }
      } else {
         int n = m.size();
         Iterator i = m.entrySet().iterator();

         while(n-- != 0) {
            java.util.Map.Entry e = (java.util.Map.Entry)i.next();
            this.put(e.getKey(), (Integer)e.getValue());
         }
      }

   }

   public int hashCode() {
      int h = 0;
      int n = this.size();

      for(ObjectIterator i = Object2IntMaps.fastIterator(this); n-- != 0; h += ((Object2IntMap.Entry)i.next()).hashCode()) {
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
         return m.size() != this.size() ? false : this.object2IntEntrySet().containsAll(m.entrySet());
      }
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      ObjectIterator i = Object2IntMaps.fastIterator(this);
      int n = this.size();
      boolean first = true;
      s.append("{");

      while(n-- != 0) {
         if (first) {
            first = false;
         } else {
            s.append(", ");
         }

         Object2IntMap.Entry e = (Object2IntMap.Entry)i.next();
         if (this == e.getKey()) {
            s.append("(this map)");
         } else {
            s.append(String.valueOf(e.getKey()));
         }

         s.append("=>");
         s.append(String.valueOf(e.getIntValue()));
      }

      s.append("}");
      return s.toString();
   }

   public static class BasicEntry implements Object2IntMap.Entry {
      protected Object key;
      protected int value;

      public BasicEntry() {
      }

      public BasicEntry(Object key, Integer value) {
         this.key = key;
         this.value = value;
      }

      public BasicEntry(Object key, int value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
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
         } else if (o instanceof Object2IntMap.Entry) {
            Object2IntMap.Entry e = (Object2IntMap.Entry)o;
            return Objects.equals(this.key, e.getKey()) && this.value == e.getIntValue();
         } else {
            java.util.Map.Entry e = (java.util.Map.Entry)o;
            Object key = e.getKey();
            Object value = e.getValue();
            if (value != null && value instanceof Integer) {
               return Objects.equals(this.key, key) && this.value == (Integer)value;
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
      }

      public String toString() {
         return this.key + "->" + this.value;
      }
   }
}
