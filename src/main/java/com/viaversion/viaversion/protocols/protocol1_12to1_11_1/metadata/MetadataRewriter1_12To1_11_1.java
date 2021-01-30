package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.BedRewriter;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_12To1_11_1 extends EntityRewriter {
   public MetadataRewriter1_12To1_11_1(Protocol1_12To1_11_1 protocol) {
      super(protocol);
   }

   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List metadatas, UserConnection connection) {
      if (metadata.getValue() instanceof DataItem) {
         BedRewriter.toClientItem((Item)metadata.getValue());
      }

      if (type != null) {
         if (type == Entity1_12Types.EntityType.EVOCATION_ILLAGER && metadata.method_71() == 12) {
            metadata.setId(13);
         }

      }
   }

   public EntityType typeFromId(int type) {
      return Entity1_12Types.getTypeFromId(type, false);
   }

   public EntityType objectTypeFromId(int type) {
      return Entity1_12Types.getTypeFromId(type, true);
   }
}
