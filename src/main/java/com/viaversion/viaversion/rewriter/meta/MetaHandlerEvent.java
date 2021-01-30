package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MetaHandlerEvent {
   UserConnection user();

   int entityId();

   @Nullable
   EntityType entityType();

   default int index() {
      return this.meta().method_71();
   }

   default void setIndex(int index) {
      this.meta().setId(index);
   }

   Metadata meta();

   void cancel();

   boolean cancelled();

   @Nullable
   Metadata metaAtIndex(int var1);

   List metadataList();

   @Nullable
   List extraMeta();

   void createExtraMeta(Metadata var1);
}
