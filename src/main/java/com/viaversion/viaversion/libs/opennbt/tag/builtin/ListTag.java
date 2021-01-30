package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class ListTag extends Tag implements Iterable {
   // $FF: renamed from: ID int
   public static final int field_45 = 9;
   private final List value;
   private Class type;

   public ListTag() {
      this.value = new ArrayList();
   }

   public ListTag(@Nullable Class type) {
      this.type = type;
      this.value = new ArrayList();
   }

   public ListTag(List value) throws IllegalArgumentException {
      this.value = new ArrayList(value.size());
      this.setValue(value);
   }

   public List getValue() {
      return this.value;
   }

   public void setValue(List value) throws IllegalArgumentException {
      Preconditions.checkNotNull(value);
      this.type = null;
      this.value.clear();
      Iterator var2 = value.iterator();

      while(var2.hasNext()) {
         Tag tag = (Tag)var2.next();
         this.add(tag);
      }

   }

   public Class getElementType() {
      return this.type;
   }

   public boolean add(Tag tag) throws IllegalArgumentException {
      Preconditions.checkNotNull(tag);
      if (this.type == null) {
         this.type = tag.getClass();
      } else if (tag.getClass() != this.type) {
         throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
      }

      return this.value.add(tag);
   }

   public boolean remove(Tag tag) {
      return this.value.remove(tag);
   }

   public Tag get(int index) {
      return (Tag)this.value.get(index);
   }

   public int size() {
      return this.value.size();
   }

   public Iterator iterator() {
      return this.value.iterator();
   }

   public void read(DataInput in) throws IOException {
      this.type = null;
      int id = in.readByte();
      if (id != 0) {
         this.type = TagRegistry.getClassFor(id);
         if (this.type == null) {
            throw new IOException("Unknown tag ID in ListTag: " + id);
         }
      }

      int count = in.readInt();

      for(int index = 0; index < count; ++index) {
         Tag tag;
         try {
            tag = TagRegistry.createInstance(id);
         } catch (TagCreateException var7) {
            throw new IOException("Failed to create tag.", var7);
         }

         tag.read(in);
         this.add(tag);
      }

   }

   public void write(DataOutput out) throws IOException {
      if (this.type == null) {
         out.writeByte(0);
      } else {
         int id = TagRegistry.getIdFor(this.type);
         if (id == -1) {
            throw new IOException("ListTag contains unregistered tag class.");
         }

         out.writeByte(id);
      }

      out.writeInt(this.value.size());
      Iterator var4 = this.value.iterator();

      while(var4.hasNext()) {
         Tag tag = (Tag)var4.next();
         tag.write(out);
      }

   }

   public final ListTag clone() {
      List newList = new ArrayList();
      Iterator var2 = this.value.iterator();

      while(var2.hasNext()) {
         Tag value = (Tag)var2.next();
         newList.add(value.clone());
      }

      return new ListTag(newList);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ListTag tags = (ListTag)o;
         return !Objects.equals(this.type, tags.type) ? false : this.value.equals(tags.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.type != null ? this.type.hashCode() : 0;
      result = 31 * result + this.value.hashCode();
      return result;
   }

   public int getTagId() {
      return 9;
   }
}
