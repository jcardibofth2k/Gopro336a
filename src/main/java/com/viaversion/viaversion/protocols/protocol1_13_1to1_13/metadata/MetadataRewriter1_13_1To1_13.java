package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_13_1To1_13 extends EntityRewriter {
   public MetadataRewriter1_13_1To1_13(Protocol1_13_1To1_13 protocol) {
      super(protocol);
   }

   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List metadatas, UserConnection connection) {
      int data;
      if (metadata.metaType() == MetaType1_13.Slot) {
         this.protocol.getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == MetaType1_13.BlockID) {
         data = (Integer)metadata.getValue();
         metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == MetaType1_13.PARTICLE) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (type.isOrHasParent(Entity1_13Types.EntityType.MINECART_ABSTRACT) && metadata.method_71() == 9) {
            data = (Integer)metadata.getValue();
            metadata.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         } else if (type.isOrHasParent(Entity1_13Types.EntityType.ABSTRACT_ARROW) && metadata.method_71() >= 7) {
            metadata.setId(metadata.method_71() + 1);
         }

      }
   }

   public EntityType typeFromId(int type) {
      return Entity1_13Types.getTypeFromId(type, false);
   }

   public EntityType objectTypeFromId(int type) {
      return Entity1_13Types.getTypeFromId(type, true);
   }
}
