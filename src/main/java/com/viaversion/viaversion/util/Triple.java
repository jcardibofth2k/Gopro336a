package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Triple {
   private final Object first;
   private final Object second;
   private final Object third;

   public Triple(@Nullable Object first, @Nullable Object second, @Nullable Object third) {
      this.first = first;
      this.second = second;
      this.third = third;
   }

   @Nullable
   public Object getFirst() {
      return this.first;
   }

   @Nullable
   public Object getSecond() {
      return this.second;
   }

   @Nullable
   public Object getThird() {
      return this.third;
   }

   public String toString() {
      return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Triple triple = (Triple)o;
         if (!Objects.equals(this.first, triple.first)) {
            return false;
         } else {
            return !Objects.equals(this.second, triple.second) ? false : Objects.equals(this.third, triple.third);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.first != null ? this.first.hashCode() : 0;
      result = 31 * result + (this.second != null ? this.second.hashCode() : 0);
      result = 31 * result + (this.third != null ? this.third.hashCode() : 0);
      return result;
   }
}
