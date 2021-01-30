package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_14;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class MetadataRewriter1_15To1_14_4 extends EntityRewriter {
   public MetadataRewriter1_15To1_14_4(Protocol1_15To1_14_4 protocol) {
      super(protocol);
   }

   public void handleMetadata(int entityId, EntityType type, Metadata metadata, List metadatas, UserConnection connection) throws Exception {
      int data;
      if (metadata.metaType() == MetaType1_14.Slot) {
         ((Protocol1_15To1_14_4)this.protocol).getItemRewriter().handleItemToClient((Item)metadata.getValue());
      } else if (metadata.metaType() == MetaType1_14.BlockID) {
         data = (Integer)metadata.getValue();
         metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data));
      } else if (metadata.metaType() == MetaType1_13.PARTICLE) {
         this.rewriteParticle((Particle)metadata.getValue());
      }

      if (type != null) {
         if (type.isOrHasParent(Entity1_15Types.MINECART_ABSTRACT) && metadata.method_71() == 10) {
            data = (Integer)metadata.getValue();
            metadata.setValue(((Protocol1_15To1_14_4)this.protocol).getMappingData().getNewBlockStateId(data));
         }

         if (metadata.method_71() > 11 && type.isOrHasParent(Entity1_15Types.LIVINGENTITY)) {
            metadata.setId(metadata.method_71() + 1);
         }

         if (type.isOrHasParent(Entity1_15Types.WOLF)) {
            if (metadata.method_71() == 18) {
               metadatas.remove(metadata);
            } else if (metadata.method_71() > 18) {
               metadata.setId(metadata.method_71() - 1);
            }
         }

      }
   }

   public int newEntityId(int id) {
      return EntityPackets.getNewEntityId(id);
   }

   public EntityType typeFromId(int type) {
      return Entity1_15Types.getTypeFromId(type);
   }
}
