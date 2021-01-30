package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringTag extends Tag {
   // $FF: renamed from: ID int
   public static final int field_54 = 8;
   private String value;

   public StringTag() {
      this("");
   }

   public StringTag(String value) {
      Preconditions.checkNotNull(value);
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      Preconditions.checkNotNull(value);
      this.value = value;
   }

   public void read(DataInput in) throws IOException {
      this.value = in.readUTF();
   }

   public void write(DataOutput out) throws IOException {
      out.writeUTF(this.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         StringTag stringTag = (StringTag)o;
         return this.value.equals(stringTag.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public final StringTag clone() {
      return new StringTag(this.value);
   }

   public int getTagId() {
      return 8;
   }
}
