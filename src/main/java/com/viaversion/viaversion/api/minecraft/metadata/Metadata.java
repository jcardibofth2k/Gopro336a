package com.viaversion.viaversion.api.minecraft.metadata;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Metadata {
   // $FF: renamed from: id int
   private int field_154;
   private MetaType metaType;
   private Object value;

   public Metadata(int id, MetaType metaType, @Nullable Object value) {
      this.field_154 = id;
      this.metaType = metaType;
      this.value = this.checkValue(metaType, value);
   }

   // $FF: renamed from: id () int
   public int method_71() {
      return this.field_154;
   }

   public void setId(int id) {
      this.field_154 = id;
   }

   public MetaType metaType() {
      return this.metaType;
   }

   public void setMetaType(MetaType metaType) {
      this.checkValue(metaType, this.value);
      this.metaType = metaType;
   }

   @Nullable
   public Object value() {
      return this.value;
   }

   @Nullable
   public Object getValue() {
      return this.value;
   }

   public void setValue(@Nullable Object value) {
      this.value = this.checkValue(this.metaType, value);
   }

   public void setTypeAndValue(MetaType metaType, @Nullable Object value) {
      this.value = this.checkValue(metaType, value);
      this.metaType = metaType;
   }

   private Object checkValue(MetaType metaType, @Nullable Object value) {
      Preconditions.checkNotNull(metaType);
      if (value != null && !metaType.type().getOutputClass().isAssignableFrom(value.getClass())) {
         throw new IllegalArgumentException("Metadata value and metaType are incompatible. Type=" + metaType + ", value=" + (value != null ? value + " (" + value.getClass().getSimpleName() + ")" : "null"));
      } else {
         return value;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setMetaTypeUnsafe(MetaType type) {
      this.metaType = type;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Metadata metadata = (Metadata)o;
         if (this.field_154 != metadata.field_154) {
            return false;
         } else {
            return this.metaType == this.metaType && Objects.equals(this.value, metadata.value);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.field_154;
      result = 31 * result + this.metaType.hashCode();
      result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
      return result;
   }

   public String toString() {
      return "Metadata{id=" + this.field_154 + ", metaType=" + this.metaType + ", value=" + this.value + '}';
   }
}
