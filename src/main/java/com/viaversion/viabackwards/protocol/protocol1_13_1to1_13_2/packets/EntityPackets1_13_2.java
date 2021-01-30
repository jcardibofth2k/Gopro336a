package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets;

import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.Protocol1_13_1To1_13_2;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13_2;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.api.type.types.version.Types1_13_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.Iterator;
import java.util.List;

public class EntityPackets1_13_2 {
   public static void register(Protocol1_13_1To1_13_2 protocol) {
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Iterator var2 = ((List)wrapper.get(Types1_13.METADATA_LIST, 0)).iterator();

                  while(var2.hasNext()) {
                     Metadata metadata = (Metadata)var2.next();
                     if (metadata.metaType() == MetaType1_13_2.Slot) {
                        metadata.setMetaType(MetaType1_13.Slot);
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Iterator var2 = ((List)wrapper.get(Types1_13.METADATA_LIST, 0)).iterator();

                  while(var2.hasNext()) {
                     Metadata metadata = (Metadata)var2.next();
                     if (metadata.metaType() == MetaType1_13_2.Slot) {
                        metadata.setMetaType(MetaType1_13.Slot);
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.ENTITY_METADATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Types1_13_2.METADATA_LIST, Types1_13.METADATA_LIST);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Iterator var2 = ((List)wrapper.get(Types1_13.METADATA_LIST, 0)).iterator();

                  while(var2.hasNext()) {
                     Metadata metadata = (Metadata)var2.next();
                     if (metadata.metaType() == MetaType1_13_2.Slot) {
                        metadata.setMetaType(MetaType1_13.Slot);
                     }
                  }

               }
            });
         }
      });
   }
}
