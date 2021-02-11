package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Pair {
   private final Object key;
   private Object value;

   public Pair(@Nullable Object key, @Nullable Object value) {
      this.key = key;
      this.value = value;
   }

   @Nullable
   public Object getKey() {
      return this.key;
   }

   @Nullable
   public Object getValue() {
      return this.value;
   }

   public void setValue(@Nullable Object value) {
      this.value = value;
   }

   public String toString() {
      return "Pair{" + this.key + ", " + this.value + '}';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Pair pair = (Pair)o;
         return Objects.equals(this.key, pair.key) && Objects.equals(this.value, pair.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.key != null ? this.key.hashCode() : 0;
      result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
      return result;
   }
}
