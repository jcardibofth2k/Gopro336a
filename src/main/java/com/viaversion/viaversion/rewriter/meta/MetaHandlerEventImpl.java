package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MetaHandlerEventImpl implements MetaHandlerEvent {
   private final UserConnection connection;
   private final EntityType entityType;
   private final int entityId;
   private final List metadataList;
   private final Metadata meta;
   private List extraData;
   private boolean cancel;

   public MetaHandlerEventImpl(UserConnection connection, @Nullable EntityType entityType, int entityId, Metadata meta, List metadataList) {
      this.connection = connection;
      this.entityType = entityType;
      this.entityId = entityId;
      this.meta = meta;
      this.metadataList = metadataList;
   }

   public UserConnection user() {
      return this.connection;
   }

   public int entityId() {
      return this.entityId;
   }

   @Nullable
   public EntityType entityType() {
      return this.entityType;
   }

   public Metadata meta() {
      return this.meta;
   }

   public void cancel() {
      this.cancel = true;
   }

   public boolean cancelled() {
      return this.cancel;
   }

   @Nullable
   public Metadata metaAtIndex(int index) {
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
      return Collections.unmodifiableList(this.metadataList);
   }

   @Nullable
   public List extraMeta() {
      return this.extraData;
   }

   public void createExtraMeta(Metadata metadata) {
      (this.extraData != null ? this.extraData : (this.extraData = new ArrayList())).add(metadata);
   }
}
