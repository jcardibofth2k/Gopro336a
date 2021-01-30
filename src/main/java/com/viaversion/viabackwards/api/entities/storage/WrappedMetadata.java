package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class WrappedMetadata {
   private final List metadataList;

   public WrappedMetadata(List metadataList) {
      this.metadataList = metadataList;
   }

   public boolean has(Metadata data) {
      return this.metadataList.contains(data);
   }

   public void remove(Metadata data) {
      this.metadataList.remove(data);
   }

   public void remove(int index) {
      this.metadataList.removeIf((meta) -> {
         return meta.method_71() == index;
      });
   }

   public void add(Metadata data) {
      this.metadataList.add(data);
   }

   @Nullable
   public Metadata get(int index) {
      Iterator var2 = this.metadataList.iterator();

      Metadata meta;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         meta = (Metadata)var2.next();
      } while(index != meta.method_71());

      return meta;
   }

   public List metadataList() {
      return this.metadataList;
   }

   public String toString() {
      return "MetaStorage{metaDataList=" + this.metadataList + '}';
   }
}
